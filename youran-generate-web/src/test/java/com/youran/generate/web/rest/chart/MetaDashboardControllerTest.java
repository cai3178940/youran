package com.youran.generate.web.rest.chart;

import com.google.common.collect.Lists;
import com.youran.common.util.JsonUtil;
import com.youran.generate.help.GenerateHelper;
import com.youran.generate.help.chart.MetaDashboardHelper;
import com.youran.generate.pojo.dto.chart.MetaDashboardAddDTO;
import com.youran.generate.pojo.dto.chart.MetaDashboardUpdateDTO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.po.chart.MetaDashboardPO;
import com.youran.generate.web.AbstractWebTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 【看板】单元测试
 *
 * @author cbb
 * @date 2020/06/13
 */
public class MetaDashboardControllerTest extends AbstractWebTest {

    @Autowired
    private MetaDashboardHelper metaDashboardHelper;

    @Autowired
    private GenerateHelper generateHelper;

    private MetaProjectPO metaProject;

    @BeforeEach
    public void init() {
        this.metaProject = generateHelper.saveProjectExample();
    }

    /**
     * 新增【看板】
     */
    @Test
    public void save() throws Exception {
        MetaDashboardAddDTO addDTO = metaDashboardHelper.getMetaDashboardAddDTO(metaProject.getProjectId());
        restMockMvc.perform(post(getApiPath() + "/meta_dashboard")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(status().isCreated());
    }

    /**
     * 修改【看板】
     */
    @Test
    public void update() throws Exception {
        MetaDashboardPO metaDashboard = metaDashboardHelper.saveMetaDashboardExample(metaProject.getProjectId());
        MetaDashboardUpdateDTO updateDTO = metaDashboardHelper.getMetaDashboardUpdateDTO(metaDashboard);
        restMockMvc.perform(put(getApiPath() + "/meta_dashboard")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(updateDTO)))
            .andExpect(status().isOk());
    }

    /**
     * 列表查询【看板】
     */
    @Test
    public void list() throws Exception {
        MetaDashboardPO metaDashboard = metaDashboardHelper.saveMetaDashboardExample(metaProject.getProjectId());
        restMockMvc.perform(get(getApiPath() + "/meta_dashboard"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(is(1)));
    }

    /**
     * 查看【看板】详情
     */
    @Test
    public void show() throws Exception {
        MetaDashboardPO metaDashboard = metaDashboardHelper.saveMetaDashboardExample(metaProject.getProjectId());
        restMockMvc.perform(get(getApiPath() + "/meta_dashboard/{dashboardId}", metaDashboard.getDashboardId()))
            .andExpect(status().isOk());
    }

    /**
     * 删除单个【看板】
     */
    @Test
    public void del() throws Exception {
        MetaDashboardPO metaDashboard = metaDashboardHelper.saveMetaDashboardExample(metaProject.getProjectId());
        restMockMvc.perform(delete(getApiPath() + "/meta_dashboard/{dashboardId}", metaDashboard.getDashboardId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(is(1)));
    }

    /**
     * 批量删除【看板】
     */
    @Test
    public void deleteBatch() throws Exception {
        MetaDashboardPO metaDashboard = metaDashboardHelper.saveMetaDashboardExample(metaProject.getProjectId());
        restMockMvc.perform(delete(getApiPath() + "/meta_dashboard")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(Lists.newArrayList(metaDashboard.getDashboardId()))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(is(1)));
    }



}
