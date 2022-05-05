package com.youran.generate.web.rest;

import com.youran.common.util.JsonUtil;
import com.youran.generate.help.GenerateHelper;
import com.youran.generate.help.MetaEntityHelper;
import com.youran.generate.pojo.dto.MetaEntityAddDTO;
import com.youran.generate.pojo.dto.MetaEntityUpdateDTO;
import com.youran.generate.pojo.po.MetaEntityPO;
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
public class MetaEntityControllerTest extends AbstractWebTest {

    @Autowired
    private GenerateHelper generateHelper;

    private MetaProjectPO metaProject;

    @BeforeEach
    public void init() {
        this.metaProject = generateHelper.saveProjectExample();
    }

    @Test
    public void save() throws Exception {
        MetaEntityAddDTO addDTO = MetaEntityHelper.getAddDTO(metaProject.getProjectId(), 0);
        restMockMvc.perform(post(getApiPath() + "/meta_entity/save")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    public void update() throws Exception {
        MetaEntityPO metaEntity = generateHelper.saveEntityExample(metaProject.getProjectId(), 0);
        MetaEntityUpdateDTO updateDTO = MetaEntityHelper.getUpdateDTO(metaEntity);
        restMockMvc.perform(put(getApiPath() + "/meta_entity/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(updateDTO)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void list() throws Exception {
        generateHelper.saveEntityExample(metaProject.getProjectId(), 0);
        restMockMvc.perform(get(getApiPath() + "/meta_entity/list")
            .param("projectId", metaProject.getProjectId() + ""))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.length()").value(is(1)));
    }

    @Test
    public void show() throws Exception {
        MetaEntityPO metaEntity = generateHelper.saveEntityExample(metaProject.getProjectId(), 0);
        restMockMvc.perform(get(getApiPath() + "/meta_entity/{entityId}", metaEntity.getEntityId()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.entityId").value(is(metaEntity.getEntityId())));
    }

    @Test
    public void del() throws Exception {
        MetaEntityPO metaEntity = generateHelper.saveEntityExample(metaProject.getProjectId(), 0);
        restMockMvc.perform(delete(getApiPath() + "/meta_entity/{entityId}", metaEntity.getEntityId()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$").value(is(1)));
    }


}
