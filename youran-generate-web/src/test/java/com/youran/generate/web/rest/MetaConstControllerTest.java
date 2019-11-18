package com.youran.generate.web.rest;

import com.youran.common.util.JsonUtil;
import com.youran.generate.help.GenerateHelper;
import com.youran.generate.help.MetaConstHelper;
import com.youran.generate.pojo.dto.MetaConstAddDTO;
import com.youran.generate.pojo.dto.MetaConstUpdateDTO;
import com.youran.generate.pojo.po.MetaConstPO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.web.AbstractWebTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author: cbb
 * @date: 2017/5/12
 */
public class MetaConstControllerTest extends AbstractWebTest {

    @Autowired
    private GenerateHelper generateHelper;

    private MetaProjectPO metaProject;

    @Before
    public void init() {
        this.metaProject = generateHelper.saveProjectExample();
    }

    @Test
    public void save() throws Exception {
        MetaConstAddDTO addDTO = MetaConstHelper.getAddDTO(metaProject.getProjectId());
        restMockMvc.perform(post(getApiPath() + "/meta_const/save")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    public void update() throws Exception {
        MetaConstPO metaConst = generateHelper.saveConstExample(metaProject.getProjectId());
        MetaConstUpdateDTO updateDTO = MetaConstHelper.getUpdateDTO(metaConst);
        restMockMvc.perform(put(getApiPath() + "/meta_const/update")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(updateDTO)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void list() throws Exception {
        generateHelper.saveConstExample(metaProject.getProjectId());
        restMockMvc.perform(get(getApiPath() + "/meta_const/list")
            .param("projectId", metaProject.getProjectId() + ""))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.length()").value(is(1)));
    }

    @Test
    public void show() throws Exception {
        MetaConstPO metaConst = generateHelper.saveConstExample(metaProject.getProjectId());
        restMockMvc.perform(get(getApiPath() + "/meta_const/{constId}", metaConst.getConstId()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.constId").value(is(metaConst.getConstId())));
    }

    @Test
    public void del() throws Exception {
        MetaConstPO metaConst = generateHelper.saveConstExample(metaProject.getProjectId());
        restMockMvc.perform(delete(getApiPath() + "/meta_const/{constId}", metaConst.getConstId()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$").value(is(1)));
    }


}
