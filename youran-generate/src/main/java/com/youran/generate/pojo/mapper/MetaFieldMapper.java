package com.youran.generate.pojo.mapper;

import com.youran.generate.pojo.dto.MetaFieldAddDTO;
import com.youran.generate.pojo.dto.MetaFieldUpdateDTO;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.vo.MetaFieldShowVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * Title:字段映射
 * Description: 基于mapstruct来实现，编译器自动生成实现类
 * Author: cbb
 * Create Time:2017/5/12 11:52
 */
@Mapper
public interface MetaFieldMapper {

    MetaFieldMapper INSTANCE = Mappers.getMapper( MetaFieldMapper.class );
    /**
     * addDTO映射po
     * @param addDTO
     * @return
     */
    MetaFieldPO fromAddDTO(MetaFieldAddDTO addDTO);

    /**
     * 将updateDTO中的值设置到po
     * @param metaFieldPO
     * @param metaFieldUpdateDTO
     */
    void setPO(@MappingTarget MetaFieldPO metaFieldPO, MetaFieldUpdateDTO metaFieldUpdateDTO);


    /**
     * po映射showVO
     * @param metaFieldPO
     * @return
     */
    MetaFieldShowVO toShowVO(MetaFieldPO metaFieldPO);
}
