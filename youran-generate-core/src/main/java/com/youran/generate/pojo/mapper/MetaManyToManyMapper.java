package com.youran.generate.pojo.mapper;

import com.youran.generate.pojo.dto.MetaManyToManyAddDTO;
import com.youran.generate.pojo.dto.MetaManyToManyUpdateDTO;
import com.youran.generate.pojo.po.MetaManyToManyPO;
import com.youran.generate.pojo.vo.MetaManyToManyShowVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * 多对多映射
 *
 * @author: cbb
 * @date: 2017/7/4
 */
@Mapper(uses = FeatureMapper.class)
public interface MetaManyToManyMapper {

    MetaManyToManyMapper INSTANCE = Mappers.getMapper(MetaManyToManyMapper.class);

    /**
     * addDTO映射po
     *
     * @param dto
     * @return
     */
    @Mappings(value = {
        @Mapping(target = "f1", expression = "java(dto.getFeature()==null ? null:dto.getFeature().getF1())"),
        @Mapping(target = "f2", expression = "java(dto.getFeature()==null ? null:dto.getFeature().getF2())")
    })
    MetaManyToManyPO fromAddDTO(MetaManyToManyAddDTO dto);

    /**
     * 将updateDTO中的值设置到po
     *
     * @param metaManyToManyPO
     * @param dto
     */
    @Mappings(value = {
        @Mapping(target = "f1", expression = "java(dto.getFeature()==null ? null:dto.getFeature().getF1())"),
        @Mapping(target = "f2", expression = "java(dto.getFeature()==null ? null:dto.getFeature().getF2())")
    })
    void setPO(@MappingTarget MetaManyToManyPO metaManyToManyPO, MetaManyToManyUpdateDTO dto);


    /**
     * po映射showVO
     *
     * @param metaManyToManyPO
     * @return
     */
    MetaManyToManyShowVO toShowVO(MetaManyToManyPO metaManyToManyPO);


    @BeanMapping(ignoreByDefault = true)
    @Mappings({
        @Mapping(target = "tableName"),
        @Mapping(target = "schemaName"),
        @Mapping(target = "desc"),
        @Mapping(target = "holdRefer1"),
        @Mapping(target = "holdRefer2"),
        @Mapping(target = "entityIdField1"),
        @Mapping(target = "entityIdField2"),
        @Mapping(target = "needId"),
        @Mapping(target = "bigId"),
        @Mapping(target = "feature"),
    })
    MetaManyToManyPO copy(MetaManyToManyPO mtmFromJson);


}
