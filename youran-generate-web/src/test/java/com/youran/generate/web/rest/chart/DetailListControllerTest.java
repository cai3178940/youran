package com.youran.generate.web.rest.chart;

import com.google.common.collect.Lists;
import com.youran.common.util.JsonUtil;
import com.youran.generate.help.GenerateHelper;
import com.youran.generate.help.chart.DetailListHelper;
import com.youran.generate.help.chart.MetaChartHelper;
import com.youran.generate.help.chart.MetaChartSourceHelper;
import com.youran.generate.help.chart.MetaChartSourceItemHelper;
import com.youran.generate.pojo.dto.chart.DetailListAddDTO;
import com.youran.generate.pojo.dto.chart.DetailListUpdateDTO;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.po.chart.DetailListPO;
import com.youran.generate.pojo.po.chart.source.MetaChartSourcePO;
import com.youran.generate.pojo.po.chart.source.item.DetailColumnPO;
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
public class DetailListControllerTest extends AbstractWebTest {

    @Autowired
    private DetailListHelper detailListHelper;
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
    private MetaFieldPO metaField;
    private MetaChartSourcePO metaChartSource;
    private DetailColumnPO detailColumnPO;

    @Before
    public void init() {
        this.metaProject = generateHelper.saveProjectExample();
        this.metaEntity = generateHelper.saveEntityExample(metaProject.getProjectId(), 0);
        this.metaField = generateHelper.saveFieldExample(metaEntity.getEntityId());
        this.metaChartSource = metaChartSourceHelper.saveMetaChartSourceExample(
            this.metaProject.getProjectId(), this.metaEntity.getEntityId());
        this.detailColumnPO = metaChartSourceItemHelper.saveDetailColumnExample(
            this.metaProject.getProjectId(),
            this.metaChartSource.getSourceId(),
            this.metaField.getFieldId()
        );
    }

    /**
     * 新增【聚合表】
     */
    @Test
    public void save() throws Exception {
        DetailListAddDTO addDTO = detailListHelper.getAddDTO(
            metaProject.getProjectId(),metaChartSource.getSourceId(),
            Arrays.asList(metaChartHelper.getChartItemDTOExample(detailColumnPO.getSourceItemId())));
        restMockMvc.perform(post(getApiPath() + "/meta_chart/detail_list")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(status().isCreated());
    }

    /**
     * 修改【聚合表】
     */
    @Test
    public void update() throws Exception {
        DetailListPO metaChart = detailListHelper.saveExample(
            metaProject.getProjectId(),metaChartSource.getSourceId(),
            Arrays.asList(metaChartHelper.getChartItemDTOExample(detailColumnPO.getSourceItemId())));
        DetailListUpdateDTO updateDTO = detailListHelper.getUpdateDTO(metaChart);
        restMockMvc.perform(put(getApiPath() + "/meta_chart/detail_list")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(updateDTO)))
            .andExpect(status().isOk());
    }

    /**
     * 列表查询【聚合表】
     */
    @Test
    public void list() throws Exception {
        DetailListPO metaChart = detailListHelper.saveExample(
            metaProject.getProjectId(),metaChartSource.getSourceId(),
            Arrays.asList(metaChartHelper.getChartItemDTOExample(detailColumnPO.getSourceItemId())));
        restMockMvc.perform(get(getApiPath() + "/meta_chart"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(is(1)));
    }

    /**
     * 查看【聚合表】详情
     */
    @Test
    public void show() throws Exception {
        DetailListPO metaChart = detailListHelper.saveExample(
            metaProject.getProjectId(),metaChartSource.getSourceId(),
            Arrays.asList(metaChartHelper.getChartItemDTOExample(detailColumnPO.getSourceItemId())));
        restMockMvc.perform(get(getApiPath() + "/meta_chart/detail_list/{chartId}", metaChart.getChartId()))
            .andExpect(status().isOk());
    }

    /**
     * 批量删除【聚合表】
     */
    @Test
    public void deleteBatch() throws Exception {
        DetailListPO metaChart = detailListHelper.saveExample(
            metaProject.getProjectId(),metaChartSource.getSourceId(),
            Arrays.asList(metaChartHelper.getChartItemDTOExample(detailColumnPO.getSourceItemId())));
        restMockMvc.perform(delete(getApiPath() + "/meta_chart")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(Lists.newArrayList(metaChart.getChartId()))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(is(1)));
    }



}
