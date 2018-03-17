package com.youran.generate.web.rest;

import com.google.common.collect.Lists;
import com.youran.common.util.JsonUtil;
import com.youran.generate.data.MetaProjectData;
import com.youran.generate.help.GenerateHelper;
import com.youran.generate.pojo.dto.MetaProjectAddDTO;
import com.youran.generate.pojo.dto.MetaProjectUpdateDTO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.web.AbstractWebTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/5/24
 */
public class MetaProjectControllerTest extends AbstractWebTest {

    @Autowired
    private GenerateHelper generateHelper;

    @Test
    public void save() throws Exception {
        MetaProjectAddDTO addDTO = MetaProjectData.getAddDTO();
        restMockMvc.perform(post(getRootPath()+"/meta_project/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.toJSONString(addDTO)))
                .andExpect(jsonPath("$.code").value(is("0")));

    }

    @Test
    public void update() throws Exception {
        MetaProjectPO metaProject = generateHelper.saveProjectExample();
        MetaProjectUpdateDTO updateDTO = MetaProjectData.getUpdateDTO(metaProject);
        restMockMvc.perform(put(getRootPath()+"/meta_project/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.toJSONString(updateDTO)))
                .andExpect(jsonPath("$.code").value(is("0")));
    }


    @Test
    public void list() throws Exception {
        generateHelper.saveProjectExample();
        restMockMvc.perform(get(getRootPath()+"/meta_project/list"))
                .andExpect(jsonPath("$.code").value(is("0")))
                .andExpect(jsonPath("$.data.length()").value(is(1)));
    }

    @Test
    public void show() throws Exception {
        MetaProjectPO metaProject = generateHelper.saveProjectExample();
        restMockMvc.perform(get(getRootPath()+"/meta_project/{projectId}",metaProject.getProjectId()))
                .andExpect(jsonPath("$.code").value(is("0")))
                .andExpect(jsonPath("$.data.projectId").value(is(metaProject.getProjectId())));
    }

    @Test
    public void del() throws Exception {
        MetaProjectPO metaProject = generateHelper.saveProjectExample();
        restMockMvc.perform(delete(getRootPath()+"/meta_project/{projectId}",metaProject.getProjectId()))
                .andExpect(jsonPath("$.code").value(is("0")))
                .andExpect(jsonPath("$.data").value(is(1)));
    }

    @Test
    public void deleteBatch() throws Exception {
        MetaProjectPO metaProject = generateHelper.saveProjectExample();
        restMockMvc.perform(put(getRootPath()+"/meta_project/deleteBatch")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.toJSONString(Lists.newArrayList(metaProject.getProjectId()))))
                .andExpect(jsonPath("$.code").value(is("0")))
                .andExpect(jsonPath("$.data").value(is(1)));
    }



}
