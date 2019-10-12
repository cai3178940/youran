package com.youran.generate.service;

import com.youran.common.util.JsonUtil;
import com.youran.common.util.TempDirUtil;
import com.youran.generate.config.GenerateProperties;
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

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private MetaQueryAssembleService metaQueryAssembleService;
    /**
     * 本系统名称，这里用于指定导入导出文件目录所在的父文件夹名称
     */
    @Value("${spring.application.name}")
    private String appName;
    @Autowired
    private GenerateProperties generateProperties;

    /**
     * 将项目元数据导出成zip
     * @param projectId
     * @return
     */
    public File export(Integer projectId) {

        MetaProjectPO project = metaQueryAssembleService.getAssembledProject(projectId,
            true, true, true, true, true);
        String exportDir = TempDirUtil.getTmpDir(appName, false, false)
            +File.separator+generateProperties.getVersion()
            +File.separator+project.getProjectId()
            +File.separator+"export";
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
        JsonUtil.writeJsonToFile(project,true,new File(dir,"project.json"));
        List<MetaConstPO> consts = project.getConsts();
        // 导出枚举json文件
        JsonUtil.writeJsonToFile(consts,true,new File(dir,"const.json"));
        List<MetaConstDetailPO> details = consts.stream()
            .flatMap(constPO -> constPO.getDetailList().stream())
            .collect(Collectors.toList());
        // 导出枚举值json文件
        JsonUtil.writeJsonToFile(details,true,new File(dir,"constDetail.json"));
        List<MetaEntityPO> entities = project.getEntities();
        // 导出实体json文件
        JsonUtil.writeJsonToFile(entities,true,new File(dir,"entity.json"));
        List<MetaFieldPO> fields = entities.stream()
            .flatMap(entityPO -> {
                Map<Integer, MetaFieldPO> fieldMap = entityPO.getFields();
                if (MapUtils.isEmpty(fieldMap)) {
                    return Stream.empty();
                }
                return fieldMap.values().stream();
            }).collect(Collectors.toList());
        // 导出字段json文件
        JsonUtil.writeJsonToFile(fields,true,new File(dir,"field.json"));
        List<MetaIndexPO> indexes = entities.stream()
            .flatMap(entityPO -> entityPO.getIndexes().stream())
            .collect(Collectors.toList());
        // 导出索引json文件
        JsonUtil.writeJsonToFile(indexes,true,new File(dir,"index.json"));

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
        JsonUtil.writeJsonToFile(cascadeExts,true,new File(dir,"cascadeExt.json"));
        List<MetaManyToManyPO> mtms = project.getMtms();
        // 导出多对多json文件
        JsonUtil.writeJsonToFile(mtms,true,new File(dir,"manyToMany.json"));
        List<MetaMtmCascadeExtPO> mtmCascades = mtms.stream()
            .flatMap(mtm -> {
                List<MetaMtmCascadeExtPO> list1 = mtm.getCascadeExtList1();
                List<MetaMtmCascadeExtPO> list2 = mtm.getCascadeExtList2();
                return Stream.concat(list1.stream(), list2.stream());
            })
            .collect(Collectors.toList());
        // 导出多对多级联扩展json文件
        JsonUtil.writeJsonToFile(mtmCascades,true,new File(dir,"mtmCascadeExt.json"));
        Zip4jUtil.compressFolder(exportDir, zipFilePath);
        return outFile;
    }
}
