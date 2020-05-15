package com.youran.generate.web.rest.chart;

import com.google.common.collect.Lists;
import com.youran.common.util.JsonUtil;
import com.youran.generate.help.GenerateHelper;
import com.youran.generate.help.chart.MetaChartSourceHelper;
import com.youran.generate.help.chart.MetaChartSourceItemHelper;
import com.youran.generate.pojo.dto.chart.source.item.*;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.po.chart.source.MetaChartSourcePO;
import com.youran.generate.pojo.po.chart.source.item.*;
import com.youran.generate.web.AbstractWebTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 【图表数据源项】单元测试
 *
 * @author cbb
 * @date 2020/04/04
 */
public class MetaChartSourceItemControllerTest extends AbstractWebTest {

    @Autowired
    private MetaChartSourceItemHelper metaChartSourceItemHelper;
    @Autowired
    private MetaChartSourceHelper metaChartSourceHelper;
    @Autowired
    private GenerateHelper generateHelper;

    private MetaProjectPO metaProject;
    private MetaEntityPO metaEntity;
    private MetaFieldPO metaField;
    private MetaChartSourcePO metaChartSource;

    @Before
    public void init() {
        this.metaProject = generateHelper.saveProjectExample();
        this.metaEntity = generateHelper.saveEntityExample(metaProject.getProjectId(), 0);
        this.metaField = generateHelper.saveFieldExample(metaEntity.getEntityId());
        this.metaChartSource = metaChartSourceHelper.saveMetaChartSourceExample(
            this.metaProject.getProjectId(), this.metaEntity.getEntityId());
    }

    /**
     * 聚合排序
     */
    @Test
    public void saveAggOrder() throws Exception {
        AggOrderAddDTO addDTO = metaChartSourceItemHelper.getAggOrderAddDTO(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            null
        );
        restMockMvc.perform(post(getApiPath() + "/meta_chart_source_item/agg_order")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(status().isOk());
    }

    @Test
    public void updateAggOrder() throws Exception {
        AggOrderPO po = metaChartSourceItemHelper.saveAggOrderExample(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            null
        );
        AggOrderUpdateDTO updateDTO = metaChartSourceItemHelper.getAggOrderUpdateDTO(po);
        restMockMvc.perform(put(getApiPath() + "/meta_chart_source_item/agg_order")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(updateDTO)))
            .andExpect(status().isOk());
    }

