package com.youran.generate.web.rest;

import com.google.common.collect.Lists;
import com.youran.common.util.JsonUtil;
import com.youran.generate.help.MetaMtmCascadeExtHelper;
import com.youran.generate.pojo.dto.MetaMtmCascadeExtAddDTO;
import com.youran.generate.pojo.dto.MetaMtmCascadeExtUpdateDTO;
import com.youran.generate.pojo.po.MetaMtmCascadeExtPO;
import com.youran.generate.web.AbstractWebTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>Title: 【多对多级联扩展】单元测试</p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/09/21
 */
public class MetaMtmCascadeExtControllerTest extends AbstractWebTest {

    @Autowired
    private MetaMtmCascadeExtHelper metaMtmCascadeExtHelper;


    @Test
    public void save() throws Exception {
        MetaMtmCascadeExtAddDTO addDTO = metaMtmCascadeExtHelper.getMetaMtmCascadeExtAddDTO();
        restMockMvc.perform(post(getApiPath() + "/metaMtmCascadeExt")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(status().isCreated());
    }

    @Test
    public void update() throws Exception {
        MetaMtmCascadeExtPO metaMtmCascadeExt = metaMtmCascadeExtHelper.saveMetaMtmCascadeExtExample();
        MetaMtmCascadeExtUpdateDTO updateDTO = metaMtmCascadeExtHelper.getMetaMtmCascadeExtUpdateDTO(metaMtmCascadeExt);
        restMockMvc.perform(put(getApiPath() + "/metaMtmCascadeExt")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(updateDTO)))
            .andExpect(status().isOk());
    }

    @Test
    public void list() throws Exception {
        MetaMtmCascadeExtPO metaMtmCascadeExt = metaMtmCascadeExtHelper.saveMetaMtmCascadeExtExample();
        restMockMvc.perform(get(getApiPath() + "/metaMtmCascadeExt"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(is(1)));
    }

    @Test
    public void show() throws Exception {
        MetaMtmCascadeExtPO metaMtmCascadeExt = metaMtmCascadeExtHelper.saveMetaMtmCascadeExtExample();
        restMockMvc.perform(get(getApiPath() + "/metaMtmCascadeExt/{cascadeMtmExtId}",metaMtmCascadeExt.getCascadeMtmExtId()))
            .andExpect(status().isOk());
    }

    @Test
    public void del() throws Exception {
        MetaMtmCascadeExtPO metaMtmCascadeExt = metaMtmCascadeExtHelper.saveMetaMtmCascadeExtExample();
        restMockMvc.perform(delete(getApiPath() + "/metaMtmCascadeExt/{cascadeMtmExtId}",metaMtmCascadeExt.getCascadeMtmExtId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(is(1)));
    }

    @Test
    public void deleteBatch() throws Exception {
        MetaMtmCascadeExtPO metaMtmCascadeExt = metaMtmCascadeExtHelper.saveMetaMtmCascadeExtExample();
        restMockMvc.perform(delete(getApiPath() + "/metaMtmCascadeExt")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(Lists.newArrayList(metaMtmCascadeExt.getCascadeMtmExtId()))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(is(1)));
    }



}
