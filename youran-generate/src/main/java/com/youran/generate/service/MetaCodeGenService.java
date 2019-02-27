package com.youran.generate.service;

import com.google.common.collect.Lists;
import com.youran.common.constant.BoolConst;
import com.youran.common.util.AESSecurityUtil;
import com.youran.common.util.DateUtil;
import com.youran.common.util.H2Util;
import com.youran.generate.config.GenerateProperties;
import com.youran.generate.constant.TemplateEnum;
import com.youran.generate.constant.TemplateType;
import com.youran.generate.exception.GenerateException;
import com.youran.generate.pojo.dto.GitCredentialDTO;
import com.youran.generate.pojo.vo.ProgressVO;
import com.youran.generate.pojo.po.GenHistoryPO;
import com.youran.generate.pojo.po.MetaConstPO;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.template.BaseModel;
import com.youran.generate.pojo.template.ConstModel;
import com.youran.generate.pojo.template.EntityModel;
import com.youran.generate.util.FreeMakerUtil;
import com.youran.generate.util.Zip4jUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class MetaCodeGenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetaCodeGenService.class);

    @Autowired
    private MetaQueryAssembleService metaQueryAssembleService;
    @Autowired
    private MetaProjectService metaProjectService;
    @Autowired
    private JGitService jGitService;
    @Autowired
    private GenerateProperties generateProperties;
    @Autowired
    private GenHistoryService genHistoryService;
    @Value("${spring.application.name}")
    private String appName;

    /**
     * 输出建表语句
     * @param projectId
     */
    public String genSql(Integer projectId) {
        // 获取组装后的项目
        MetaProjectPO project = metaQueryAssembleService.getAssembledProject(projectId,false,true,false);
        return getSqlText(project);
    }

    /**
     * 打印sql脚本
     * @param project
     * @return
     */
    private String getSqlText(MetaProjectPO project) {
        String text = FreeMakerUtil.writeToStr("root/"+ TemplateEnum.SQL.getTemplate(), new BaseModel(project));
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
        String tmpDir = this.doGenCode(projectId,progressConsumer);
        //压缩src目录到zip文件
        this.progressing(progressConsumer,1,"将项目打包成zip格式");
        String outFilePath = tmpDir + ".zip";
        Zip4jUtil.compressFolder(tmpDir, outFilePath);
        //删除临时目录
        try {
            //方便本地调试，直接将代码生成在项目中
            String devProjectDir = generateProperties.getDevProjectDir();
            if (generateProperties.getDevMode() == 1) {
                if (StringUtils.isBlank(devProjectDir)) {
                    throw new GenerateException("请配置本地开发工程路径youran.generate.devProjectDir");
                }
                LOGGER.debug("------全部替换开发工程：" + devProjectDir);
                this.progressing(progressConsumer,1,"全部替换开发工程" + devProjectDir);
                FileUtils.deleteDirectory(new File(devProjectDir));
                FileUtils.copyDirectory(new File(tmpDir), new File(devProjectDir));
            } else if (generateProperties.getDevMode() == 2) {
                if (StringUtils.isBlank(devProjectDir)) {
                    throw new GenerateException("请配置本地开发工程路径youran.generate.devProjectDir");
                }
                LOGGER.debug("------部分替换开发工程：" + devProjectDir);
                this.progressing(progressConsumer,1,"部分替换开发工程" + devProjectDir);
                this.compareAndCoverFile(new File(tmpDir), new File(devProjectDir));
            }
            if (generateProperties.isDelTemp()) {
                LOGGER.debug("------删除临时文件夹：" + tmpDir);
                this.progressing(progressConsumer,1 ,"删除临时文件夹");
                FileUtils.deleteDirectory(new File(tmpDir));
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        //返回zip文件
        return new File(outFilePath);
    }





    private String doGenCode(Integer projectId,Consumer<ProgressVO> progressConsumer){
        // 获取组装后的项目
        this.progressing(progressConsumer,1,"获取并组装项目元数据");
        MetaProjectPO project = metaQueryAssembleService.getAssembledProject(projectId,true,true,true);
        String tmpDir = H2Util.getTmpDir(appName, true, true);
        LOGGER.debug("------代码生成临时路径：" + tmpDir);
        this.progressing(progressConsumer,0,"渲染代码模板");
        for (TemplateEnum templateEnum : TemplateEnum.values()) {
            try {
                // 睡20毫秒，故意慢一点，好让前端进度条反应缓慢增长
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.progressing(progressConsumer,1,null);
            //生成全局文件
            if (templateEnum.getType() == TemplateType.COMMON) {
                this.renderCommonFTL(project, tmpDir, templateEnum);
            } else if (templateEnum.getType() == TemplateType.ENTITY) {
                //生成实体模版文件
                for (MetaEntityPO metaEntityPO : project.getEntities()) {
                    this.renderEntityFTL(project, tmpDir, templateEnum, metaEntityPO);
                }
            } else if (templateEnum.getType() == TemplateType.CONST) {
                //生成实体模版文件
                for (MetaConstPO metaConstPO : project.getConsts()) {
                    this.renderConstFTL(project, tmpDir, templateEnum, metaConstPO);
                }
            }
        }
        return tmpDir;
    }




    //对比原目录下文件与目标目录，并覆盖
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
            //LOGGER.debug("relativePath={}",relativePath);
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

    private boolean compareFile(File file1, File file2) throws IOException {
        boolean file1Exists = file1.exists();
        if (file1Exists != file2.exists()) {
            return false;
        }
        if (!file1Exists) {
            return true;
        }
        List<String> file1Content = FileUtils.readLines(file1);
        List<String> file2Content = FileUtils.readLines(file2);
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

    private void doCover(File targetFile, File file) throws IOException {
        LOGGER.debug("进行文件覆盖={}", targetFile.getPath());
        FileUtils.copyFile(file, targetFile);
    }




    /**
     * 渲染实体freemarker模版
     * @param project      项目
     * @param tmpDir       输出临时目录
     */
    private void renderCommonFTL(MetaProjectPO project, String tmpDir, TemplateEnum templateEnum) {
        String outFilePath = this.getOutFilePath(project, tmpDir, templateEnum.getTemplate(), null, null);
        BaseModel model = new BaseModel(project);
        doRenderFTL(templateEnum, outFilePath, model);
    }

    /**
     * 渲染实体freemarker模版
     * @param project      项目
     * @param tmpDir       输出临时目录
     */
    private void renderEntityFTL(MetaProjectPO project, String tmpDir, TemplateEnum templateEnum, MetaEntityPO metaEntityPO) {
        String outFilePath = this.getOutFilePath(project, tmpDir, templateEnum.getTemplate(), metaEntityPO.getClassName(),null);
        EntityModel model = new EntityModel(project,metaEntityPO);
        doRenderFTL(templateEnum, outFilePath, model);
    }


    /**
     * 渲染常量freemarker模版
     * @param project      项目
     * @param tmpDir       输出临时目录
     * @param templateEnum 模版文件枚举
     * @param metaConstPO 当前常量
     */
    private void renderConstFTL(MetaProjectPO project, String tmpDir, TemplateEnum templateEnum, MetaConstPO metaConstPO) {
        String outFilePath = this.getOutFilePath(project, tmpDir, templateEnum.getTemplate(), null, metaConstPO.getConstName());
        ConstModel model = new ConstModel(project,metaConstPO);
        doRenderFTL(templateEnum, outFilePath, model);
    }

    /**
     * 执行模板渲染
     * @param templateEnum
     * @param outFilePath
     * @param model
     */
    private void doRenderFTL(TemplateEnum templateEnum, String outFilePath, BaseModel model) {
        LOGGER.debug("------开始渲染" + templateEnum.name() + "------");
        String text = FreeMakerUtil.writeToStr("root/"+templateEnum.getTemplate(), model);
        LOGGER.debug(text);
        this.writeToFile(text, outFilePath);
    }

    /**
     * 获取文件输出路径
     * @param project
     * @param tmpDir
     * @param templatePath
     * @param entityClassName
     * @param constName
     * @return
     */
    private String getOutFilePath(MetaProjectPO project,String tmpDir,String templatePath,String entityClassName,String constName){
        String packageName = project.getPackageName();
        if (StringUtils.isBlank(packageName)) {
            throw new GenerateException("包名未设置");
        }
        templatePath = templatePath
                .replace("{commonModule}",project.getProjectName()+"-common")
                .replace("{coreModule}",project.getProjectName()+"-core")
                .replace("{webModule}",project.getProjectName()+"-web")
                .replace("{packageName}", packageName.replaceAll("\\.", "/"))
                .replace("{commonPackage}",project.fetchCommonPackageName().replaceAll("\\.", "/"))
                .replace("{ProjectName}", StringUtils.capitalize(project.fetchNormalProjectName()))
                .replace("{projectName}", StringUtils.uncapitalize(project.fetchNormalProjectName()))
                .replace("{project-name}", project.getProjectName());
        if (entityClassName != null) {
            templatePath = templatePath.replace("{ClassName}", StringUtils.capitalize(entityClassName));
        }
        if (constName != null) {
            templatePath = templatePath.replace("{ConstName}", constName)
                    .replace("{EnumName}", constName);
        }
        templatePath = templatePath.substring(0, templatePath.lastIndexOf("."));
        return tmpDir + "/" + templatePath;
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
            throw new GenerateException("写文件异常");
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
        if(BoolConst.TRUE!=remote){
            throw new GenerateException("当前项目未开启Git仓库");
        }
        String remoteUrl = project.getRemoteUrl();
        if(StringUtils.isBlank(remoteUrl)){
            throw new GenerateException("仓库地址为空");
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
        this.progressing(progressConsumer,10,"克隆远程仓库");
        String repository = jGitService.cloneRemoteRepository(project.getProjectName(), remoteUrl,
            credential, oldBranchName, newBranchName);
        File repoDir = new File(repository);
        File[] oldFiles = repoDir.listFiles((dir, name) -> !name.equals(".git"));
        try {
            this.progressing(progressConsumer,1,"清空旧代码");
            for (File oldFile : oldFiles) {
                FileUtils.forceDelete(oldFile);
            }
            //生成代码
            String genDir = this.doGenCode(projectId,progressConsumer);
            this.progressing(progressConsumer,1,"拷贝新生成的代码");
            FileUtils.copyDirectory(new File(genDir), repoDir);
        } catch (IOException e) {
            LOGGER.error("IO异常",e);
            throw new GenerateException("操作失败");
        }
        this.progressing(progressConsumer,1,"提交到远程仓库");
        String commit = jGitService.commitAll(repository,
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
            throw new GenerateException("密码解密异常");
        }
        return new GitCredentialDTO(project.getUsername(), password);
    }


    /**
     * 执行进度通知
     * @param progressConsumer
     * @param addPercent
     * @param msg
     */
    private void progressing(Consumer<ProgressVO> progressConsumer,int addPercent,String msg){
        if(progressConsumer!=null){
            progressConsumer.accept(ProgressVO.progressing(addPercent,msg));
        }
    }

}
