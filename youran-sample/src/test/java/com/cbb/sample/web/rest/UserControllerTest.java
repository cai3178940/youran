
package com.cbb.sample.web.rest;

import com.cbb.sample.help.UserHelper;
import com.cbb.sample.pojo.dto.UserAddDTO;
import com.cbb.sample.pojo.dto.UserQueryDTO;
import com.cbb.sample.pojo.dto.UserUpdateDTO;
import com.cbb.sample.pojo.po.UserPO;
import com.cbb.sample.web.AbstractWebTest;
import com.youran.common.util.DateUtil;
import com.youran.common.util.JsonUtil;
import com.youran.common.util.SafeUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static com.cbb.sample.pojo.example.UserExample.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


/**
 * Title: 【用户】单元测试
 * Description:
 * Author: cbb
 * Create Time: 2017-09-13 15:14
 */
public class UserControllerTest extends AbstractWebTest {

    @Autowired
    private UserHelper userHelper;

    @Test
    public void save() throws Exception {
        UserAddDTO addDTO = userHelper.getUserAddDTO();
        restMockMvc.perform(post(getRootPath()+"/user/save")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(jsonPath("$.errorCode").value(is(0)));
    }

    @Test
    public void update() throws Exception {
        UserPO user = userHelper.saveUserExample();
        UserUpdateDTO updateDTO = userHelper.getUserUpdateDTO(user);
        restMockMvc.perform(put(getRootPath()+"/user/update")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(updateDTO)))
            .andExpect(jsonPath("$.errorCode").value(is(0)));
    }

    @Test
    public void list() throws Exception {
        userHelper.saveUserExample();
        UserQueryDTO queryDTO = new UserQueryDTO();
        queryDTO.setUserName(E_USERNAME);
        queryDTO.setSex(SafeUtil.getInteger(E_SEX));
        queryDTO.setBirthdayStart(DateUtil.parseDate(E_BIRTHDAY));
        queryDTO.setBirthdayEnd(DateUtil.parseDate(E_BIRTHDAY));
        String content = JsonUtil.toJSONString(queryDTO);
        restMockMvc.perform(post(getRootPath()+"/user/list")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(content))
            .andExpect(jsonPath("$.errorCode").value(is(0)))
            .andExpect(jsonPath("$.data.entities.length()").value(is(1)));
    }

    @Test
    public void show() throws Exception {
        UserPO user = userHelper.saveUserExample();
        restMockMvc.perform(get(getRootPath()+"/user/{id}",user.getId()))
            .andExpect(jsonPath("$.errorCode").value(is(0)));
    }

    @Test
    public void del() throws Exception {
        UserPO user = userHelper.saveUserExample();
        restMockMvc.perform(delete(getRootPath()+"/user/{id}",user.getId()))
            .andExpect(jsonPath("$.errorCode").value(is(0)))
            .andExpect(jsonPath("$.data").value(is(1)));
    }

}