    @Test
    public void listAggOrder() throws Exception {
        AggOrderPO po = metaChartSourceItemHelper.saveAggOrderExample(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            null
        );
        restMockMvc.perform(get(getApiPath() + "/meta_chart_source_item/agg_order")
            .param("projectId",this.metaProject.getProjectId().toString())
            .param("sourceId",this.metaChartSource.getSourceId().toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(is(1)));
    }

    /**
     * 明细列
     */
    @Test
    public void saveDetailColumn() throws Exception {
        DetailColumnAddDTO addDTO = metaChartSourceItemHelper.getDetailColumnAddDTO(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            this.metaField.getFieldId()
        );
        restMockMvc.perform(post(getApiPath() + "/meta_chart_source_item/detail_column")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(status().isOk());
    }

    @Test
    public void updateDetailColumn() throws Exception {
        DetailColumnPO po = metaChartSourceItemHelper.saveDetailColumnExample(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            this.metaField.getFieldId()
        );
        DetailColumnUpdateDTO updateDTO = metaChartSourceItemHelper.getDetailColumnUpdateDTO(po);
        restMockMvc.perform(put(getApiPath() + "/meta_chart_source_item/detail_column")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(updateDTO)))
            .andExpect(status().isOk());
    }

    @Test
    public void listDetailColumn() throws Exception {
        DetailColumnPO po = metaChartSourceItemHelper.saveDetailColumnExample(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            this.metaField.getFieldId()
        );
        restMockMvc.perform(get(getApiPath() + "/meta_chart_source_item/detail_column")
            .param("projectId",this.metaProject.getProjectId().toString())
            .param("sourceId",this.metaChartSource.getSourceId().toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(is(1)));
    }

    /**
     * 明细排序
     */
    @Test
    public void saveDetailOrder() throws Exception {
        DetailColumnPO detailColumnPO = metaChartSourceItemHelper.saveDetailColumnExample(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            this.metaField.getFieldId()
        );
        DetailOrderAddDTO addDTO = metaChartSourceItemHelper.getDetailOrderAddDTO(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            detailColumnPO.getSourceItemId()
        );
        restMockMvc.perform(post(getApiPath() + "/meta_chart_source_item/detail_order")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(status().isOk());
    }

    @Test
    public void updateDetailOrder() throws Exception {
        DetailColumnPO detailColumnPO = metaChartSourceItemHelper.saveDetailColumnExample(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            this.metaField.getFieldId()
        );
        DetailOrderPO po = metaChartSourceItemHelper.saveDetailOrderExample(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            detailColumnPO.getSourceItemId()
        );
        DetailOrderUpdateDTO updateDTO = metaChartSourceItemHelper.getDetailOrderUpdateDTO(po);
        restMockMvc.perform(put(getApiPath() + "/meta_chart_source_item/detail_order")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(updateDTO)))
            .andExpect(status().isOk());
    }

    @Test
    public void listDetailOrder() throws Exception {
        DetailColumnPO detailColumnPO = metaChartSourceItemHelper.saveDetailColumnExample(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            this.metaField.getFieldId()
        );
        DetailOrderPO po = metaChartSourceItemHelper.saveDetailOrderExample(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            detailColumnPO.getSourceItemId()
        );
        restMockMvc.perform(get(getApiPath() + "/meta_chart_source_item/detail_order")
            .param("projectId",this.metaProject.getProjectId().toString())
            .param("sourceId",this.metaChartSource.getSourceId().toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(is(1)));
    }

    /**
     * 维度
     */
    @Test
    public void saveDimension() throws Exception {
        DimensionAddDTO addDTO = metaChartSourceItemHelper.getDimensionAddDTO(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            this.metaField.getFieldId()
        );
        restMockMvc.perform(post(getApiPath() + "/meta_chart_source_item/dimension")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(status().isOk());
    }

    @Test
    public void updateDimension() throws Exception {
        DimensionPO po = metaChartSourceItemHelper.saveDimensionExample(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            this.metaField.getFieldId()
        );
        DimensionUpdateDTO updateDTO = metaChartSourceItemHelper.getDimensionUpdateDTO(po);
        restMockMvc.perform(put(getApiPath() + "/meta_chart_source_item/dimension")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(updateDTO)))
            .andExpect(status().isOk());
    }

    @Test
    public void listDimension() throws Exception {
        DimensionPO po = metaChartSourceItemHelper.saveDimensionExample(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            this.metaField.getFieldId()
        );
        restMockMvc.perform(get(getApiPath() + "/meta_chart_source_item/dimension")
            .param("projectId",this.metaProject.getProjectId().toString())
            .param("sourceId",this.metaChartSource.getSourceId().toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(is(1)));
    }

    /**
     * having条件
     */
    @Test
    public void saveHaving() throws Exception {
        MetricsPO metricsPO = metaChartSourceItemHelper.saveMetricsExample(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            this.metaField.getFieldId()
        );
        HavingAddDTO addDTO = metaChartSourceItemHelper.getHavingAddDTO(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            metricsPO.getSourceItemId()
        );
        restMockMvc.perform(post(getApiPath() + "/meta_chart_source_item/having")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(status().isOk());
    }

    @Test
    public void updateHaving() throws Exception {
        MetricsPO metricsPO = metaChartSourceItemHelper.saveMetricsExample(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            this.metaField.getFieldId()
        );
        HavingPO po = metaChartSourceItemHelper.saveHavingExample(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            metricsPO.getSourceItemId()
        );
        HavingUpdateDTO updateDTO = metaChartSourceItemHelper.getHavingUpdateDTO(po);
        restMockMvc.perform(put(getApiPath() + "/meta_chart_source_item/having")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(updateDTO)))
            .andExpect(status().isOk());
    }

    @Test
    public void listHaving() throws Exception {
        MetricsPO metricsPO = metaChartSourceItemHelper.saveMetricsExample(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            this.metaField.getFieldId()
        );
        HavingPO po = metaChartSourceItemHelper.saveHavingExample(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            metricsPO.getSourceItemId()
        );
        restMockMvc.perform(get(getApiPath() + "/meta_chart_source_item/having")
            .param("projectId",this.metaProject.getProjectId().toString())
            .param("sourceId",this.metaChartSource.getSourceId().toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(is(1)));
    }

    /**
     * 指标
     */
    @Test
    public void saveMetrics() throws Exception {
        MetricsAddDTO addDTO = metaChartSourceItemHelper.getMetricsAddDTO(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            this.metaField.getFieldId()
        );
        restMockMvc.perform(post(getApiPath() + "/meta_chart_source_item/metrics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(status().isOk());
    }

    @Test
    public void updateMetrics() throws Exception {
        MetricsPO po = metaChartSourceItemHelper.saveMetricsExample(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            this.metaField.getFieldId()
        );
        MetricsUpdateDTO updateDTO = metaChartSourceItemHelper.getMetricsUpdateDTO(po);
        restMockMvc.perform(put(getApiPath() + "/meta_chart_source_item/metrics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(updateDTO)))
            .andExpect(status().isOk());
    }

    @Test
    public void listMetrics() throws Exception {
        MetricsPO po = metaChartSourceItemHelper.saveMetricsExample(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            this.metaField.getFieldId()
        );
        restMockMvc.perform(get(getApiPath() + "/meta_chart_source_item/metrics")
            .param("projectId",this.metaProject.getProjectId().toString())
            .param("sourceId",this.metaChartSource.getSourceId().toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(is(1)));
    }

    /**
     * where条件
     */
    @Test
    public void saveWhere() throws Exception {
        WhereAddDTO addDTO = metaChartSourceItemHelper.getWhereAddDTO(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            this.metaField.getFieldId()
        );
        restMockMvc.perform(post(getApiPath() + "/meta_chart_source_item/where")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(status().isOk());
    }


    @Test
    public void updateWhere() throws Exception {
        WherePO po = metaChartSourceItemHelper.saveWhereExample(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            this.metaField.getFieldId()
        );
        WhereUpdateDTO updateDTO = metaChartSourceItemHelper.getWhereUpdateDTO(po);
        restMockMvc.perform(put(getApiPath() + "/meta_chart_source_item/where")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(updateDTO)))
            .andExpect(status().isOk());
    }

    @Test
    public void listWhere() throws Exception {
        WherePO po = metaChartSourceItemHelper.saveWhereExample(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            this.metaField.getFieldId()
        );
        restMockMvc.perform(get(getApiPath() + "/meta_chart_source_item/where")
            .param("projectId",this.metaProject.getProjectId().toString())
            .param("sourceId",this.metaChartSource.getSourceId().toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(is(1)));
    }

    /**
     * 批量删除【图表数据源项】
     */
    @Test
    public void deleteBatch() throws Exception {
        AggOrderPO po = metaChartSourceItemHelper.saveAggOrderExample(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            null
        );
        restMockMvc.perform(delete(getApiPath() + "/meta_chart_source_item")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(Lists.newArrayList(po.getSourceItemId()))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(is(1)));
    }



}
