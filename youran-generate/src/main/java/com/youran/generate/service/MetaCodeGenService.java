package com.youran.generate.service;

import com.google.common.collect.Lists;
import com.youran.common.constant.BoolConst;
import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.util.AESSecurityUtil;
import com.youran.common.util.DateUtil;
import com.youran.common.util.TempDirUtil;
import com.youran.generate.config.GenerateProperties;
import com.youran.generate.constant.DevMode;
import com.youran.generate.constant.TemplateEnum;
import com.youran.generate.constant.TemplateType;
import com.youran.generate.exception.SkipCurrentException;
import com.youran.generate.pojo.dto.GitCredentialDTO;
import com.youran.generate.pojo.po.GenHistoryPO;
import com.youran.generate.pojo.po.MetaConstPO;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.vo.ProgressVO;
import com.youran.generate.template.context.BaseContext;
import com.youran.generate.template.context.ConstContext;
import com.youran.generate.template.context.EntityContext;
import com.youran.generate.util.FreeMakerUtil;
import com.youran.generate.util.Zip4jUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;


/**
 * <p>Title:代码生成的核心service</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/14
 */
@Service
public class MetaCodeGenService implements InitializingBean {

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
    /**
     * 本系统名称，这里用于指定代码生成目录的父文件夹名称
     */
    @Value("${spring.application.name}")
    private String appName;

    /**
     * 启动以后清空代码目录
     * /[tmp目录]/[spring.application.name]
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // 获取代码目录
        String appCodeDir = TempDirUtil.getTmpDir(appName, false, false);
        File tmpDirFile = new File(appCodeDir);
        FileUtils.deleteDirectory(tmpDirFile);
    }

    /**
     * 输出建表语句
     * @param projectId
     */
    public String genSql(Integer projectId) {
        // 获取组装后的项目
        MetaProjectPO project = metaQueryAssembleService.getAssembledProject(projectId,
            false,true,false, false, false);
        return getSqlText(project);
    }

    /**
     * 打印sql脚本
     * @param project
     * @return
     */
    private String getSqlText(MetaProjectPO project) {
        String text = FreeMakerUtil.writeToStr("root/"+ TemplateEnum.SQL.getTemplate(), new BaseContext(project));
        LOGGER.debug("------打印生成sql脚本-----");
        LOGGER.debug(text);
        return text;
    }

