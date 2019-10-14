package com.youran.generate.pojo.mapper;

import com.youran.generate.pojo.dto.MetaMtmCascadeExtAddDTO;
import com.youran.generate.pojo.dto.MetaMtmCascadeExtUpdateDTO;
import com.youran.generate.pojo.po.MetaMtmCascadeExtPO;
import com.youran.generate.pojo.vo.MetaMtmCascadeExtShowVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * <p>Title: 【多对多级联扩展】映射</p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/09/21
 */
@Mapper
public interface MetaMtmCascadeExtMapper {

    MetaMtmCascadeExtMapper INSTANCE = Mappers.getMapper( MetaMtmCascadeExtMapper.class );

    /**
    * addDTO映射po
    * @param metaMtmCascadeExtAddDTO
    * @return
    */
    MetaMtmCascadeExtPO fromAddDTO(MetaMtmCascadeExtAddDTO metaMtmCascadeExtAddDTO);

    /**
    * 将updateDTO中的值设置到po
    * @param metaMtmCascadeExtPO
    * @param metaMtmCascadeExtUpdateDTO
    */
    void setUpdateDTO(@MappingTarget MetaMtmCascadeExtPO metaMtmCascadeExtPO, MetaMtmCascadeExtUpdateDTO metaMtmCascadeExtUpdateDTO);

    /**
    * po映射showVO
    * @param metaMtmCascadeExtPO
    * @return
    */
    MetaMtmCascadeExtShowVO toShowVO(MetaMtmCascadeExtPO metaMtmCascadeExtPO);

    @BeanMapping(ignoreByDefault = true)
    @Mappings({
        @Mapping(target = "alias"),
        @Mapping(target = "list"),
        @Mapping(target = "show"),
        @Mapping(target = "query"),
    })
    MetaMtmCascadeExtPO copy(MetaMtmCascadeExtPO mtmCascadeExtFromJson);


}

