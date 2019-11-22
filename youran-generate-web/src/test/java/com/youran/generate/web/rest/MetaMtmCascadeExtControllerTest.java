package com.youran.generate.web.rest;

import com.google.common.collect.Lists;
import com.youran.common.util.JsonUtil;
import com.youran.generate.help.GenerateHelper;
import com.youran.generate.help.MetaMtmCascadeExtHelper;
import com.youran.generate.pojo.dto.MetaMtmCascadeExtAddDTO;
import com.youran.generate.pojo.dto.MetaMtmCascadeExtUpdateDTO;
import com.youran.generate.pojo.po.*;
import com.youran.generate.web.AbstractWebTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 【多对多级联扩展】单元测试
 *
 * @author cbb
 * @date 2019/09/21
 */
public class MetaMtmCascadeExtControllerTest extends AbstractWebTest {

    @Autowired
    private GenerateHelper generateHelper;

    private MetaProjectPO metaProject;
    private MetaEntityPO metaEntity1;
    private MetaEntityPO metaEntity2;
    private MetaFieldPO metaField1;
    private MetaFieldPO metaField2;
    private MetaManyToManyPO metaManyToMany;

    @Before
    public void init() {
        this.metaProject = generateHelper.saveProjectExample();
        this.metaEntity1 = generateHelper.saveEntityExample(metaProject.getProjectId(), 1);
        this.metaEntity2 = generateHelper.saveEntityExample(metaProject.getProjectId(), 2);
        this.metaField1 = generateHelper.saveFieldExample(this.metaEntity1.getEntityId());
        this.metaField2 = generateHelper.saveFieldExample(this.metaEntity2.getEntityId());
        this.metaManyToMany = generateHelper.saveManyToManyExample(metaProject.getProjectId(),
            metaEntity1.getEntityId(), metaEntity2.getEntityId());
    }

    @Test
    public void save() throws Exception {
        MetaMtmCascadeExtAddDTO addDTO = MetaMtmCascadeExtHelper.getAddDTO(this.metaManyToMany.getMtmId(),
            metaEntity1.getEntityId(), metaEntity2.getEntityId(), metaField2.getFieldId());
        restMockMvc.perform(post(getApiPath() + "/meta_mtm_cascade_ext/save")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(status().isCreated());
    }

    @Test
    public void update() throws Exception {
        MetaMtmCascadeExtPO metaMtmCascadeExt = generateHelper.saveMtmCascadeExtExample(this.metaManyToMany.getMtmId(),
            metaEntity1.getEntityId(), metaEntity2.getEntityId(), metaField2.getFieldId());
        MetaMtmCascadeExtUpdateDTO updateDTO = MetaMtmCascadeExtHelper.getUpdateDTO(metaMtmCascadeExt);
        restMockMvc.perform(put(getApiPath() + "/meta_mtm_cascade_ext/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(updateDTO)))
            .andExpect(status().isOk());
    }

    @Test
    public void list() throws Exception {
        MetaMtmCascadeExtPO metaMtmCascadeExt = generateHelper.saveMtmCascadeExtExample(this.metaManyToMany.getMtmId(),
            metaEntity1.getEntityId(), metaEntity2.getEntityId(), metaField2.getFieldId());
        restMockMvc.perform(get(getApiPath() + "/meta_mtm_cascade_ext/list"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(is(1)));
    }

    @Test
    public void show() throws Exception {
        MetaMtmCascadeExtPO metaMtmCascadeExt = generateHelper.saveMtmCascadeExtExample(this.metaManyToMany.getMtmId(),
            metaEntity1.getEntityId(), metaEntity2.getEntityId(), metaField2.getFieldId());
        restMockMvc.perform(get(getApiPath() + "/meta_mtm_cascade_ext/{mtmCascadeExtId}", metaMtmCascadeExt.getMtmCascadeExtId()))
            .andExpect(status().isOk());
    }

    @Test
    public void del() throws Exception {
        MetaMtmCascadeExtPO metaMtmCascadeExt = generateHelper.saveMtmCascadeExtExample(this.metaManyToMany.getMtmId(),
            metaEntity1.getEntityId(), metaEntity2.getEntityId(), metaField2.getFieldId());
        restMockMvc.perform(delete(getApiPath() + "/meta_mtm_cascade_ext/{mtmCascadeExtId}", metaMtmCascadeExt.getMtmCascadeExtId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(is(1)));
    }

    @Test
    public void deleteBatch() throws Exception {
        MetaMtmCascadeExtPO metaMtmCascadeExt = generateHelper.saveMtmCascadeExtExample(this.metaManyToMany.getMtmId(),
            metaEntity1.getEntityId(), metaEntity2.getEntityId(), metaField2.getFieldId());
        restMockMvc.perform(put(getApiPath() + "/meta_mtm_cascade_ext")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJSONString(Lists.newArrayList(metaMtmCascadeExt.getMtmCascadeExtId()))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(is(1)));
    }


}
