package com.youran.generate.service;

import com.google.common.collect.Lists;
import com.youran.common.constant.BoolConst;
import com.youran.common.util.AESSecurityUtil;
import com.youran.common.util.DateUtil;
import com.youran.common.util.H2Util;
import com.youran.generate.config.GenerateProperties;
import com.youran.generate.constant.*;
import com.youran.generate.dao.*;
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
    private MetadataQueryService metadataQueryService;
    @Autowired
    private MetaEntityDAO metaEntityDAO;
    @Autowired
    private MetaProjectDAO metaProjectDAO;
    @Autowired
    private MetaManyToManyDAO metaManyToManyDAO;
    @Autowired
    private MetaConstDAO metaConstDAO;
    @Autowired
    private JGitService jGitService;
    @Value("${spring.application.name}")
    private String appName;
    @Autowired
    private GenerateProperties generateProperties;
    @Autowired
    private GenHistoryService genHistoryService;
    @Autowired
    private MetaCascadeExtDAO metaCascadeExtDAO;

    /**
     * 输出建表语句
     * @param projectId
     */
    public String genSql(Integer projectId) {
        MetaProjectPO project = metaProjectDAO.findById(projectId);
        if (project == null) {
            throw new GenerateException("项目不存在");
        }
        List<Integer> entityIds = metaEntityDAO.findIdsByProject(projectId);
        if (CollectionUtils.isEmpty(entityIds)) {
            return "";
        }
        List<MetaEntityPO> metaEntities = entityIds
                .stream()
                .map(metadataQueryService::getEntityWithAll)
                .collect(Collectors.toList());
        List<MetaManyToManyPO> manyToManies = metaManyToManyDAO.findByProjectId(projectId);
        //填充多对多持有引用
        this.fillEntityHoldRefs(metaEntities, manyToManies);
        project.setMtms(manyToManies);
        project.setEntities(metaEntities);
        this.checkProject(project,false);
        Map<String, Object> map = this.buildTemplateParamMap(project, null, null);
        String text = FreeMakerUtil.writeToStr("root/{webModule}/src/test/resources/DB/{projectName}.sql.ftl", map);
        LOGGER.debug("------打印生成sql脚本-----");
        LOGGER.debug(text);
        return text;
    }

    /**
     * 填充外键实体和外键字段
     */
    private void fillForeign(List<MetaEntityPO> metaEntities){
        for (MetaEntityPO metaEntity : metaEntities) {
            for (MetaFieldPO metaFieldPO : metaEntity.getFields()) {
                if(BoolConst.TRUE != metaFieldPO.getForeignKey()){
                    continue;
                }
                //查找当前外键字段对应的外键实体
                MetaEntityPO foreignEntity = this.findMetaEntityById(metaEntities, metaFieldPO.getForeignEntityId());
                metaFieldPO.setForeignEntity(foreignEntity);
                //获取外键关联的主键字段
                MetaFieldPO foreignField = foreignEntity.getPkField();
                if(!Objects.equals(foreignField.getFieldType(),metaFieldPO.getFieldType())){
                    throw new GenerateException("外键字段"+metaEntity.getTableName()+"."+metaFieldPO.getFieldName()+"与"
                            +foreignEntity.getTableName()+"."+foreignField.getFieldName()+"字段类型不一致");
                }
                if(!Objects.equals(foreignField.getJfieldType(),metaFieldPO.getJfieldType())){
                    throw new GenerateException("java字段"+metaEntity.getClassName()+"."+metaFieldPO.getJfieldName()+"与"
                            +foreignEntity.getClassName()+"."+foreignField.getJfieldName()+"字段类型不一致");
                }
                metaFieldPO.setForeignField(foreignField);
                // 填充级联扩展列表
                this.fillMetaCascadeExtList(metaFieldPO,foreignEntity.getFields());
                foreignEntity.addForeignField(metaFieldPO);
                foreignEntity.addForeignEntity(metaEntity);
            }
        }
    }

    /**
     * 填充级联扩展列表
     */
    private void fillMetaCascadeExtList(MetaFieldPO metaFieldPO,List<MetaFieldPO> foreignFields) {
        List<MetaCascadeExtPO> cascadeExts = metaCascadeExtDAO.findByFieldId(metaFieldPO.getFieldId());
        List<MetaCascadeExtPO> cascadeQueryExts = new ArrayList<>();
        List<MetaCascadeExtPO> cascadeShowExts = new ArrayList<>();
        List<MetaCascadeExtPO> cascadeListExts = new ArrayList<>();
        for (MetaCascadeExtPO cascadeExt : cascadeExts) {
            Optional<MetaFieldPO> first = foreignFields.stream()
                .filter(field -> field.getFieldId().equals(cascadeExt.getCascadeFieldId()))
                .findFirst();
            if(!first.isPresent()) {
                throw new GenerateException(metaFieldPO.getFieldDesc()+"的级联扩展字段有误");
            }
            cascadeExt.setCascadeField(first.get());
            if(BoolConst.TRUE==cascadeExt.getQuery()){
                cascadeQueryExts.add(cascadeExt);
            }
            if(BoolConst.TRUE==cascadeExt.getShow()){
                cascadeShowExts.add(cascadeExt);
            }
            if(BoolConst.TRUE==cascadeExt.getList()){
                cascadeListExts.add(cascadeExt);
            }
        }
        metaFieldPO.setCascadeExts(cascadeExts);
        metaFieldPO.setCascadeQueryExts(cascadeQueryExts);
        metaFieldPO.setCascadeShowExts(cascadeShowExts);
        metaFieldPO.setCascadeListExts(cascadeListExts);
    }

    /**
     * 从list中查找实体
     * @param metaEntities
     * @param entityId
     * @return
     */
    private MetaEntityPO findMetaEntityById(List<MetaEntityPO> metaEntities,Integer entityId){
        Optional<MetaEntityPO> first = metaEntities.stream().filter(entityPO -> entityPO.getEntityId().equals(entityId)).findFirst();
        if(first.isPresent()){
            return first.get();
        }
        throw new GenerateException("实体id有误，entityId="+entityId);
    }


    private void fillEntityHoldRefs(List<MetaEntityPO> metaEntities, List<MetaManyToManyPO> manyToManies) {
        if (CollectionUtils.isEmpty(manyToManies) || CollectionUtils.isEmpty(metaEntities)) {
            return;
        }
        //将实体列表转成map
        Map<Integer, MetaEntityPO> entityMap = metaEntities.stream()
                .collect(Collectors.toMap(MetaEntityPO::getEntityId, e -> e));
        for (MetaManyToManyPO manyToMany : manyToManies) {
            MetaEntityPO entity1 = entityMap.get(manyToMany.getEntityId1());
            MetaEntityPO entity2 = entityMap.get(manyToMany.getEntityId2());
            if (BoolConst.FALSE == manyToMany.getHoldRefer1()) {
                entity1.addUnHoldRefer(entity2);
                entity1.addUnHoldMtms(manyToMany);
            } else {
                entity1.addHoldRefer(entity2);
                entity1.addHoldMtms(manyToMany);
            }
            if (BoolConst.FALSE == manyToMany.getHoldRefer2()) {
                entity2.addUnHoldRefer(entity1);
                entity2.addUnHoldMtms(manyToMany);
            } else {
                entity2.addHoldRefer(entity1);
                entity2.addHoldMtms(manyToMany);
            }
            manyToMany.setRefer1(entity1);
            manyToMany.setRefer2(entity2);
        }
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
        MetaProjectPO project = metaProjectDAO.findById(projectId);
        if (project == null) {
            throw new GenerateException("项目不存在");
        }
        List<Integer> entityIds = metaEntityDAO.findIdsByProject(projectId);
        if (CollectionUtils.isEmpty(entityIds)) {
            throw new GenerateException("项目中没有实体");
        }
        String tmpDir = H2Util.getTmpDir(appName, true, true);
        LOGGER.debug("------代码生成临时路径：" + tmpDir);
        List<MetaEntityPO> metaEntities = entityIds
            .stream()
            .map(metadataQueryService::getEntityWithAll).collect(Collectors.toList());
        //填充外键相关属性
        this.fillForeign(metaEntities);
        List<Integer> constIds = metaConstDAO.findIdsByProject(projectId);
        List<MetaConstPO> metaConstPOS = constIds
            .stream()
            .map(metadataQueryService::getConstWithAll).collect(Collectors.toList());
        List<MetaManyToManyPO> manyToManies = metaManyToManyDAO.findByProjectId(projectId);
        //填充多对多持有引用
        this.fillEntityHoldRefs(metaEntities, manyToManies);
        project.setMtms(manyToManies);
        project.setEntities(metaEntities);
        project.setConsts(metaConstPOS);

        this.checkProject(project,true);

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

    //校验项目完整性
    private void checkProject(MetaProjectPO project,boolean checkConst) {
        List<MetaEntityPO> entities = project.getEntities();
        List<MetaConstPO> consts = project.getConsts();
        for (MetaEntityPO entity : entities) {
            List<MetaFieldPO> fields = entity.getFields();
            int pkCount = 0;
            int delSignCount = 0;
            int createByCount = 0;
            int createDateCount = 0;
            int operateByCount = 0;
            int operateDateCount = 0;
            int versionCount = 0;
            for (MetaFieldPO field : fields) {
                String specialField = field.getSpecialField();
                if(BoolConst.TRUE == field.getPrimaryKey()){
                    pkCount++;
                    if(StringUtils.isNotBlank(specialField)){
                        throw new GenerateException("实体【"+entity.getTitle()+"】的主键【"+field.getFieldDesc()+"】不可以是特殊字段");
                    }
                }
                if (Objects.equals(specialField, MetaSpecialField.DEL_SIGN)) {
                    delSignCount++;
                } else if (Objects.equals(specialField, MetaSpecialField.CREATE_BY)) {
                    createByCount++;
                } else if (Objects.equals(specialField, MetaSpecialField.CREATE_DATE)) {
                    createDateCount++;
                } else if (Objects.equals(specialField, MetaSpecialField.OPERATE_BY)) {
                    operateByCount++;
                } else if (Objects.equals(specialField, MetaSpecialField.OPERATE_DATE)) {
                    operateDateCount++;
                } else if (Objects.equals(specialField, MetaSpecialField.VERSION)) {
                    versionCount++;
                }
            }
            if(pkCount==0){
                throw new GenerateException("实体【"+entity.getTitle()+"】中未找到主键");
            }
            if(pkCount>1){
                throw new GenerateException("实体【"+entity.getTitle()+"】中存在"+pkCount+"个主键");
            }
            if(delSignCount>1){
                throw new GenerateException("实体【"+entity.getTitle()+"】中存在"+delSignCount+"个逻辑删除字段");
            }
            if(createByCount>1){
                throw new GenerateException("实体【"+entity.getTitle()+"】中存在"+createByCount+"个创建人员字段");
            }
            if(createDateCount>1){
                throw new GenerateException("实体【"+entity.getTitle()+"】中存在"+createDateCount+"个创建时间字段");
            }
            if(operateByCount>1){
                throw new GenerateException("实体【"+entity.getTitle()+"】中存在"+operateByCount+"个更新人员字段");
            }
            if(operateDateCount>1){
                throw new GenerateException("实体【"+entity.getTitle()+"】中存在"+operateDateCount+"个更新时间字段");
            }
            if(versionCount>1){
                throw new GenerateException("实体【"+entity.getTitle()+"】中存在"+versionCount+"个乐观锁版本字段");
            }
        }
        if(checkConst) {

        }


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
     * @param singleEntity 当前元数据实体
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
     * @param singleEntity 当前元数据实体
     * @return
     */
    private Map<String, Object> buildTemplateParamMap(MetaProjectPO project, MetaEntityPO singleEntity, MetaConstPO singleConst) {
        Map<String, Object> map = new HashMap<>();
        //当前元数据实体
        map.put("metaEntity", singleEntity);
        //当前元数据常量
        map.put("metaConst", singleConst);
        //所有元数据实体
        map.put("metaEntities", project.getEntities());
        //所有元数据常量
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
     * @param metaEntityPO   元数据实体
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
        MetaEntityPO metaEntityPO = metaEntityDAO.findById(entityId);
        if (metaEntityPO == null) {
            throw new GenerateException("实体不存在");
        }
        MetaProjectPO project = metaProjectDAO.findById(metaEntityPO.getProjectId());
        List<MetaEntityPO> metaEntities = Lists.newArrayList(metadataQueryService.fillEntity(metaEntityPO));
        project.setEntities(metaEntities);
        this.checkProject(project,false);
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
        MetaProjectPO project = metaProjectDAO.findById(projectId);
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

        GenHistoryPO history = genHistoryService.save(project, commit, newBranchName);

        project.setLastHistoryId(history.getHistoryId());
        metaProjectDAO.update(project);

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
