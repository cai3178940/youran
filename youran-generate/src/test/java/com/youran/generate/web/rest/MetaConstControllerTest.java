package com.youran.generate.web.rest;

import com.youran.common.util.JsonUtil;
import com.youran.generate.data.MetaConstData;
import com.youran.generate.help.GenerateHelper;
import com.youran.generate.pojo.dto.MetaConstAddDTO;
import com.youran.generate.pojo.dto.MetaConstUpdateDTO;
import com.youran.generate.pojo.po.MetaConstPO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.web.AbstractWebTest;
import org.junit.Before;
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
 * Create Time:2017/5/12 14:35
 */
public class MetaConstControllerTest extends AbstractWebTest {

    @Autowired
    private GenerateHelper generateHelper;

    private MetaProjectPO metaProject;

    @Before
    public void init(){
        this.metaProject = generateHelper.saveProjectExample();
    }

    @Test
    public void save() throws Exception {
        MetaConstAddDTO addDTO = MetaConstData.getAddDTO(metaProject.getProjectId());
        restMockMvc.perform(post(getRootPath()+"/meta_const/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.toJSONString(addDTO)))
                .andExpect(jsonPath("$.code").value(is("0")));

    }

    @Test
    public void update() throws Exception {
        MetaConstPO metaConst = generateHelper.saveConstExample(metaProject.getProjectId());
        MetaConstUpdateDTO updateDTO = MetaConstData.getUpdateDTO(metaConst);
        restMockMvc.perform(put(getRootPath()+"/meta_const/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.toJSONString(updateDTO)))
                .andExpect(jsonPath("$.code").value(is("0")));
    }


    @Test
    public void list() throws Exception {
        generateHelper.saveConstExample(metaProject.getProjectId());
        restMockMvc.perform(get(getRootPath()+"/meta_const/list")
                .param("projectId",metaProject.getProjectId()+""))
                .andExpect(jsonPath("$.code").value(is("0")))
                .andExpect(jsonPath("$.data.entities.length()").value(is(1)));
    }

    @Test
    public void show() throws Exception {
        MetaConstPO metaConst = generateHelper.saveConstExample(metaProject.getProjectId());
        restMockMvc.perform(get(getRootPath()+"/meta_const/{constId}",metaConst.getConstId()))
                .andExpect(jsonPath("$.code").value(is("0")))
                .andExpect(jsonPath("$.data.constId").value(is(metaConst.getConstId())));
    }

    @Test
    public void del() throws Exception {
        MetaConstPO metaConst = generateHelper.saveConstExample(metaProject.getProjectId());
        restMockMvc.perform(delete(getRootPath()+"/meta_const/{constId}",metaConst.getConstId()))
                .andExpect(jsonPath("$.code").value(is("0")))
                .andExpect(jsonPath("$.data").value(is(1)));
    }



}
