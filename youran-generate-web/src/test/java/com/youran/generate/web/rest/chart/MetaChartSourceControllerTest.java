package com.youran.generate.web.rest.chart;

import com.google.common.collect.Lists;
import com.youran.common.util.JsonUtil;
import com.youran.generate.help.GenerateHelper;
import com.youran.generate.help.chart.MetaChartSourceHelper;
import com.youran.generate.pojo.dto.chart.source.MetaChartSourceAddDTO;
import com.youran.generate.pojo.dto.chart.source.MetaChartSourceUpdateDTO;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.po.chart.source.MetaChartSourcePO;
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
 * 【图表数据源】单元测试
 *
 * @author cbb
 * @date 2020/04/04
 */
public class MetaChartSourceControllerTest extends AbstractWebTest {

    @Autowired
    private MetaChartSourceHelper metaChartSourceHelper;
    @Autowired
    private GenerateHelper generateHelper;

    private MetaProjectPO metaProject;
    private MetaEntityPO metaEntity;

    @Before
    public void init() {
        this.metaProject = generateHelper.saveProjectExample();
        this.metaEntity = generateHelper.saveEntityExample(metaProject.getProjectId(), 0);
    }
    /**
     * 新增【图表数据源】
     */
    @Test
    public void save() throws Exception {
        MetaChartSourceAddDTO addDTO = metaChartSourceHelper.getMetaChartSourceAddDTO(
            this.metaProject.getProjectId(), this.metaEntity.getEntityId());
        restMockMvc.perform(post(getApiPath() + "/metaChartSource")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(status().isCreated());
    }

    /**
     * 修改【图表数据源】
     */
    @Test
    public void update() throws Exception {
        MetaChartSourcePO metaChartSource = metaChartSourceHelper.saveMetaChartSourceExample(
            this.metaProject.getProjectId(), this.metaEntity.getEntityId());
        MetaChartSourceUpdateDTO updateDTO = metaChartSourceHelper.getMetaChartSourceUpdateDTO(metaChartSource);
        restMockMvc.perform(put(getApiPath() + "/metaChartSource")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(updateDTO)))
            .andExpect(status().isOk());
    }

    /**
     * 查看【图表数据源】详情
     */
    @Test
    public void show() throws Exception {
        MetaChartSourcePO metaChartSource = metaChartSourceHelper.saveMetaChartSourceExample(
            this.metaProject.getProjectId(), this.metaEntity.getEntityId());
        restMockMvc.perform(get(getApiPath() + "/metaChartSource/{sourceId}", metaChartSource.getSourceId()))
            .andExpect(status().isOk());
    }

    /**
     * 批量删除【图表数据源】
     */
    @Test
    public void deleteBatch() throws Exception {
        MetaChartSourcePO metaChartSource = metaChartSourceHelper.saveMetaChartSourceExample(
            this.metaProject.getProjectId(), this.metaEntity.getEntityId());
        restMockMvc.perform(delete(getApiPath() + "/metaChartSource")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(Lists.newArrayList(metaChartSource.getSourceId()))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(is(1)));
    }



}