    /**
     * 生成代码压缩包
     * @param projectId
     * @param progressConsumer 实时反馈进度
     * @return
     */
    public File genCodeZip(Integer projectId, Consumer<ProgressVO> progressConsumer) {
        String tmpDir = this.genProjectCodeIfNotExists(projectId,progressConsumer);
        //压缩src目录到zip文件
        this.progressing(progressConsumer,90, 99 ,1, "将项目打包成zip格式");
        String outFilePath = tmpDir + ".zip";
        Zip4jUtil.compressFolder(tmpDir, outFilePath);
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
                this.progressing(progressConsumer,95,99, 1,"全部替换开发工程" + devProjectDir);
                FileUtils.deleteDirectory(new File(devProjectDir));
                FileUtils.copyDirectory(new File(tmpDir), new File(devProjectDir));
            } else if (generateProperties.getDevMode() == DevMode.INCREMENT_REPLACE) {
                if (StringUtils.isBlank(devProjectDir)) {
                    throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR,
                        "请配置本地开发工程路径youran.generate.devProjectDir");
                }
                LOGGER.debug("------部分替换开发工程：" + devProjectDir);
                this.progressing(progressConsumer,95, 99, 1,"部分替换开发工程" + devProjectDir);
                this.compareAndCoverFile(new File(tmpDir), new File(devProjectDir));
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        //返回zip文件
        return new File(outFilePath);
    }


    /**
     * 如果当天尚未生成过最新版本的代码，则生成代码
     * 总体进度20%-80%之间
     * @param projectId 项目id
     * @param progressConsumer 进度条
     * @return 代码目录
     */
    public String genProjectCodeIfNotExists(Integer projectId,Consumer<ProgressVO> progressConsumer){
        MetaProjectPO project = metaProjectService.getProject(projectId,true);
        // 获取最新代码目录
        String projectDir = this.getProjectRecentDir(project);
        File dir = new File(projectDir);
        // 如果当天尚未生成过同一个版本的代码，则执行代码生成
        if(!dir.exists()){
            try {
                this.doGenCode(projectDir,projectId,progressConsumer);
            } catch (Exception e) {
                LOGGER.error("代码生成异常", e);
                try {
                    FileUtils.deleteDirectory(dir);
                } catch (IOException e1) {
                    LOGGER.error("代码生成异常回删目录失败",e1);
                }
                if(e instanceof BusinessException){
                    throw e;
                }else {
                    throw new BusinessException("代码生成异常");
                }
            }
        }else{
            LOGGER.info("代码已经存在，无需生成：{}",projectDir);
        }
        return projectDir;
    }

    /**
     * 获取最新代码目录
     * @param project
     * @return /[tmp目录]/[spring.application.name]/[youran.version]/[projectId]/[projectVersion]
     */
    public String getProjectRecentDir(MetaProjectPO project){
        String projectDir = TempDirUtil.getTmpDir(appName, false, false)
            +File.separator+generateProperties.getVersion()
            +File.separator+project.getProjectId()
            +File.separator+project.getProjectVersion();
        return projectDir;
    }

    /**
     * 在指定目录中生成代码
     * @param projectDir
     * @param projectId
     * @param progressConsumer
     */
    private void doGenCode(String projectDir, Integer projectId,Consumer<ProgressVO> progressConsumer){
        // 获取组装后的项目
        this.progressing(progressConsumer,20,80,1,"获取并组装项目元数据");
        MetaProjectPO project = metaQueryAssembleService.getAssembledProject(projectId,
            true,true,true, true, true);
        LOGGER.debug("------代码生成临时路径：" + projectDir);
        this.progressing(progressConsumer,20,80,1,"开始渲染代码");
        for (TemplateEnum templateEnum : TemplateEnum.values()) {
            try {
                // 睡20毫秒，故意慢一点，好让前端进度条反应缓慢增长
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //生成全局文件
            this.progressing(progressConsumer,25,80, 2,"代码渲染中");
            if (templateEnum.getType() == TemplateType.COMMON) {
                BaseContext context = new BaseContext(project);
                this.renderFTL(context,templateEnum,projectDir);
            } else if (templateEnum.getType() == TemplateType.ENTITY) {
                //生成实体模版文件
                for (MetaEntityPO metaEntityPO : project.getEntities()) {
                    EntityContext context = new EntityContext(project,metaEntityPO);
                    this.renderFTL(context,templateEnum,projectDir);
                }
            } else if (templateEnum.getType() == TemplateType.CONST) {
                //生成枚举模版文件
                for (MetaConstPO metaConstPO : project.getConsts()) {
                    ConstContext context = new ConstContext(project,metaConstPO);
                    this.renderFTL(context,templateEnum,projectDir);
                }
            }
        }
    }


    /**
     * 对比原目录下文件与目标目录，并覆盖
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
        List<String> file1Content = FileUtils.readLines(file1,"utf-8");
        List<String> file2Content = FileUtils.readLines(file2,"utf-8");
        for (int i = 0; i < file1Content.size(); i++) {
            String l1 = file1Content.get(i);
            String l2 = file2Content.get(i);
            if (l1.indexOf("* Create Time") >= 0) {
                continue;
            }
            if (!Objects.equals(l1, l2)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 执行文件覆盖
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
     * @param context 上下文信息
     * @param templateEnum 模板枚举
     * @param projectDir 代码输出目录
     */
    private void renderFTL(BaseContext context, TemplateEnum templateEnum, String projectDir) {
        LOGGER.debug("------开始渲染" + templateEnum.name() + "------");
        try {
            String text = FreeMakerUtil.writeToStr("root/"+templateEnum.getTemplate(), context);
            LOGGER.debug(text);
            String outFilePath = this.renderCodeFilePath(context, templateEnum, projectDir);
            LOGGER.debug("输出代码文件：{}",outFilePath);
            this.writeToFile(text, outFilePath);
        } catch (SkipCurrentException e) {
            return;
        }
    }

    /**
     * 渲染代码文件输出路径
     * @param context 上下文信息
     * @param templateEnum 模板枚举
     * @param projectDir 代码输出目录
     * @return 代码文件地址
     */
    private String renderCodeFilePath(BaseContext context, TemplateEnum templateEnum, String projectDir){
        String packageName = context.getPackageName();
        if (StringUtils.isBlank(packageName)) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR,"包名未设置");
        }
        String templatePath = templateEnum.getTemplate()
                .replace("{commonModule}",context.getProjectNameSplit()+"-common")
                .replace("{coreModule}",context.getProjectNameSplit()+"-core")
                .replace("{webModule}",context.getProjectNameSplit()+"-web")
                .replace("{packageName}", packageName.replaceAll("\\.", "/"))
                .replace("{commonPackage}",context.getCommonPackage().replaceAll("\\.", "/"))
                .replace("{ProjectName}", context.getProjectNameUpper())
                .replace("{projectName}", context.getProjectName())
                .replace("{project-name}", context.getProjectNameSplit());
        if (context instanceof EntityContext) {
            EntityContext entityContext = (EntityContext) context;
            templatePath = templatePath.replace("{ClassName}", entityContext.getClassNameUpper());
        }
        if (context instanceof ConstContext) {
            ConstContext constContext = (ConstContext) context;
            templatePath = templatePath.replace("{EnumName}", constContext.getConstNameUpper());
        }
        templatePath = templatePath.substring(0, templatePath.lastIndexOf("."));
        return projectDir + "/" + templatePath;
    }

    /**
     * 将文本写入文件
     * @param text         文本内容
     * @param outFilePath       文件路径
     */
    private void writeToFile(String text, String outFilePath) {
        File file = new File(outFilePath);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            FileUtils.write(file, text,"UTF-8");
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR,"写文件异常");
        }
    }

    /**
     * sql预览
     * @param entityId
     * @return
     */
    public String sqlPreview(Integer entityId) {
        MetaEntityPO metaEntityPO = metaQueryAssembleService.getAssembledEntity(entityId);
        MetaProjectPO project = metaProjectService.getProject(metaEntityPO.getProjectId(),true);
        project.setEntities(Lists.newArrayList(metaEntityPO));
        return getSqlText(project);
    }

    /**
     * 提交到仓库
     * @param projectId
     * @param progressConsumer
     * @return
     */
    public GenHistoryPO gitCommit(Integer projectId,Consumer<ProgressVO> progressConsumer) {
        MetaProjectPO project = metaProjectService.getProject(projectId,true);
        Integer remote = project.getRemote();
        if(BoolConst.isFalse(remote)){
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR,"当前项目未开启Git仓库");
        }
        String remoteUrl = project.getRemoteUrl();
        if(StringUtils.isBlank(remoteUrl)){
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR,"仓库地址为空");
        }
        Date now = new Date();
        Integer lastHistoryId = project.getLastHistoryId();
        String oldBranchName = null;
        if(lastHistoryId!=null){
            GenHistoryPO genHistory = genHistoryService.getGenHistory(lastHistoryId, true);
            genHistoryService.checkVersion(project,genHistory);
            oldBranchName = genHistory.getBranch();
        }
        String newBranchName = "auto"+ DateUtil.getDateStr(now,"yyyyMMddHHmmss");
        GitCredentialDTO credential = this.getCredentialDTO(project);
        this.progressing(progressConsumer,5,10,1,"克隆远程仓库");
        String repository = gitService.cloneRemoteRepository(project.getProjectName(), remoteUrl,
            credential, oldBranchName, newBranchName);
        File repoDir = new File(repository);
        File[] oldFiles = repoDir.listFiles((dir, name) -> !".git".equals(name));
        try {
            this.progressing(progressConsumer,6, 10, 1,"清空旧代码");
            for (File oldFile : oldFiles) {
                FileUtils.forceDelete(oldFile);
            }
            //生成代码20%-80%
            String genDir = this.genProjectCodeIfNotExists(projectId,progressConsumer);
            this.progressing(progressConsumer,81,85,1,"拷贝新生成的代码");
            FileUtils.copyDirectory(new File(genDir), repoDir);
        } catch (IOException e) {
            LOGGER.error("IO异常",e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR,"操作失败");
        }
        this.progressing(progressConsumer,90,99,1,"提交到远程仓库");
        String commit = gitService.commitAll(repository,
            DateUtil.getDateStr(now,"yyyy-MM-dd HH:mm:ss")+"自动生成代码",
            credential);
        // 创建提交历史
        GenHistoryPO history = genHistoryService.save(project, commit, newBranchName);
        // 更新项目的最终提交历史
        metaProjectService.updateLastHistory(projectId,history.getHistoryId());
        return history;
    }

    /**
     * 获取认证DTO
     * @param project
     * @return
     */
    private GitCredentialDTO getCredentialDTO(MetaProjectPO project){
        if(StringUtils.isBlank(project.getUsername())){
            return null;
        }
        if(StringUtils.isBlank(project.getPassword())){
            return new GitCredentialDTO(project.getUsername(), "");
        }
        String password;
        try {
            password = AESSecurityUtil.decrypt(project.getPassword(), generateProperties.getAesKey());
        } catch (Exception e) {
            LOGGER.error("密码解密异常",e);
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR,"密码解密异常");
        }
        return new GitCredentialDTO(project.getUsername(), password);
    }


    /**
     * 执行进度通知
     * @param progressConsumer
     * @param addPercent
     * @param msg
     */
    private void progressing(Consumer<ProgressVO> progressConsumer,
                             int minPercent, int maxPercent,
                             int addPercent, String msg){
        if(progressConsumer!=null){
            progressConsumer.accept(ProgressVO.progressing(minPercent,maxPercent,addPercent,msg));
        }
    }


}
