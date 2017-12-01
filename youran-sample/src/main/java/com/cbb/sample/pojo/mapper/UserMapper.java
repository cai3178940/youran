package com.cbb.sample.pojo.mapper;

import com.cbb.sample.pojo.dto.UserAddDTO;
import com.cbb.sample.pojo.dto.UserUpdateDTO;
import com.cbb.sample.pojo.po.UserPO;
import com.cbb.sample.pojo.vo.UserShowVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/9/13 14:54
 */
@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    /**
     * addDTO映射po
     * @param userAddDTO
     * @return
     */
    UserPO fromAddDTO(UserAddDTO userAddDTO);

    /**
     * 将updateDTO中的值设置到po
     * @param userPO
     * @param userUpdateDTO
     */
    void setPO(@MappingTarget UserPO userPO, UserUpdateDTO userUpdateDTO);

    /**
     * po映射showVO
     * @param userPO
     * @return
     */
    UserShowVO toShowVO(UserPO userPO);

}
