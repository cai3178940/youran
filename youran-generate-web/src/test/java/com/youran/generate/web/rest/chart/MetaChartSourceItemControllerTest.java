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
import com.youran.generate.pojo.po.chart.source.item.AggOrderPO;
import com.youran.generate.pojo.po.chart.source.item.DetailColumnPO;
import com.youran.generate.pojo.po.chart.source.item.MetricsPO;
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

    @Test
    public void saveAggOrder() throws Exception {
        AggOrderAddDTO addDTO = metaChartSourceItemHelper.getAggOrderAddDTO(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            null
        );
        restMockMvc.perform(post(getApiPath() + "/metaChartSourceItem/aggOrder")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(status().isOk());
    }

    @Test
    public void saveDetailColumn() throws Exception {
        DetailColumnAddDTO addDTO = metaChartSourceItemHelper.getDetailColumnAddDTO(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            this.metaField.getFieldId()
        );
        restMockMvc.perform(post(getApiPath() + "/metaChartSourceItem/detailColumn")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(status().isOk());
    }

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
        restMockMvc.perform(post(getApiPath() + "/metaChartSourceItem/detailOrder")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(status().isOk());
    }

    @Test
    public void saveDimension() throws Exception {
        DimensionAddDTO addDTO = metaChartSourceItemHelper.getDimensionAddDTO(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            this.metaField.getFieldId()
        );
        restMockMvc.perform(post(getApiPath() + "/metaChartSourceItem/dimension")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(status().isOk());
    }

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
        restMockMvc.perform(post(getApiPath() + "/metaChartSourceItem/having")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(status().isOk());
    }

    @Test
    public void saveMetrics() throws Exception {
        MetricsAddDTO addDTO = metaChartSourceItemHelper.getMetricsAddDTO(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            this.metaField.getFieldId()
        );
        restMockMvc.perform(post(getApiPath() + "/metaChartSourceItem/metrics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(status().isOk());
    }

    @Test
    public void saveWhere() throws Exception {
        WhereAddDTO addDTO = metaChartSourceItemHelper.getWhereAddDTO(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            this.metaField.getFieldId()
        );
        restMockMvc.perform(post(getApiPath() + "/metaChartSourceItem/where")
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
        restMockMvc.perform(put(getApiPath() + "/metaChartSourceItem/aggOrder")
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
        restMockMvc.perform(get(getApiPath() + "/metaChartSourceItem/aggOrder"))
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
        restMockMvc.perform(delete(getApiPath() + "/metaChartSourceItem")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(Lists.newArrayList(po.getSourceItemId()))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(is(1)));
    }



}
