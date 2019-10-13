package com.youran.generate.service;

import com.youran.common.exception.BusinessException;
import com.youran.common.util.JsonUtil;
import com.youran.common.util.TempDirUtil;
import com.youran.generate.config.GenerateProperties;
import com.youran.generate.pojo.mapper.MetaConstDetailMapper;
import com.youran.generate.pojo.mapper.MetaConstMapper;
import com.youran.generate.pojo.mapper.MetaProjectMapper;
import com.youran.generate.pojo.po.*;
import com.youran.generate.util.Zip4jUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>Title: 元数据导入导出服务类</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 10/12/2019 21:20
 */
@Service
public class MetaImportExportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetaImportExportService.class);

    public static final String PROJECT_JSON_FILE = "project.json";
    public static final String CONST_JSON_FILE = "const.json";
    public static final String CONST_DETAIL_JSON_FILE = "constDetail.json";
    public static final String ENTITY_JSON_FILE = "entity.json";
    public static final String FIELD_JSON_FILE = "field.json";
    public static final String INDEX_JSON_FILE = "index.json";
    public static final String CASCADE_EXT_JSON_FILE = "cascadeExt.json";
    public static final String MTM_JSON_FILE = "manyToMany.json";
    public static final String MTM_CASCADE_EXT_JSON_FILE = "mtmCascadeExt.json";


    @Autowired
    private MetaQueryAssembleService metaQueryAssembleService;
    /**
     * 本系统名称，这里用于指定导入导出文件目录所在的父文件夹名称
     */
    @Value("${spring.application.name}")
    private String appName;
    @Autowired
    private GenerateProperties generateProperties;
    @Autowired
    private MetaProjectService metaProjectService;
    @Autowired
    private MetaConstService metaConstService;
    @Autowired
    private MetaConstDetailService metaConstDetailService;
    @Autowired
    private MetaEntityService metaEntityService;
    @Autowired
    private MetaFieldService metaFieldService;
    @Autowired
    private MetaIndexService metaIndexService;
    @Autowired
    private MetaCascadeExtService metaCascadeExtService;
    @Autowired
    private MetaManyToManyService metaManyToManyService;
    @Autowired
    private MetaMtmCascadeExtService metaMtmCascadeExtService;


    /**
     * 获取元数据导出目录
     * @param project
     * @return
     */
    private String getExportDir(MetaProjectPO project){
        return TempDirUtil.getTmpDir(appName, false, false)
            +File.separator+generateProperties.getVersion()
            +File.separator+project.getProjectId()
            +File.separator+"export";
    }
    /**
     * 获取元数据导入文件路径
     * @return
     */
    public String getImportFilePath(){
        return TempDirUtil.getTmpDir(appName, false, false)
            +File.separator+generateProperties.getVersion()
            +File.separator+"import"
            +File.separator+System.currentTimeMillis()+".zip";
    }
    /**
     * 获取元数据导入目录
     * @return
     */
    private String getImportDir(File zipFile){
        String path = zipFile.getPath();
        // 去除末尾.zip
        return path.substring(0,path.length()-4);
    }

    /**
     * 将项目元数据导出成zip
     * @param projectId
     * @return
     */
    public File metaExport(Integer projectId) {
        MetaProjectPO project = metaQueryAssembleService.getAssembledProject(projectId,
            true, true, true, true, true);
        String exportDir = this.getExportDir(project);
        String zipFilePath = exportDir + ".zip";
        File dir = new File(exportDir);
        File outFile = new File(zipFilePath);
        try {
            FileUtils.deleteDirectory(dir);
            FileUtils.deleteQuietly(outFile);
            FileUtils.forceMkdir(dir);
        } catch (IOException e) {
            LOGGER.error("创建导出目录失败",e);
        }
        // 导出项目json文件
        JsonUtil.writeJsonToFile(project,true,new File(dir,PROJECT_JSON_FILE));
        List<MetaConstPO> consts = project.getConsts();
        // 导出枚举json文件
        JsonUtil.writeJsonToFile(consts,true,new File(dir,CONST_JSON_FILE));
        List<MetaConstDetailPO> details = consts.stream()
            .flatMap(constPO -> constPO.getDetailList().stream())
            .collect(Collectors.toList());
        // 导出枚举值json文件
        JsonUtil.writeJsonToFile(details,true,new File(dir,CONST_DETAIL_JSON_FILE));
        List<MetaEntityPO> entities = project.getEntities();
        // 导出实体json文件
        JsonUtil.writeJsonToFile(entities,true,new File(dir,ENTITY_JSON_FILE));
        List<MetaFieldPO> fields = entities.stream()
            .flatMap(entityPO -> {
                Map<Integer, MetaFieldPO> fieldMap = entityPO.getFields();
                if (MapUtils.isEmpty(fieldMap)) {
                    return Stream.empty();
                }
                return fieldMap.values().stream();
            }).collect(Collectors.toList());
        // 导出字段json文件
        JsonUtil.writeJsonToFile(fields,true,new File(dir,FIELD_JSON_FILE));
        List<MetaIndexPO> indexes = entities.stream()
            .flatMap(entityPO -> entityPO.getIndexes().stream())
            .collect(Collectors.toList());
        // 导出索引json文件
        JsonUtil.writeJsonToFile(indexes,true,new File(dir,INDEX_JSON_FILE));

        List<MetaCascadeExtPO> cascadeExts = fields.stream()
            .flatMap(fieldPO -> {
                List<MetaCascadeExtPO> list = fieldPO.getCascadeExts();
                if (CollectionUtils.isEmpty(list)) {
                    return Stream.empty();
                }
                return list.stream();
            })
            .collect(Collectors.toList());
        // 导出外键级联扩展json文件
        JsonUtil.writeJsonToFile(cascadeExts,true,new File(dir,CASCADE_EXT_JSON_FILE));
        List<MetaManyToManyPO> mtms = project.getMtms();
        // 导出多对多json文件
        JsonUtil.writeJsonToFile(mtms,true,new File(dir,MTM_JSON_FILE));
        List<MetaMtmCascadeExtPO> mtmCascades = mtms.stream()
            .flatMap(mtm -> {
                List<MetaMtmCascadeExtPO> list1 = mtm.getCascadeExtList1();
                List<MetaMtmCascadeExtPO> list2 = mtm.getCascadeExtList2();
                return Stream.concat(list1.stream(), list2.stream());
            })
            .collect(Collectors.toList());
        // 导出多对多级联扩展json文件
        JsonUtil.writeJsonToFile(mtmCascades,true,new File(dir,MTM_CASCADE_EXT_JSON_FILE));
        Zip4jUtil.compressFolder(dir, outFile);
        return outFile;
    }

    /**
     * 通过zip压缩包导入项目元数据
     * @param zipFile
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public MetaProjectPO metaImport(File zipFile) {
        String importDir = this.getImportDir(zipFile);
        // 解压zip包
        Zip4jUtil.extractAll(zipFile, importDir);
        LOGGER.info("将zip包解压到：{}",importDir);
        // json文件所在目录
        String jsonDir = importDir + File.separator + "export" + File.separator;
        // 读取项目json文件，并解析成po
        MetaProjectPO projectFromJson = JsonUtil.parseObjectFromFile(
            new File(jsonDir + PROJECT_JSON_FILE), MetaProjectPO.class);
        if(projectFromJson==null){
            throw new BusinessException("导入失败");
        }
        MetaProjectPO project = this.saveProject(projectFromJson);

        // 读取常量json文件，并解析成po列表
        List<MetaConstPO> constListFromJson = JsonUtil.parseArrayFromFile(
            new File(jsonDir + CONST_JSON_FILE), MetaConstPO.class);
        List<MetaConstPO> constList = constListFromJson.stream()
            .map(constFromJson -> this.saveConst(constFromJson,project.getProjectId()))
            .collect(Collectors.toList());
        Map<Integer, Integer> constIdMap = this.getIdMap(constListFromJson, constList, MetaConstPO::getConstId);

        // 读取常量值json文件，并解析成po列表
        List<MetaConstDetailPO> constDetailListFromJson = JsonUtil.parseArrayFromFile(
            new File(jsonDir + CONST_DETAIL_JSON_FILE), MetaConstDetailPO.class);
        constDetailListFromJson.stream()
            .forEach(constDetailFromJson -> this.saveConstDetail(constDetailFromJson,constIdMap));

        return project;
    }




    /**
     * 把json中解析出来的项目保持到数据库
     * @param projectFromJson
     * @return
     */
    private MetaProjectPO saveProject(MetaProjectPO projectFromJson){
        MetaProjectPO project = MetaProjectMapper.INSTANCE.copy(projectFromJson);
        metaProjectService.doSave(project);
        LOGGER.debug("导入项目：{}",JsonUtil.toJSONString(project));
        return project;
    }

    /**
     * 把json中解析出来的枚举保持到数据库
     * @param constFromJson
     * @return
     */
    private MetaConstPO saveConst(MetaConstPO constFromJson,Integer projectId) {
        MetaConstPO constPO = MetaConstMapper.INSTANCE.copy(constFromJson);
        constPO.setProjectId(projectId);
        metaConstService.doSave(constPO);
        LOGGER.debug("导入枚举：{}",JsonUtil.toJSONString(constPO));
        return constPO;
    }

    /**
     * 把json中解析出来的枚举值保持到数据库
     * @param constDetailFromJson
     * @param constIdMap
     * @return
     */
    private MetaConstDetailPO saveConstDetail(MetaConstDetailPO constDetailFromJson,
                                              Map<Integer, Integer> constIdMap) {
        MetaConstDetailPO constDetailPO = MetaConstDetailMapper.INSTANCE.copy(constDetailFromJson);
        Integer constId = constIdMap.get(constDetailFromJson.getConstId());
        if(constId==null){
            LOGGER.error("枚举值json有误：{}",JsonUtil.toJSONString(constDetailFromJson));
            return null;
        }
        constDetailPO.setConstId(constId);
        metaConstDetailService.doSave(constDetailPO);
        LOGGER.debug("导入枚举值：{}",JsonUtil.toJSONString(constDetailPO));
        return constDetailPO;
    }

    /**
     * 获取主键映射表
     * @param poListFromJson 从json文件中解析出来的元素列表
     * @param poList 持久化后的元素列表
     * @param idGetter 主键获取函数
     * @param <T> 列表元素类型
     * @param <R> 主键类型
     * @return
     */
    private <T,R> Map<R,R> getIdMap(List<T> poListFromJson, List<T> poList,
                                              Function<T,R> idGetter){
        Map<R,R> idMap = new HashMap<>(poListFromJson.size());
        for (int i = 0; i < poListFromJson.size(); i++) {
            T poFromJson = poListFromJson.get(i);
            T po = poList.get(i);
            idMap.put(idGetter.apply(poFromJson),idGetter.apply(po));
        }
        return idMap;
    }


}
