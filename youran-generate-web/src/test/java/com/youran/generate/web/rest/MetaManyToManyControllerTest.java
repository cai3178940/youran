package com.youran.generate.web.rest;

import com.youran.common.util.JsonUtil;
import com.youran.generate.help.GenerateHelper;
import com.youran.generate.help.MetaManyToManyHelper;
import com.youran.generate.pojo.dto.MetaManyToManyAddDTO;
import com.youran.generate.pojo.dto.MetaManyToManyUpdateDTO;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaManyToManyPO;
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
 * @date: 2017/7/4
 */
public class MetaManyToManyControllerTest extends AbstractWebTest {

    @Autowired
    private GenerateHelper generateHelper;

    private MetaProjectPO metaProject;
    private MetaEntityPO metaEntity1;
    private MetaEntityPO metaEntity2;

    @BeforeEach
    public void init() {
        this.metaProject = generateHelper.saveProjectExample();
        this.metaEntity1 = generateHelper.saveEntityExample(metaProject.getProjectId(), 1);
        this.metaEntity2 = generateHelper.saveEntityExample(metaProject.getProjectId(), 2);
    }


    @Test
    public void save() throws Exception {
        MetaManyToManyAddDTO addDTO = MetaManyToManyHelper.getAddDTO(metaProject.getProjectId(),
            metaEntity1.getEntityId(), metaEntity2.getEntityId());
        restMockMvc.perform(post(getApiPath() + "/meta_mtm/save")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    public void update() throws Exception {
        MetaManyToManyPO metaManyToMany = generateHelper.saveManyToManyExample(metaProject.getProjectId(),
            metaEntity1.getEntityId(), metaEntity2.getEntityId());
        MetaManyToManyUpdateDTO updateDTO = MetaManyToManyHelper.getUpdateDTO(metaManyToMany);
        restMockMvc.perform(put(getApiPath() + "/meta_mtm/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(updateDTO)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void list() throws Exception {
        generateHelper.saveManyToManyExample(metaProject.getProjectId(),
            metaEntity1.getEntityId(), metaEntity2.getEntityId());
        restMockMvc.perform(get(getApiPath() + "/meta_mtm/list")
            .param("projectId", metaProject.getProjectId() + ""))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.length()").value(is(1)));
    }

    @Test
    public void show() throws Exception {
        MetaManyToManyPO metaManyToMany = generateHelper.saveManyToManyExample(metaProject.getProjectId(),
            metaEntity1.getEntityId(), metaEntity2.getEntityId());
        restMockMvc.perform(get(getApiPath() + "/meta_mtm/{fieldId}", metaManyToMany.getMtmId()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.mtmId").value(is(metaManyToMany.getMtmId())));
    }

    @Test
    public void del() throws Exception {
        MetaManyToManyPO metaManyToMany = generateHelper.saveManyToManyExample(metaProject.getProjectId(),
            metaEntity1.getEntityId(), metaEntity2.getEntityId());
        restMockMvc.perform(delete(getApiPath() + "/meta_mtm/{fieldId}", metaManyToMany.getMtmId()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$").value(is(1)));
    }
}
