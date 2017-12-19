package com.youran.generate.web.rest;

import com.youran.common.util.JsonUtil;
import com.youran.generate.data.MetaManyToManyData;
import com.youran.generate.help.GenerateHelper;
import com.youran.generate.pojo.dto.MetaManyToManyAddDTO;
import com.youran.generate.pojo.dto.MetaManyToManyUpdateDTO;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaManyToManyPO;
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
 * Create Time:2017/7/4 17:13
 */
public class MetaManyToManyControllerTest extends AbstractWebTest {

    @Autowired
    private GenerateHelper generateHelper;

    private MetaProjectPO metaProject;
    private MetaEntityPO metaEntity1;
    private MetaEntityPO metaEntity2;

    @Before
    public void init(){
        this.metaProject = generateHelper.saveProjectExample();
        this.metaEntity1 = generateHelper.saveEntityExample(metaProject.getProjectId(),1);
        this.metaEntity2 = generateHelper.saveEntityExample(metaProject.getProjectId(),2);
    }



    @Test
    public void save() throws Exception {
        MetaManyToManyAddDTO addDTO = MetaManyToManyData.getAddDTO(metaProject.getProjectId(),
                metaEntity1.getEntityId(),metaEntity2.getEntityId());
        restMockMvc.perform(post(getRootPath()+"/meta_mtm/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.toJSONString(addDTO)))
                .andExpect(jsonPath("$.code").value(is("0")));

    }

    @Test
    public void update() throws Exception {
        MetaManyToManyPO metaManyToMany = generateHelper.saveManyToManyExample(metaProject.getProjectId(),
                metaEntity1.getEntityId(),metaEntity2.getEntityId());
        MetaManyToManyUpdateDTO updateDTO = MetaManyToManyData.getUpdateDTO(metaManyToMany);
        restMockMvc.perform(put(getRootPath()+"/meta_mtm/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.toJSONString(updateDTO)))
                .andExpect(jsonPath("$.code").value(is("0")));
    }


    @Test
    public void list() throws Exception {
        generateHelper.saveManyToManyExample(metaProject.getProjectId(),
                metaEntity1.getEntityId(),metaEntity2.getEntityId());
        restMockMvc.perform(get(getRootPath()+"/meta_mtm/list")
                .param("projectId",metaProject.getProjectId()+""))
                .andExpect(jsonPath("$.code").value(is("0")))
                .andExpect(jsonPath("$.data.length()").value(is(1)));
    }

    @Test
    public void show() throws Exception {
        MetaManyToManyPO metaManyToMany = generateHelper.saveManyToManyExample(metaProject.getProjectId(),
                metaEntity1.getEntityId(),metaEntity2.getEntityId());
        restMockMvc.perform(get(getRootPath()+"/meta_mtm/{fieldId}",metaManyToMany.getMtmId()))
                .andExpect(jsonPath("$.code").value(is("0")))
                .andExpect(jsonPath("$.data.mtmId").value(is(metaManyToMany.getMtmId())));
    }

    @Test
    public void del() throws Exception {
        MetaManyToManyPO metaManyToMany = generateHelper.saveManyToManyExample(metaProject.getProjectId(),
                metaEntity1.getEntityId(),metaEntity2.getEntityId());
        restMockMvc.perform(delete(getRootPath()+"/meta_mtm/{fieldId}",metaManyToMany.getMtmId()))
                .andExpect(jsonPath("$.code").value(is("0")))
                .andExpect(jsonPath("$.data").value(is(1)));
    }
}
