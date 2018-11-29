package com.youran.generate.web.rest;

import com.youran.common.pojo.vo.ReplyVO;
import com.youran.common.util.JsonUtil;
import com.youran.generate.data.MetaFieldData;
import com.youran.generate.help.GenerateHelper;
import com.youran.generate.pojo.dto.MetaFieldAddDTO;
import com.youran.generate.pojo.dto.MetaFieldUpdateDTO;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaFieldPO;
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
public class MetaFieldControllerTest extends AbstractWebTest {

    @Autowired
    private GenerateHelper generateHelper;

    private MetaProjectPO metaProject;
    private MetaEntityPO metaEntity;

    @Before
    public void init(){
        this.metaProject = generateHelper.saveProjectExample();
        this.metaEntity = generateHelper.saveEntityExample(metaProject.getProjectId(),0);
    }

    @Test
    public void save() throws Exception {
        MetaFieldAddDTO addDTO = MetaFieldData.getAddDTO(metaEntity.getEntityId());
        restMockMvc.perform(post(getApiPath()+"/meta_field/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.toJSONString(addDTO)))
                .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)));

    }

    @Test
    public void update() throws Exception {
        MetaFieldPO metaField = generateHelper.saveFieldExample(metaEntity.getEntityId());
        MetaFieldUpdateDTO updateDTO = MetaFieldData.getUpdateDTO(metaField);
        restMockMvc.perform(put(getApiPath()+"/meta_field/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.toJSONString(updateDTO)))
                .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)));
    }


    @Test
    public void list() throws Exception {
        generateHelper.saveFieldExample(metaEntity.getEntityId());
        restMockMvc.perform(get(getApiPath()+"/meta_field/list")
                .param("entityId",metaEntity.getEntityId()+""))
                .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)))
                .andExpect(jsonPath("$.data.length()").value(is(1)));
    }

    @Test
    public void show() throws Exception {
        MetaFieldPO metaField = generateHelper.saveFieldExample(metaEntity.getEntityId());

        restMockMvc.perform(get(getApiPath()+"/meta_field/{fieldId}",metaField.getFieldId()))
                .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)))
                .andExpect(jsonPath("$.data.fieldId").value(is(metaField.getFieldId())));
    }

    @Test
    public void del() throws Exception {
        MetaFieldPO metaField = generateHelper.saveFieldExample(metaEntity.getEntityId());

        restMockMvc.perform(delete(getApiPath()+"/meta_field/{fieldId}",metaField.getFieldId()))
                .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)))
                .andExpect(jsonPath("$.data").value(is(1)));
    }



}
