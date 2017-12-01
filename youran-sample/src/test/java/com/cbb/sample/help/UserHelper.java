package com.cbb.sample.help;

import com.cbb.sample.pojo.dto.UserAddDTO;
import com.cbb.sample.pojo.dto.UserUpdateDTO;
import com.cbb.sample.pojo.po.UserPO;
import com.cbb.sample.service.UserService;
import com.youran.common.util.DateUtil;
import com.youran.common.util.SafeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.cbb.sample.pojo.example.UserExample.*;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/8/24 14:05
 */
@Component
public class UserHelper {

    @Autowired
    protected UserService userService;


    /**
     * 生成add测试数据
     * @return
     */
    public UserAddDTO getUserAddDTO(){
        UserAddDTO dto = new UserAddDTO();
        dto.setUserName(E_USERNAME);
        dto.setSex(SafeUtil.getInteger(E_SEX));
        dto.setBirthday(DateUtil.parseDate(E_BIRTHDAY));
        return dto;
    }


    /**
     * 生成update测试数据
     * @return
     */
    public UserUpdateDTO getUserUpdateDTO(UserPO user){
        UserUpdateDTO dto = new UserUpdateDTO();
        dto.setId(user.getId());
        dto.setUserName(user.getUserName()+"cbb");
        dto.setSex(user.getSex());
        dto.setBirthday(user.getBirthday());
        return dto;
    }

    /**
     * 保存【用户】示例
     * @return
     */
    public UserPO saveUserExample(){
        UserAddDTO addDTO = this.getUserAddDTO();
        return userService.save(addDTO);
    }



}
