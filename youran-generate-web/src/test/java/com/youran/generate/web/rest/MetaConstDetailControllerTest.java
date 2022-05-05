package com.youran.generate.web.rest;

import com.youran.common.util.JsonUtil;
import com.youran.generate.help.GenerateHelper;
import com.youran.generate.help.MetaConstDetailHelper;
import com.youran.generate.pojo.dto.MetaConstDetailAddDTO;
import com.youran.generate.pojo.dto.MetaConstDetailUpdateDTO;
import com.youran.generate.pojo.po.MetaConstDetailPO;
import com.youran.generate.pojo.po.MetaConstPO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.web.AbstractWebTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class MetaConstDetailControllerTest extends AbstractWebTest {

    @Autowired
    private GenerateHelper generateHelper;

    private MetaProjectPO metaProject;
    private MetaConstPO metaConst;

    @BeforeEach
    public void init() {
        this.metaProject = generateHelper.saveProjectExample();
        this.metaConst = generateHelper.saveConstExample(this.metaProject.getProjectId());
    }

    @Test
    public void save() throws Exception {
        MetaConstDetailAddDTO addDTO = MetaConstDetailHelper.getAddDTO(metaConst.getConstId());
        restMockMvc.perform(post(getApiPath() + "/meta_const_detail/save")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    public void update() throws Exception {
        MetaConstDetailPO metaConstDetail = generateHelper.saveConstDetailExample(metaConst.getConstId());
        MetaConstDetailUpdateDTO updateDTO = MetaConstDetailHelper.getUpdateDTO(metaConstDetail);
        restMockMvc.perform(put(getApiPath() + "/meta_const_detail/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(updateDTO)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void list() throws Exception {
        generateHelper.saveConstDetailExample(metaConst.getConstId());
        restMockMvc.perform(get(getApiPath() + "/meta_const_detail/list")
            .param("constId", metaConst.getConstId() + ""))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.length()").value(is(1)));
    }

    @Test
    public void show() throws Exception {
        MetaConstDetailPO metaConstDetail = generateHelper.saveConstDetailExample(metaConst.getConstId());
        restMockMvc.perform(get(getApiPath() + "/meta_const_detail/{constId}", metaConstDetail.getConstId()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.constId").value(is(metaConstDetail.getConstId())));
    }

    @Test
    public void del() throws Exception {
        MetaConstDetailPO metaConstDetail = generateHelper.saveConstDetailExample(metaConst.getConstId());
        restMockMvc.perform(delete(getApiPath() + "/meta_const_detail/{constId}", metaConstDetail.getConstId()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$").value(is(1)));
    }


}
