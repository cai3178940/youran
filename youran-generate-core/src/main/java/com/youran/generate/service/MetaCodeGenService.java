package com.youran.generate.service;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.util.AESSecurityUtil;
import com.youran.common.util.DateUtil;
import com.youran.generate.config.GenerateProperties;
import com.youran.generate.constant.ContextType;
import com.youran.generate.constant.DevMode;
import com.youran.generate.exception.SkipCurrentException;
import com.youran.generate.pojo.dto.GitCredentialDTO;
import com.youran.generate.pojo.po.*;
import com.youran.generate.pojo.vo.ProgressVO;
import com.youran.generate.template.context.BaseContext;
import com.youran.generate.template.context.ConstContext;
import com.youran.generate.template.context.EntityContext;
import com.youran.generate.template.renderer.TemplateRenderer;
import com.youran.generate.template.renderer.TemplateRendererBuilder;
import com.youran.generate.util.Zip4jUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.lib.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;


/**
 * 代码生成的核心service
 *
 * @author: cbb
 * @date: 2017/5/14
 */
@Service
public class MetaCodeGenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetaCodeGenService.class);

    @Autowired
    private MetaQueryAssembleService metaQueryAssembleService;
    @Autowired
    private MetaProjectService metaProjectService;
    @Autowired
    private GitService gitService;
    @Autowired
    private GenerateProperties generateProperties;
    @Autowired
    private GenHistoryService genHistoryService;
    @Autowired
    private DataDirService dataDirService;
    @Autowired
    private CodeTemplateService codeTemplateService;
    @Autowired
    private CodeTemplateAssembleAndCopyService codeTemplateAssembleAndCopyService;
    @Autowired
    private TemplateRendererBuilder templateRendererBuilder;
    /**
     * 代码生成须加锁
     */
    private ReentrantLock lock = new ReentrantLock();

    /**
     * 生成代码压缩包
     *
     * @param projectId        项目id
     * @param templateId       模板id
     * @param progressConsumer 实时反馈进度
     * @return
     */
    public File genCodeZip(Integer projectId, Integer templateId, Consumer<ProgressVO> progressConsumer) {
        String tmpDir = this.genProjectCodeIfNotExists(projectId, templateId, progressConsumer);
        //压缩src目录到zip文件
        this.progressing(progressConsumer, 90, 99, 1, "将项目打包成zip格式");
        File zipFile = new File(tmpDir + ".zip");
        Zip4jUtil.compressFolder(new File(tmpDir), zipFile);
        //删除临时目录
        try {
            //方便本地调试，直接将代码生成在项目中
            String devProjectDir = generateProperties.getDevProjectDir();
            if (generateProperties.getDevMode() == DevMode.ALL_REPLACE) {
                if (StringUtils.isBlank(devProjectDir)) {
                    throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR,
                        "请配置本地开发工程路径youran.generate.devProjectDir");
                }
                LOGGER.debug("------全部替换开发工程：" + devProjectDir);
                this.progressing(progressConsumer, 95, 99, 1, "全部替换开发工程" + devProjectDir);
                FileUtils.deleteDirectory(new File(devProjectDir));
                FileUtils.copyDirectory(new File(tmpDir), new File(devProjectDir));
            } else if (generateProperties.getDevMode() == DevMode.INCREMENT_REPLACE) {
                if (StringUtils.isBlank(devProjectDir)) {
                    throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR,
                        "请配置本地开发工程路径youran.generate.devProjectDir");
                }
                LOGGER.debug("------部分替换开发工程：" + devProjectDir);
                this.progressing(progressConsumer, 95, 99, 1, "部分替换开发工程" + devProjectDir);
                this.compareAndCoverFile(new File(tmpDir), new File(devProjectDir));
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        //返回zip文件
        return zipFile;
    }


    /**
     * 如果当天尚未生成过最新版本的代码，则生成代码
     * 总体进度20%-80%之间
     *
     * @param projectId        项目id
     * @param templateId       模板id
     * @param progressConsumer 进度条
     * @return 代码目录
     */
    public String genProjectCodeIfNotExists(Integer projectId, Integer templateId,
                                            Consumer<ProgressVO> progressConsumer) {
        if (lock.tryLock()) {
            try {
                MetaProjectPO project = metaProjectService.getAndCheckProject(projectId);
                CodeTemplatePO templatePO = codeTemplateService.getCodeTemplate(templateId, true);
                // 校验模板的版本
                codeTemplateService.checkTemplateVersion(templatePO);
                // 获取最新代码目录
                String projectDir = dataDirService.getProjectRecentDir(project, templatePO);
                File dir = new File(projectDir);
                // 如果尚未生成过同一个版本的代码，则执行代码生成
                if (!dir.exists()) {
                    try {
                        this.doGenCode(projectDir, projectId, templateId, progressConsumer);
                    } catch (Exception e) {
                        LOGGER.error("代码生成异常", e);
                        try {
                            FileUtils.deleteDirectory(dir);
                        } catch (IOException e1) {
                            LOGGER.error("代码生成异常回删目录失败", e1);
                        }
                        if (e instanceof BusinessException) {
                            throw e;
                        } else {
                            throw new BusinessException("代码生成异常");
                        }
                    }
                } else {
                    LOGGER.info("代码已经存在，无需生成：{}", projectDir);
                }
                return projectDir;
            } finally {
                lock.unlock();
            }
        } else {
            throw new BusinessException(ErrorCode.TOO_MANY_REQUESTS,
                "正在生成代码，请稍后再试！");
        }
    }

    /**
     * 在指定目录中生成代码
     *
     * @param projectDir
     * @param projectId
     * @param templateId
     * @param progressConsumer 代码生成进度条
     */
    private void doGenCode(String projectDir, Integer projectId,
                           Integer templateId, Consumer<ProgressVO> progressConsumer) {
        // 获取组装后的模板
        this.progressing(progressConsumer, 10, 80, 2, "获取并组装代码模板");
        CodeTemplatePO templatePO = codeTemplateAssembleAndCopyService.getAssembledCodeTemplate(templateId, true);
        // 获取组装后的项目
        this.progressing(progressConsumer, 20, 80, 5, "获取并组装项目元数据");
        MetaProjectPO project = metaQueryAssembleService.getAssembledProject(projectId, true);
        // 构建模板渲染器
        TemplateRenderer templateRenderer = templateRendererBuilder.buildRenderer(templatePO);
        LOGGER.debug("------代码生成临时路径：" + projectDir);
        this.progressing(progressConsumer, 20, 80, 1, "开始渲染代码");
        for (TemplateFilePO templateFile : templatePO.getTemplateFiles()) {
            // 跳过非内容文件
            if (!templateFile.isContentFile()) {
                continue;
            }
            try {
                // 睡10毫秒，故意慢一点，好让前端进度条反应缓慢增长
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
            this.progressing(progressConsumer, 25, 80, 2, "代码渲染中");
            if (Objects.equals(templateFile.getContextType(), ContextType.GLOBAL.getValue())) {
                // 生成全局文件
                BaseContext context = new BaseContext(project);
                this.renderTemplate(templateRenderer, context, templateFile, projectDir);
            } else if (Objects.equals(templateFile.getContextType(), ContextType.ENTITY.getValue())) {
                // 生成实体模版文件
                for (MetaEntityPO metaEntityPO : project.getEntities()) {
                    EntityContext context = new EntityContext(project, metaEntityPO);
                    this.renderTemplate(templateRenderer, context, templateFile, projectDir);
                }
            } else if (Objects.equals(templateFile.getContextType(), ContextType.CONST.getValue())) {
                // 生成枚举模版文件
                for (MetaConstPO metaConstPO : project.getConsts()) {
                    ConstContext context = new ConstContext(project, metaConstPO);
                    this.renderTemplate(templateRenderer, context, templateFile, projectDir);
                }
            } else if (Objects.equals(templateFile.getContextType(), ContextType.CHART.getValue())) {
                // 生成图表模版文件
                for (MetaConstPO metaConstPO : project.getConsts()) {
                    ConstContext context = new ConstContext(project, metaConstPO);
                    this.renderTemplate(templateRenderer, context, templateFile, projectDir);
                }
            } else {
                throw new BusinessException("未知上下文：" + templateFile.getContextType());
            }
        }
    }

    /**
     * 对比原目录下文件与目标目录，并覆盖
     *
     * @param sourceDir
     * @param targetDir
     * @throws IOException
     */
    private void compareAndCoverFile(File sourceDir, File targetDir) throws IOException {
        String sourcePath = sourceDir.getPath();
        String targetPath = targetDir.getPath();
        LOGGER.debug("sourcePath={}", sourcePath);
        LOGGER.debug("targetPath={}", targetPath);
        Iterator<File> fileIterator = FileUtils.iterateFiles(sourceDir, null, true);
        while (fileIterator.hasNext()) {
            File file = fileIterator.next();
            String path = file.getPath();
            String relativePath = path.substring(sourcePath.length());
            File targetFile = new File(targetPath + relativePath);
            if (!targetFile.exists()) {
                LOGGER.debug("目标文件不存在={}", targetPath + relativePath);
                this.doCover(targetFile, file);
            } else if (!compareFile(targetFile, file)) {
                LOGGER.debug("文件内容不相等={}", targetPath + relativePath);
                this.doCover(targetFile, file);
            }
        }

    }

    /**
     * 比对文件内容是否相同（无视创建时间注释）
     *
     * @param file1
     * @param file2
     * @return
     * @throws IOException
     */
    private boolean compareFile(File file1, File file2) throws IOException {
        boolean file1Exists = file1.exists();
        if (file1Exists != file2.exists()) {
            return false;
        }
        if (!file1Exists) {
            return true;
        }
        List<String> file1Content = FileUtils.readLines(file1, "utf-8");
        List<String> file2Content = FileUtils.readLines(file2, "utf-8");
        for (int i = 0; i < file1Content.size(); i++) {
            String l1 = file1Content.get(i);
            String l2 = file2Content.get(i);
            if (!Objects.equals(l1, l2)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 执行文件覆盖
     *
     * @param targetFile
     * @param file
     * @throws IOException
     */
    private void doCover(File targetFile, File file) throws IOException {
        LOGGER.debug("进行文件覆盖={}", targetFile.getPath());
        FileUtils.copyFile(file, targetFile);
    }

    /**
     * 渲染模板
     *
     * @param templateRenderer 模板渲染器
     * @param context          上下文信息
     * @param templateFile     模板文件
     * @param projectDir       代码输出目录
     */
    private void renderTemplate(TemplateRenderer templateRenderer, BaseContext context,
                                TemplateFilePO templateFile, String projectDir) {
        String filePath = templateFile.fetchFilePath();
        LOGGER.debug("------开始渲染模板文件: {}", filePath);
        try {
            String relativePath = templateRenderer.renderPath(templateFile, context);
            String outFilePath = projectDir + relativePath;
            File outFile = new File(outFilePath);
            // 设置当前打印文件所在目录，文件渲染过程中，模板内部有权限往当前目录生成额外文件
            context.setCurrentDirOnce(outFile.getParentFile().getPath());
            Object content = templateRenderer.renderContent(templateFile, context);
            this.writeToFile(content, outFile);
        } catch (SkipCurrentException e) {
            return;
        }
    }


    /**
     * 将文本写入文件
     *
     * @param content 代码文件内容
     * @param outFile 目标文件
     */
    private void writeToFile(Object content, File outFile) {
        File parentFile = outFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        LOGGER.debug("输出代码文件：{}", outFile.getPath());
        try {
            if (content instanceof byte[]) {
                FileUtils.writeByteArrayToFile(outFile, (byte[]) content);
            } else if (content instanceof String) {
                FileUtils.write(outFile, (String) content, "UTF-8");
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "写文件异常");
        }
    }

    /**
     * 提交到仓库
     *
     * @param projectId        项目id
     * @param templateId       模板id
     * @param progressConsumer 进度条
     * @return
     */
    public GenHistoryPO gitCommit(Integer projectId, Integer templateId, Consumer<ProgressVO> progressConsumer) {
        MetaProjectPO project = metaProjectService.getAndCheckProject(projectId);
        if (!project.getRemote()) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "当前项目未开启Git仓库");
        }
        String remoteUrl = project.getRemoteUrlByTemplateId(templateId);
        if (StringUtils.isBlank(remoteUrl)) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "仓库地址为空");
        }
        CodeTemplatePO codeTemplate = codeTemplateService.getCodeTemplate(templateId, true);
        Date now = new Date();
        String oldBranchName = null;
        String lastCommit = null;
        Integer lastVersion = 0;
        GenHistoryPO genHistory = genHistoryService.findLastGenHistory(project.getProjectId(), remoteUrl);
        if (genHistory != null) {
            if (!genHistoryService.checkVersion(project, codeTemplate, genHistory)) {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "远程仓库分支【" + genHistory.getBranch() + "】已经是最新版本");
            }
            oldBranchName = genHistory.getBranch();
            lastCommit = genHistory.getCommit();
            lastVersion = genHistory.getProjectVersion();
        }
        // 3.1.0之后使用auto分支作为新分支
        String newBranchName = generateProperties.getAutoBranchName();
        String repoDir = dataDirService.getProjectRepoDir(projectId, templateId, lastVersion);
        GitCredentialDTO credential = this.getCredentialDTO(project);
        this.progressing(progressConsumer, 5, 10, 1, "克隆远程仓库");
        Repository repository = gitService.getClonedRemoteRepository(repoDir, remoteUrl,
            credential, oldBranchName, newBranchName, lastCommit);
        // 将代码生成到本地repo目录
        this.genProjectCodeIntoRepository(projectId, templateId, progressConsumer, repository);
        // 提交到本地仓库
        this.progressing(progressConsumer, 90, 99, 1, "提交到本地仓库");
        String commit = gitService.commitAll(repository,
            DateUtil.getDateStr(now, "yyyy-MM-dd HH:mm:ss") +
                (StringUtils.isBlank(oldBranchName) ? "首次生成代码" : "增量生成代码"));
        // 推送远程仓库
        this.progressing(progressConsumer, 90, 99, 1, "推送远程仓库");
        gitService.push(repository, credential);
        // 创建提交历史
        GenHistoryPO history = genHistoryService.save(project, codeTemplate,
            remoteUrl, commit, newBranchName);
        return history;
    }

    /**
     * 显示git代码差异
     *
     * @param projectId        项目id
     * @param templateId       模板id
     * @param progressConsumer 进度条
     * @return
     */
    public String showGitDiff(Integer projectId, Integer templateId, Consumer<ProgressVO> progressConsumer) {
        MetaProjectPO project = metaProjectService.getAndCheckProject(projectId);
        if (!project.getRemote()) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "当前项目未开启Git仓库");
        }
        String remoteUrl = project.getRemoteUrlByTemplateId(templateId);
        if (StringUtils.isBlank(remoteUrl)) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "仓库地址为空");
        }
        CodeTemplatePO codeTemplate = codeTemplateService.getCodeTemplate(templateId, true);
        String oldBranchName = null;
        String lastCommit = null;
        Integer lastVersion = 0;
        GenHistoryPO genHistory = genHistoryService.findLastGenHistory(project.getProjectId(), remoteUrl);
        if (genHistory != null) {
            if (!genHistoryService.checkVersion(project, codeTemplate, genHistory)) {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "上次提交以来，元数据无变动");
            }
            oldBranchName = genHistory.getBranch();
            lastCommit = genHistory.getCommit();
            lastVersion = genHistory.getProjectVersion();
        }
        String repoDir = dataDirService.getProjectRepoDir(projectId, templateId, lastVersion);
        GitCredentialDTO credential = this.getCredentialDTO(project);
        this.progressing(progressConsumer, 5, 10, 1, "克隆远程仓库");
        Repository repository = gitService.getClonedRemoteRepository(repoDir, remoteUrl,
            credential, oldBranchName, generateProperties.getAutoBranchName(), lastCommit);
        // 将代码生成到本地repo目录
        this.genProjectCodeIntoRepository(projectId, templateId, progressConsumer, repository);
        this.progressing(progressConsumer, 90, 99, 1, "提交到暂存区");
        // 提交到暂存区
        String stash = gitService.createStash(repository);
        // 如果代码无变化，则直接返回空字符串
        if (stash == null) {
            return "";
        }
        this.progressing(progressConsumer, 90, 99, 1, "获取差异代码");
        // 获取差异代码
        return gitService.getStashDiff(repository, stash);
    }


    /**
     * 将代码生成到本地repo目录
     *
     * @param projectId        项目id
     * @param templateId       模板id
     * @param progressConsumer 进度条
     * @param repository       本地仓库
     */
    private void genProjectCodeIntoRepository(Integer projectId, Integer templateId,
                                              Consumer<ProgressVO> progressConsumer, Repository repository) {
        File repoDir = repository.getDirectory().getParentFile();
        File[] oldFiles = repoDir.listFiles((dir, name) -> !".git".equals(name));
        try {
            this.progressing(progressConsumer, 6, 10, 1, "清空旧代码");
            for (File oldFile : oldFiles) {
                FileUtils.forceDelete(oldFile);
            }
            //生成代码20%-80%
            String genDir = this.genProjectCodeIfNotExists(projectId, templateId, progressConsumer);
            this.progressing(progressConsumer, 81, 85, 1, "拷贝新生成的代码");
            FileUtils.copyDirectory(new File(genDir), repoDir);
        } catch (IOException e) {
            LOGGER.error("IO异常", e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "操作失败");
        }
    }

    /**
     * 获取认证DTO
     *
     * @param project
     * @return
     */
    private GitCredentialDTO getCredentialDTO(MetaProjectPO project) {
        if (StringUtils.isBlank(project.getUsername())) {
            return null;
        }
        if (StringUtils.isBlank(project.getPassword())) {
            return new GitCredentialDTO(project.getUsername(), "");
        }
        String password;
        try {
            password = AESSecurityUtil.decrypt(project.getPassword(), generateProperties.getAesKey());
        } catch (Exception e) {
            LOGGER.error("密码解密异常", e);
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "密码解密异常");
        }
        return new GitCredentialDTO(project.getUsername(), password);
    }


    /**
     * 执行进度通知
     *
     * @param progressConsumer
     * @param addPercent
     * @param msg
     */
    private void progressing(Consumer<ProgressVO> progressConsumer,
                             int minPercent, int maxPercent,
                             int addPercent, String msg) {
        if (progressConsumer != null) {
            progressConsumer.accept(ProgressVO.progressing(minPercent, maxPercent, addPercent, msg));
        }
    }


}
