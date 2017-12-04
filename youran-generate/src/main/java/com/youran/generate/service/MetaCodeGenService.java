package com.youran.generate.service;

import com.youran.common.constant.BoolConst;
import com.youran.common.util.H2Util;
import com.youran.generate.config.GenerateProperties;
import com.youran.generate.constant.*;
import com.youran.generate.dao.MetaConstDAO;
import com.youran.generate.dao.MetaEntityDAO;
import com.youran.generate.dao.MetaManyToManyDAO;
import com.youran.generate.dao.MetaProjectDAO;
import com.youran.generate.exception.GenerateException;
import com.youran.generate.pojo.po.MetaConstPO;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaManyToManyPO;
import com.youran.generate.pojo.po.MetaProjectPO;
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

    private static final Logger logger = LoggerFactory.getLogger(MetaCodeGenService.class);

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

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    private GenerateProperties generateProperties;

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
        this.fillEntityHoldRefs(metaEntities, manyToManies);//填充多对多持有引用
        project.setMtms(manyToManies);
        project.setEntities(metaEntities);
        this.checkProject(project,false);
        Map<String, Object> map = this.buildTemplateParamMap(project, null, null);
        String text = FreeMakerUtil.writeToStr("test_resources/DB/{projectName}.sql.ftl", map);
        logger.debug("------打印生成sql脚本-----");
        logger.debug(text);
        return text;
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
        MetaProjectPO project = metaProjectDAO.findById(projectId);
        if (project == null) {
            throw new GenerateException("项目不存在");
        }
        List<Integer> entityIds = metaEntityDAO.findIdsByProject(projectId);
        if (CollectionUtils.isEmpty(entityIds)) {
            return null;
        }
        String tmpDir = H2Util.getTmpDir(appName, true, true);
        logger.debug("------代码生成临时路径：" + tmpDir);
        List<MetaEntityPO> metaEntities = entityIds
                .stream()
                .map(metadataQueryService::getEntityWithAll).collect(Collectors.toList());

        List<Integer> constIds = metaConstDAO.findIdsByProject(projectId);
        List<MetaConstPO> metaConstPOS = constIds
                .stream()
                .map(metadataQueryService::getConstWithAll).collect(Collectors.toList());
        List<MetaManyToManyPO> manyToManies = metaManyToManyDAO.findByProjectId(projectId);
        this.fillEntityHoldRefs(metaEntities, manyToManies);//填充多对多持有引用
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
                logger.debug("------全部替换开发工程：" + devProjectDir);
                FileUtils.deleteDirectory(new File(devProjectDir + File.separator + "src"));
                FileUtils.deleteQuietly(new File(devProjectDir + File.separator + "pom.xml"));
                FileUtils.copyFile(new File(tmpDir + File.separator + "pom.xml"), new File(devProjectDir + File.separator + "pom.xml"));
                FileUtils.copyDirectory(new File(tmpDir + File.separator + "src"), new File(devProjectDir + File.separator + "src"));
            } else if (generateProperties.getDevMode() == 2) {
                if (StringUtils.isBlank(devProjectDir)) {
                    throw new GenerateException("请配置本地开发工程路径youran.generate.devProjectDir");
                }
                logger.debug("------部分替换开发工程：" + devProjectDir);
                this.compareAndCoverFile(new File(tmpDir + File.separator + "src"), new File(devProjectDir + File.separator + "src"));
            }
            if (generateProperties.isDelTemp()) {
                logger.debug("------删除临时目录：" + tmpDir);
                FileUtils.deleteDirectory(new File(tmpDir));
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        //返回zip文件
        return new File(outFilePath);
    }

    //校验项目完整性
    private void checkProject(MetaProjectPO project,boolean checkConst) {
        //TODO

    }


    //对比原目录下文件与目标目录，并覆盖
    private void compareAndCoverFile(File sourceDir, File targetDir) throws IOException {
        String sourcePath = sourceDir.getPath();
        String targetPath = targetDir.getPath();
        logger.debug("sourcePath={}", sourcePath);
        logger.debug("targetPath={}", targetPath);
        Iterator<File> fileIterator = FileUtils.iterateFiles(sourceDir, null, true);
        while (fileIterator.hasNext()) {
            File file = fileIterator.next();
            String path = file.getPath();
            String relativePath = path.substring(sourcePath.length());
            //logger.debug("relativePath={}",relativePath);
            File targetFile = new File(targetPath + relativePath);
            if (!targetFile.exists()) {
                logger.debug("目标文件不存在={}", targetPath + relativePath);
                this.doCover(targetFile, file);
            } else if (!compareFile(targetFile, file)) {
                logger.debug("文件内容不相等={}", targetPath + relativePath);
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
        logger.debug("进行文件覆盖={}", targetFile.getPath());
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
        logger.debug("------开始渲染" + templateEnum.name() + "------");
        String text = FreeMakerUtil.writeToStr(templateEnum.getTemplate(), map);
        logger.debug(text);
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
        //模块名
        map.put("projectName", project.fetchNormalProjectName());
        //原始模块名
        map.put("originProjectName", project.getProjectName());
        //作者
        map.put("author", project.getAuthor());
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
        return map;
    }


    /**
     * 将文本文件写入临时目录
     * @param project      项目
     * @param text         渲染出的文本
     * @param outDir       代码输出临时目录
     * @param templateFile 模版文件
     * @param metaEntityPO   元数据实体
     */
    private void writeToFile(MetaProjectPO project, String text, String outDir, String templateFile, MetaEntityPO metaEntityPO, MetaConstPO singleConst) {
        String packageName = project.getPackageName();
        if (StringUtils.isBlank(packageName)) {
            throw new GenerateException("包名未设置");
        }
        String root = templateFile.substring(0, templateFile.indexOf("/"));
        String templatePath = templateFile.substring(templateFile.indexOf("/") + 1);
        templatePath = templatePath.replace("{packageName}", packageName.replaceAll("\\.", "/"))
                .replace("{commonPackage}",project.fetchCommonPackageName().replaceAll("\\.", "/"))
                .replace("{ProjectName}", StringUtils.capitalize(project.fetchNormalProjectName()))
                .replace("{projectName}", StringUtils.uncapitalize(project.fetchNormalProjectName()));
        if (metaEntityPO != null) {
            templatePath = templatePath.replace("{ClassName}", StringUtils.capitalize(metaEntityPO.getClassName()));
        }
        if (singleConst != null) {
            templatePath = templatePath.replace("{ConstName}", singleConst.getConstName())
                    .replace("{EnumName}", singleConst.getConstName());
        }
        templatePath = templatePath.substring(0, templatePath.lastIndexOf("."));
        String rootPath;
        if (root.equals(TemplateRoot.SOURCES)) {
            rootPath = "/src/main/java/";
        } else if (root.equals(TemplateRoot.RESOURCES)) {
            rootPath = "/src/main/resources/";
        } else if (root.equals(TemplateRoot.TEST)) {
            rootPath = "/src/test/java/";
        } else if (root.equals(TemplateRoot.TEST_RESOURCES)) {
            rootPath = "/src/test/resources/";
        } else if (root.equals(TemplateRoot.POM)) {
            rootPath = "/";
        } else {
            throw new GenerateException("模版文件路径不合法，templateFile=" + templateFile);
        }


        String filePath = outDir + rootPath + templatePath;
        File file = new File(filePath);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            FileUtils.write(file, text,"UTF-8");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new GenerateException("写文件异常");
        }
    }

}
