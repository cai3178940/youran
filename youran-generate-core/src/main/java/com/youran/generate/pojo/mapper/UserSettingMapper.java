package com.youran.generate.pojo.mapper;

import com.youran.generate.pojo.dto.UserSettingAddDTO;
import com.youran.generate.pojo.dto.UserSettingUpdateDTO;
import com.youran.generate.pojo.po.UserSettingPO;
import com.youran.generate.pojo.vo.UserSettingShowVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * 【用户配置】映射
 *
 * @author cbb
 * @date 2019/11/08
 */
@Mapper
public interface UserSettingMapper {

    UserSettingMapper INSTANCE = Mappers.getMapper( UserSettingMapper.class );

    /**
    * addDTO映射po
    * @param userSettingAddDTO
    * @return
    */
    UserSettingPO fromAddDTO(UserSettingAddDTO userSettingAddDTO);

    /**
    * 将updateDTO中的值设置到po
    * @param userSettingPO
    * @param userSettingUpdateDTO
    */
    void setUpdateDTO(@MappingTarget UserSettingPO userSettingPO, UserSettingUpdateDTO userSettingUpdateDTO);

    /**
    * po映射showVO
    * @param userSettingPO
    * @return
    */
    UserSettingShowVO toShowVO(UserSettingPO userSettingPO);

}

