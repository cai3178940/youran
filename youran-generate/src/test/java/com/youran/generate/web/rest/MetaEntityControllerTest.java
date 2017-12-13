package com.youran.generate.web.rest;

import com.youran.common.util.JsonUtil;
import com.youran.generate.data.MetaEntityData;
import com.youran.generate.help.GenerateHelper;
import com.youran.generate.pojo.dto.MetaEntityAddDTO;
import com.youran.generate.pojo.dto.MetaEntityQueryDTO;
import com.youran.generate.pojo.dto.MetaEntityUpdateDTO;
import com.youran.generate.pojo.po.MetaEntityPO;
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
public class MetaEntityControllerTest extends AbstractWebTest {

    @Autowired
    private GenerateHelper generateHelper;

    private MetaProjectPO metaProject;

    @Before
    public void init(){
        this.metaProject = generateHelper.saveProjectExample();
    }

    @Test
    public void save() throws Exception {
        MetaEntityAddDTO addDTO = MetaEntityData.getAddDTO(metaProject.getProjectId(),0);
        restMockMvc.perform(post(getRootPath()+"/meta_entity/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.toJSONString(addDTO)))
                .andExpect(jsonPath("$.code").value(is("0")));

    }

    @Test
    public void update() throws Exception {
        MetaEntityPO metaEntity = generateHelper.saveEntityExample(metaProject.getProjectId(),0);
        MetaEntityUpdateDTO updateDTO = MetaEntityData.getUpdateDTO(metaEntity);
        restMockMvc.perform(put(getRootPath()+"/meta_entity/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.toJSONString(updateDTO)))
                .andExpect(jsonPath("$.code").value(is("0")));
    }


    @Test
    public void list() throws Exception {
        generateHelper.saveEntityExample(metaProject.getProjectId(),0);

        MetaEntityQueryDTO queryDTO = new MetaEntityQueryDTO();
        queryDTO.setProjectId(metaProject.getProjectId());
        restMockMvc.perform(post(getRootPath()+"/meta_entity/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.toJSONString(queryDTO)))
                .andExpect(jsonPath("$.code").value(is("0")))
                .andExpect(jsonPath("$.data.entities.length()").value(is(1)));
    }

    @Test
    public void show() throws Exception {
        MetaEntityPO metaEntity = generateHelper.saveEntityExample(metaProject.getProjectId(),0);
        restMockMvc.perform(get(getRootPath()+"/meta_entity/{entityId}",metaEntity.getEntityId()))
                .andExpect(jsonPath("$.code").value(is("0")))
                .andExpect(jsonPath("$.data.entityId").value(is(metaEntity.getEntityId())));
    }

    @Test
    public void del() throws Exception {
        MetaEntityPO metaEntity = generateHelper.saveEntityExample(metaProject.getProjectId(),0);
        restMockMvc.perform(delete(getRootPath()+"/meta_entity/{entityId}",metaEntity.getEntityId()))
                .andExpect(jsonPath("$.code").value(is("0")))
                .andExpect(jsonPath("$.data").value(is(1)));
    }



}
