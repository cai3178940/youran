package com.youran.generate.pojo.mapper;

import com.youran.generate.pojo.dto.MetaIndexAddDTO;
import com.youran.generate.pojo.dto.MetaIndexUpdateDTO;
import com.youran.generate.pojo.po.MetaIndexPO;
import com.youran.generate.pojo.vo.MetaIndexShowVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * Title: 元数据索引映射
 * Description: 基于mapstruct来实现，编译器自动生成实现类
 * Author: cbb
 * Create Time:2017/5/16 22:45
 */
@Mapper
public interface MetaIndexMapper {

    MetaIndexMapper INSTANCE = Mappers.getMapper( MetaIndexMapper.class );
    /**
     * addDTO映射po
     * @param addDTO
     * @return
     */
    MetaIndexPO fromAddDTO(MetaIndexAddDTO addDTO);

    /**
     * 将updateDTO中的值设置到po
     * @param metaIndexPO
     * @param metaIndexUpdateDTO
     */
    void setPO(@MappingTarget MetaIndexPO metaIndexPO, MetaIndexUpdateDTO metaIndexUpdateDTO);


    /**
     * po映射showVO
     * @param metaIndexPO
     * @return
     */
    MetaIndexShowVO toShowVO(MetaIndexPO metaIndexPO);

}
