package com.youran.generate.service;

import com.google.common.collect.Lists;
import com.youran.common.constant.BoolConst;
import com.youran.common.util.AESSecurityUtil;
import com.youran.common.util.DateUtil;
import com.youran.common.util.H2Util;
import com.youran.generate.config.GenerateProperties;
import com.youran.generate.constant.*;
import com.youran.generate.exception.GenerateException;
import com.youran.generate.pojo.dto.GitCredentialDTO;
import com.youran.generate.pojo.po.*;
import com.youran.generate.util.FreeMakerUtil;
import com.youran.generate.util.MetadataUtil;
import com.youran.generate.util.Zip4jUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Title: 代码生成的核心service
 * Description:
 * Author: cbb
 * Create Time:2017/5/14 10:29
 */
@Service
public class MetaCodeGenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetaCodeGenService.class);

    @Autowired
    private MetaQueryAssembleService metaQueryAssembleService;
    @Autowired
    private MetaEntityService metaEntityService;
    @Autowired
    private MetaProjectService metaProjectService;
    @Autowired
    private MetaManyToManyService metaManyToManyService;
    @Autowired
    private MetaConstService metaConstService;
    @Autowired
    private JGitService jGitService;
    @Value("${spring.application.name}")
    private String appName;
    @Autowired
    private GenerateProperties generateProperties;
    @Autowired
    private GenHistoryService genHistoryService;

    /**
     * 输出建表语句
     * @param projectId
     */
    public String genSql(Integer projectId) {
        MetaProjectPO project = metaProjectService.getProject(projectId,true);
        List<Integer> entityIds = metaEntityService.findIdsByProject(projectId);
        if (CollectionUtils.isEmpty(entityIds)) {
            return "";
        }
        List<MetaEntityPO> metaEntities = entityIds
                .stream()
                .map(metaQueryAssembleService::getAssembledEntity)
                .collect(Collectors.toList());
        List<MetaManyToManyPO> manyToManies = metaManyToManyService.findByProjectId(projectId);
        // 组装多对多对象引用
        metaQueryAssembleService.assembleManyToManyWithEntities(metaEntities, manyToManies);
        project.setMtms(manyToManies);
        project.setEntities(metaEntities);
        metaQueryAssembleService.checkAssembledProject(project,false);
        Map<String, Object> map = this.buildTemplateParamMap(project, null, null);
        String text = FreeMakerUtil.writeToStr("root/{webModule}/src/test/resources/DB/{projectName}.sql.ftl", map);
        LOGGER.debug("------打印生成sql脚本-----");
        LOGGER.debug(text);
        return text;
    }






    /**
     * 生成代码压缩包
     * @param projectId
     * @return
     */
    public File genCodeZip(Integer projectId) {

        String tmpDir = this.doGenCode(projectId);
        //压缩src目录到zip文件
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
                FileUtils.deleteDirectory(new File(devProjectDir));
                FileUtils.copyDirectory(new File(tmpDir), new File(devProjectDir));
            } else if (generateProperties.getDevMode() == 2) {
                if (StringUtils.isBlank(devProjectDir)) {
                    throw new GenerateException("请配置本地开发工程路径youran.generate.devProjectDir");
                }
                LOGGER.debug("------部分替换开发工程：" + devProjectDir);
                this.compareAndCoverFile(new File(tmpDir), new File(devProjectDir));
            }
            if (generateProperties.isDelTemp()) {
                LOGGER.debug("------删除临时目录：" + tmpDir);
                FileUtils.deleteDirectory(new File(tmpDir));
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        //返回zip文件
        return new File(outFilePath);
    }


    private String doGenCode(Integer projectId){
        MetaProjectPO project = metaProjectService.getProject(projectId,true);
        // 查询实体id列表
        List<Integer> entityIds = metaEntityService.findIdsByProject(projectId);
        if (CollectionUtils.isEmpty(entityIds)) {
            throw new GenerateException("项目中没有实体");
        }
        // 获取组装后的实体列表
        List<MetaEntityPO> metaEntities = entityIds
            .stream()
            .map(metaQueryAssembleService::getAssembledEntity).collect(Collectors.toList());
        // 组装外键实体和外键字段
        metaQueryAssembleService.assembleForeign(metaEntities);
        // 查询常量id列表
        List<Integer> constIds = metaConstService.findIdsByProject(projectId);
        // 获取组装后的常量列表
        List<MetaConstPO> metaConstPOS = constIds
            .stream()
            .map(metaQueryAssembleService::getAssembledConst).collect(Collectors.toList());
        // 查询多对多列表
        List<MetaManyToManyPO> manyToManies = metaManyToManyService.findByProjectId(projectId);
        // 组装多对多持有引用
        metaQueryAssembleService.assembleManyToManyWithEntities(metaEntities, manyToManies);
        project.setMtms(manyToManies);
        project.setEntities(metaEntities);
        project.setConsts(metaConstPOS);
        // 校验组装后的项目完整性
        metaQueryAssembleService.checkAssembledProject(project,true);

        String tmpDir = H2Util.getTmpDir(appName, true, true);
        LOGGER.debug("------代码生成临时路径：" + tmpDir);
        for (TemplateEnum templateEnum : TemplateEnum.values()) {
            //生成全局文件
            if (templateEnum.getType() == TemplateType.COMMON) {
                this.renderFTL(project, tmpDir, templateEnum, null, null);
            } else if (templateEnum.getType() == TemplateType.ENTITY) {
                //生成实体模版文件
                for (MetaEntityPO metaEntityPO : metaEntities) {
                    this.renderFTL(project, tmpDir, templateEnum, metaEntityPO, null);
                }
            } else if (templateEnum.getType() == TemplateType.CONST) {
                //生成实体模版文件
                for (MetaConstPO metaConstPO : metaConstPOS) {
                    this.renderFTL(project, tmpDir, templateEnum, null, metaConstPO);
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
     * 渲染freemarker模版
     * @param project      项目
     * @param outDir       输出临时目录
     * @param templateEnum 模版文件枚举
     * @param singleEntity 当前实体
     */
    private void renderFTL(MetaProjectPO project, String outDir, TemplateEnum templateEnum, MetaEntityPO singleEntity, MetaConstPO singleConst) {
        Map<String, Object> map = buildTemplateParamMap(project, singleEntity, singleConst);
        LOGGER.debug("------开始渲染" + templateEnum.name() + "------");
        String text = FreeMakerUtil.writeToStr("root/"+templateEnum.getTemplate(), map);
        LOGGER.debug(text);
        this.writeToFile(project, text, outDir, templateEnum.getTemplate(), singleEntity, singleConst);
    }

    /**
     * 构建模版参数map
     * @param project      项目
     * @param singleEntity 当前实体
     * @return
     */
    private Map<String, Object> buildTemplateParamMap(MetaProjectPO project, MetaEntityPO singleEntity, MetaConstPO singleConst) {
        Map<String, Object> map = new HashMap<>();
        //当前实体
        map.put("metaEntity", singleEntity);
        //当前常量
        map.put("metaConst", singleConst);
        //所有实体
        map.put("metaEntities", project.getEntities());
        //所有常量
        map.put("metaConsts", project.getConsts());
        //包名
        map.put("packageName", project.getPackageName());
        //通用模块名
        map.put("commonPackage", project.fetchCommonPackageName());
        //项目名：驼峰格式-首字母小写
        map.put("projectName", project.fetchNormalProjectName());
        //项目名：驼峰格式-首字母大写
        map.put("ProjectName", StringUtils.capitalize(project.fetchNormalProjectName()));
        //项目名：短横杠分割
        map.put("projectNameSplit", project.getProjectName());
        //原始模块名
        map.put("originProjectName", project.getProjectName());
        //groupId
        map.put("groupId", project.getGroupId());
        //作者
        map.put("author", project.getAuthor());
        //作者
        map.put("createDate", project.getCreateDate());
        //多对多关联
        map.put("mtms", project.getMtms());
        //注入静态类
        map.put("MetadataUtil", FreeMakerUtil.getStaticModel(MetadataUtil.class));
        //注入常量类
        map.put("JFieldType", FreeMakerUtil.getStaticModel(JFieldType.class));
        //注入常量类
        map.put("QueryType", FreeMakerUtil.getStaticModel(QueryType.class));
        //注入常量类
        map.put("MetaSpecialField", FreeMakerUtil.getStaticModel(MetaSpecialField.class));
        //注意：此处添加新变量时，请在freemarker_implicit.ftl中也添加上对应变量，可以防止idea下的ftl文件有错误提示
        return map;
    }


    /**
     * 将文本文件写入临时目录
     * @param project      项目
     * @param text         渲染出的文本
     * @param outDir       代码输出临时目录
     * @param templatePath 模版文件路径
     * @param metaEntityPO   实体
     */
    private void writeToFile(MetaProjectPO project, String text, String outDir, String templatePath, MetaEntityPO metaEntityPO, MetaConstPO singleConst) {
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
        if (metaEntityPO != null) {
            templatePath = templatePath.replace("{ClassName}", StringUtils.capitalize(metaEntityPO.getClassName()));
        }
        if (singleConst != null) {
            templatePath = templatePath.replace("{ConstName}", singleConst.getConstName())
                    .replace("{EnumName}", singleConst.getConstName());
        }
        templatePath = templatePath.substring(0, templatePath.lastIndexOf("."));


        String filePath = outDir + "/" + templatePath;
        File file = new File(filePath);
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
        MetaEntityPO metaEntityPO = metaEntityService.getEntity(entityId,true);
        MetaProjectPO project = metaProjectService.getProject(metaEntityPO.getProjectId(),true);
        List<MetaEntityPO> metaEntities = Lists.newArrayList(metaQueryAssembleService.assembleEntity(metaEntityPO));
        project.setEntities(metaEntities);
        metaQueryAssembleService.checkAssembledProject(project,false);
        Map<String, Object> map = this.buildTemplateParamMap(project, null, null);
        String text = FreeMakerUtil.writeToStr("root/{webModule}/src/test/resources/DB/{projectName}.sql.ftl", map);
        LOGGER.debug("------打印生成sql脚本-----");
        LOGGER.debug(text);
        return text;
    }

    /**
     * 提交到仓库
     * @param projectId
     * @return
     */
    public void gitCommit(Integer projectId) {
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
        String repository = jGitService.cloneRemoteRepository(project.getProjectName(), remoteUrl,
            credential, oldBranchName, newBranchName);
        File repoDir = new File(repository);
        File[] oldFiles = repoDir.listFiles((dir, name) -> !name.equals(".git"));
        try {
            for (File oldFile : oldFiles) {
                FileUtils.forceDelete(oldFile);
            }
            //生成代码
            String genDir = this.doGenCode(projectId);
            FileUtils.copyDirectory(new File(genDir), repoDir);
        } catch (IOException e) {
            LOGGER.error("IO异常",e);
            throw new GenerateException("操作失败");
        }
        String commit = jGitService.commitAll(repository,
            DateUtil.getDateStr(now,"yyyy-MM-dd HH:mm:ss")+"自动生成代码",
            credential);
        // 创建提交历史
        GenHistoryPO history = genHistoryService.save(project, commit, newBranchName);
        // 更新项目的最终提交历史
        metaProjectService.updateLastHistory(projectId,history.getHistoryId());
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

}
