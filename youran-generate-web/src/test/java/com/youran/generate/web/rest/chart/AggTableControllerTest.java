package com.youran.generate.web.rest.chart;

import com.google.common.collect.Lists;
import com.youran.common.util.JsonUtil;
import com.youran.generate.help.GenerateHelper;
import com.youran.generate.help.chart.AggTableHelper;
import com.youran.generate.help.chart.MetaChartHelper;
import com.youran.generate.help.chart.MetaChartSourceHelper;
import com.youran.generate.help.chart.MetaChartSourceItemHelper;
import com.youran.generate.pojo.dto.chart.AggTableAddDTO;
import com.youran.generate.pojo.dto.chart.AggTableUpdateDTO;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.po.chart.AggTablePO;
import com.youran.generate.pojo.po.chart.source.MetaChartSourcePO;
import com.youran.generate.pojo.po.chart.source.item.DimensionPO;
import com.youran.generate.pojo.po.chart.source.item.MetricsPO;
import com.youran.generate.web.AbstractWebTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 【聚合表】单元测试
 *
 * @author cbb
 * @date 2020/04/04
 */
public class AggTableControllerTest extends AbstractWebTest {

    @Autowired
    private AggTableHelper aggTableHelper;
    @Autowired
    private MetaChartHelper metaChartHelper;
    @Autowired
    private MetaChartSourceItemHelper metaChartSourceItemHelper;
    @Autowired
    private MetaChartSourceHelper metaChartSourceHelper;
    @Autowired
    private GenerateHelper generateHelper;

    private MetaProjectPO metaProject;
    private MetaEntityPO metaEntity;
    private MetaFieldPO metaField1;
    private MetaFieldPO metaField2;
    private MetaChartSourcePO metaChartSource;
    private DimensionPO dimensionPO;
    private MetricsPO metricsPO;

    @Before
    public void init() {
        this.metaProject = generateHelper.saveProjectExample();
        this.metaEntity = generateHelper.saveEntityExample(metaProject.getProjectId(), 0);
        this.metaField1 = generateHelper.saveFieldExample(metaEntity.getEntityId(),"field1");
        this.metaField2 = generateHelper.saveFieldExample(metaEntity.getEntityId(),"field2");
        this.metaChartSource = metaChartSourceHelper.saveMetaChartSourceExample(
            this.metaProject.getProjectId(), this.metaEntity.getEntityId());
        this.dimensionPO = metaChartSourceItemHelper.saveDimensionExample(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            this.metaField1.getFieldId()
        );
        this.metricsPO = metaChartSourceItemHelper.saveMetricsExample(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            this.metaField2.getFieldId()
        );
    }

    /**
     * 新增【聚合表】
     */
    @Test
    public void save() throws Exception {
        AggTableAddDTO addDTO = aggTableHelper.getAddDTO(
            metaProject.getProjectId(),metaChartSource.getSourceId(),
            Arrays.asList(metaChartHelper.getChartItemDTOExample(dimensionPO.getSourceItemId())),
            Arrays.asList(metaChartHelper.getChartItemDTOExample(metricsPO.getSourceItemId())));
        restMockMvc.perform(post(getApiPath() + "/meta_chart/agg_table")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(status().isCreated());
    }

    /**
     * 修改【聚合表】
     */
    @Test
    public void update() throws Exception {
        AggTablePO metaChart = aggTableHelper.saveExample(
            metaProject.getProjectId(),metaChartSource.getSourceId(),
            Arrays.asList(metaChartHelper.getChartItemDTOExample(dimensionPO.getSourceItemId())),
            Arrays.asList(metaChartHelper.getChartItemDTOExample(metricsPO.getSourceItemId())));
        AggTableUpdateDTO updateDTO = aggTableHelper.getUpdateDTO(metaChart);
        restMockMvc.perform(put(getApiPath() + "/meta_chart/agg_table")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(updateDTO)))
            .andExpect(status().isOk());
    }

    /**
     * 列表查询【聚合表】
     */
    @Test
    public void list() throws Exception {
        AggTablePO metaChart = aggTableHelper.saveExample(
            metaProject.getProjectId(),metaChartSource.getSourceId(),
            Arrays.asList(metaChartHelper.getChartItemDTOExample(dimensionPO.getSourceItemId())),
            Arrays.asList(metaChartHelper.getChartItemDTOExample(metricsPO.getSourceItemId())));
        restMockMvc.perform(get(getApiPath() + "/meta_chart"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(is(1)));
    }

    /**
     * 查看【聚合表】详情
     */
    @Test
    public void show() throws Exception {
        AggTablePO metaChart = aggTableHelper.saveExample(
            metaProject.getProjectId(),metaChartSource.getSourceId(),
            Arrays.asList(metaChartHelper.getChartItemDTOExample(dimensionPO.getSourceItemId())),
            Arrays.asList(metaChartHelper.getChartItemDTOExample(metricsPO.getSourceItemId())));
        restMockMvc.perform(get(getApiPath() + "/meta_chart/agg_table/{chartId}", metaChart.getChartId()))
            .andExpect(status().isOk());
    }

    /**
     * 批量删除【聚合表】
     */
    @Test
    public void deleteBatch() throws Exception {
        AggTablePO metaChart = aggTableHelper.saveExample(
            metaProject.getProjectId(),metaChartSource.getSourceId(),
            Arrays.asList(metaChartHelper.getChartItemDTOExample(dimensionPO.getSourceItemId())),
            Arrays.asList(metaChartHelper.getChartItemDTOExample(metricsPO.getSourceItemId())));
        restMockMvc.perform(delete(getApiPath() + "/meta_chart")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(Lists.newArrayList(metaChart.getChartId()))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(is(1)));
    }



}
