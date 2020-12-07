package com.youran.generate.service;

import com.youran.common.util.JsonUtil;
import com.youran.generate.config.GenerateProperties;
import com.youran.generate.constant.ImportExportConst;
import com.youran.generate.pojo.dto.SystemDTO;
import com.youran.generate.pojo.po.*;
import com.youran.generate.pojo.po.chart.MetaChartPO;
import com.youran.generate.pojo.po.chart.MetaDashboardPO;
import com.youran.generate.pojo.po.chart.source.MetaChartSourcePO;
import com.youran.generate.pojo.po.chart.source.item.MetaChartSourceItemPO;
import com.youran.generate.util.Zip4jUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 元数据导出服务类
 *
 * @author: cbb
 * @date: 10/12/2019 21:20
 */
@Service
public class MetaExportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetaExportService.class);
    @Autowired
    private MetaQueryAssembleService metaQueryAssembleService;
    @Autowired
    private GenerateProperties generateProperties;
    @Autowired
    private DataDirService dataDirService;


    /**
     * 将项目元数据导出成zip
     *
     * @param projectId
     * @return
     */
    public File metaExport(Integer projectId) {
        MetaProjectPO project = metaQueryAssembleService.getAssembledProject(projectId, false);
        String exportDir = dataDirService.getProjectExportDir(project);
        String zipFilePath = exportDir + ".zip";
        File dir = new File(exportDir);
        File outFile = new File(zipFilePath);
        try {
            FileUtils.deleteDirectory(dir);
            FileUtils.deleteQuietly(outFile);
            FileUtils.forceMkdir(dir);
        } catch (IOException e) {
            LOGGER.error("创建导出目录失败", e);
        }
        // 导出项目json文件
        JsonUtil.writeJsonToFile(project, true, new File(dir, ImportExportConst.PROJECT_JSON_FILE));
        List<MetaConstPO> consts = project.getConsts();
        if (CollectionUtils.isNotEmpty(consts)) {
            // 导出枚举json文件
            JsonUtil.writeJsonToFile(consts, true, new File(dir, ImportExportConst.CONST_JSON_FILE));
            List<MetaConstDetailPO> details = consts.stream()
                .flatMap(constPO -> constPO.getDetailList().stream())
                .collect(Collectors.toList());
            // 导出枚举值json文件
            JsonUtil.writeJsonToFile(details, true, new File(dir, ImportExportConst.CONST_DETAIL_JSON_FILE));
        }
        List<MetaEntityPO> entities = project.getEntities();
        if (CollectionUtils.isNotEmpty(entities)) {
            // 导出实体json文件
            JsonUtil.writeJsonToFile(entities, true, new File(dir, ImportExportConst.ENTITY_JSON_FILE));
            List<MetaFieldPO> fields = entities.stream()
                .flatMap(entityPO -> {
                    Map<Integer, MetaFieldPO> fieldMap = entityPO.getFields();
                    if (MapUtils.isEmpty(fieldMap)) {
                        return Stream.empty();
                    }
                    return fieldMap.values().stream();
                }).collect(Collectors.toList());
            // 导出字段json文件
            JsonUtil.writeJsonToFile(fields, true, new File(dir, ImportExportConst.FIELD_JSON_FILE));
            List<MetaIndexPO> indexes = entities.stream()
                .flatMap(entityPO -> entityPO.getIndexes().stream())
                .peek(MetaIndexPO::resetFieldIds)
                .collect(Collectors.toList());
            // 导出索引json文件
            JsonUtil.writeJsonToFile(indexes, true, new File(dir, ImportExportConst.INDEX_JSON_FILE));
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
            JsonUtil.writeJsonToFile(cascadeExts, true, new File(dir, ImportExportConst.CASCADE_EXT_JSON_FILE));
        }
        List<MetaManyToManyPO> mtms = project.getMtms();
        if (CollectionUtils.isNotEmpty(mtms)) {
            // 导出多对多json文件
            JsonUtil.writeJsonToFile(mtms, true, new File(dir, ImportExportConst.MTM_JSON_FILE));
            List<MetaMtmCascadeExtPO> mtmCascades = mtms.stream()
                .flatMap(mtm -> {
                    List<MetaMtmCascadeExtPO> list1 = mtm.getCascadeExtList1();
                    List<MetaMtmCascadeExtPO> list2 = mtm.getCascadeExtList2();
                    return Stream.concat(list1.stream(), list2.stream());
                })
                .collect(Collectors.toList());
            // 导出多对多级联扩展json文件
            JsonUtil.writeJsonToFile(mtmCascades, true, new File(dir, ImportExportConst.MTM_CASCADE_EXT_JSON_FILE));
        }
        List<MetaChartPO> charts = project.getCharts();
        if (CollectionUtils.isNotEmpty(charts)) {
            // 导出图表json文件
            JsonUtil.writeJsonToFile(charts, true, new File(dir, ImportExportConst.CHART_JSON_FILE));
            Set<MetaChartSourcePO> chartSources = charts.stream().map(MetaChartPO::getChartSource).collect(Collectors.toSet());
            // 导出图表数据源json文件
            JsonUtil.writeJsonToFile(chartSources, true, new File(dir, ImportExportConst.CHART_SOURCE_JSON_FILE));
            List<MetaChartSourceItemPO> chartSourceItems = chartSources.stream()
                .flatMap(chartSource -> chartSource.fetchSourceItems().stream())
                .collect(Collectors.toList());
            // 导出图表数据项json文件
            JsonUtil.writeJsonToFile(chartSourceItems, true, new File(dir, ImportExportConst.CHART_SOURCE_ITEM_JSON_FILE));
        }
        List<MetaDashboardPO> dashboards = project.getDashboards();
        if (CollectionUtils.isNotEmpty(dashboards)) {
            // 导出看板json文件
            JsonUtil.writeJsonToFile(dashboards, true, new File(dir, ImportExportConst.DASHBOARD_JSON_FILE));
        }

        // 导出系统信息json文件
        SystemDTO systemDTO = new SystemDTO(generateProperties.getVersion());
        JsonUtil.writeJsonToFile(systemDTO, true, new File(dir, ImportExportConst.SYSTEM_JSON_FILE));

        // 将文件夹打成压缩包
        Zip4jUtil.compressFolder(dir, outFile);
        return outFile;
    }


}
