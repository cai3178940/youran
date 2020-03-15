package com.youran.generate.pojo.mapper;

import com.youran.generate.pojo.dto.MetaFieldAddDTO;
import com.youran.generate.pojo.dto.MetaFieldUpdateDTO;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.vo.MetaFieldShowVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * 字段映射
 * <p>基于mapstruct来实现，编译器自动生成实现类
 *
 * @author: cbb
 * @date: 2017/5/12
 */
@Mapper
public interface MetaFieldMapper {

    MetaFieldMapper INSTANCE = Mappers.getMapper(MetaFieldMapper.class);

    /**
     * addDTO映射po
     *
     * @param addDTO
     * @return
     */
    MetaFieldPO fromAddDTO(MetaFieldAddDTO addDTO);

    /**
     * 将updateDTO中的值设置到po
     *
     * @param metaFieldPO
     * @param metaFieldUpdateDTO
     */
    void setPO(@MappingTarget MetaFieldPO metaFieldPO, MetaFieldUpdateDTO metaFieldUpdateDTO);


    /**
     * po映射showVO
     *
     * @param metaFieldPO
     * @return
     */
    MetaFieldShowVO toShowVO(MetaFieldPO metaFieldPO);


    @BeanMapping(ignoreByDefault = true)
    @Mappings({
        @Mapping(target = "jfieldName"),
        @Mapping(target = "fieldName"),
        @Mapping(target = "jfieldType"),
        @Mapping(target = "fieldType"),
        @Mapping(target = "fieldDesc"),
        @Mapping(target = "fieldExample"),
        @Mapping(target = "fieldComment"),
        @Mapping(target = "fieldLength"),
        @Mapping(target = "fieldScale"),
        @Mapping(target = "primaryKey"),
        @Mapping(target = "autoIncrement"),
        @Mapping(target = "pkStrategy"),
        @Mapping(target = "notNull"),
        @Mapping(target = "defaultValue"),
        @Mapping(target = "foreignKey"),
        @Mapping(target = "foreignFieldId"),
        @Mapping(target = "editType"),
        @Mapping(target = "dicType"),
        @Mapping(target = "insert"),
        @Mapping(target = "update"),
        @Mapping(target = "list"),
        @Mapping(target = "columnWidth"),
        @Mapping(target = "listSort"),
        @Mapping(target = "show"),
        @Mapping(target = "query"),
        @Mapping(target = "queryType"),
        @Mapping(target = "orderNo"),
        @Mapping(target = "specialField"),
    })
    MetaFieldPO copy(MetaFieldPO field);


}
