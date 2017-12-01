package com.youran.generate.web.rest;

import com.youran.common.util.JsonUtil;
import com.youran.generate.data.MetaConstDetailData;
import com.youran.generate.help.GenerateHelper;
import com.youran.generate.pojo.dto.MetaConstDetailAddDTO;
import com.youran.generate.pojo.dto.MetaConstDetailQueryDTO;
import com.youran.generate.pojo.dto.MetaConstDetailUpdateDTO;
import com.youran.generate.pojo.po.MetaConstDetailPO;
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
public class MetaConstDetailControllerTest extends AbstractWebTest {

    @Autowired
    private GenerateHelper generateHelper;

    private MetaProjectPO metaProject;
    private MetaConstPO metaConst;

    @Before
    public void init(){
        this.metaProject = generateHelper.saveProjectExample();
        this.metaConst = generateHelper.saveConstExample(this.metaProject.getProjectId());
    }

    @Test
    public void save() throws Exception {
        MetaConstDetailAddDTO addDTO = MetaConstDetailData.getAddDTO(metaConst.getConstId());
        restMockMvc.perform(post(getRootPath()+"/meta_const_detail/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.toJSONString(addDTO)))
                .andExpect(jsonPath("$.errorCode").value(is(0)));

    }

    @Test
    public void update() throws Exception {
        MetaConstDetailPO metaConstDetail = generateHelper.saveConstDetailExample(metaConst.getConstId());
        MetaConstDetailUpdateDTO updateDTO = MetaConstDetailData.getUpdateDTO(metaConstDetail);
        restMockMvc.perform(put(getRootPath()+"/meta_const_detail/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.toJSONString(updateDTO)))
                .andExpect(jsonPath("$.errorCode").value(is(0)));
    }


    @Test
    public void list() throws Exception {
        generateHelper.saveConstDetailExample(metaConst.getConstId());

        MetaConstDetailQueryDTO queryDTO = new MetaConstDetailQueryDTO();
        queryDTO.setConstId(metaConst.getConstId());
        restMockMvc.perform(post(getRootPath()+"/meta_const_detail/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.toJSONString(queryDTO)))
                .andExpect(jsonPath("$.errorCode").value(is(0)))
                .andExpect(jsonPath("$.data.entities.length()").value(is(1)));
    }

    @Test
    public void show() throws Exception {
        MetaConstDetailPO metaConstDetail = generateHelper.saveConstDetailExample(metaConst.getConstId());
        restMockMvc.perform(get(getRootPath()+"/meta_const_detail/{constId}",metaConstDetail.getConstId()))
                .andExpect(jsonPath("$.errorCode").value(is(0)))
                .andExpect(jsonPath("$.data.constId").value(is(metaConstDetail.getConstId())));
    }

    @Test
    public void del() throws Exception {
        MetaConstDetailPO metaConstDetail = generateHelper.saveConstDetailExample(metaConst.getConstId());
        restMockMvc.perform(delete(getRootPath()+"/meta_const_detail/{constId}",metaConstDetail.getConstId()))
                .andExpect(jsonPath("$.errorCode").value(is(0)))
                .andExpect(jsonPath("$.data").value(is(1)));
    }



}
