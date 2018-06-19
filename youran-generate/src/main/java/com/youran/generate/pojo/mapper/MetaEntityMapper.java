package com.youran.generate.pojo.mapper;

import com.youran.generate.pojo.dto.MetaEntityAddDTO;
import com.youran.generate.pojo.dto.MetaEntityUpdateDTO;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.vo.MetaEntityShowVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * Title: 实体映射
 * Description: 基于mapstruct来实现，编译器自动生成实现类
 * Author: cbb
 * Create Time:2017/5/12 11:52
 */
@Mapper
public interface MetaEntityMapper {

    MetaEntityMapper INSTANCE = Mappers.getMapper( MetaEntityMapper.class );

    /**
     * addDTO映射po
     * @param addDTO
     * @return
     */
    MetaEntityPO fromAddDTO(MetaEntityAddDTO addDTO);

    /**
     * 将updateDTO中的值设置到po
     * @param metaEntityPO
     * @param metaEntityUpdateDTO
     */
    void setPO(@MappingTarget MetaEntityPO metaEntityPO, MetaEntityUpdateDTO metaEntityUpdateDTO);

    /**
     * po映射showVO
     * @param metaEntityPO
     * @return
     */
    MetaEntityShowVO toShowVO(MetaEntityPO metaEntityPO);
}
