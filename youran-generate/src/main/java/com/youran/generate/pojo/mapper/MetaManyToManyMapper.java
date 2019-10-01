package com.youran.generate.pojo.mapper;

import com.youran.generate.pojo.dto.MetaManyToManyAddDTO;
import com.youran.generate.pojo.dto.MetaManyToManyUpdateDTO;
import com.youran.generate.pojo.po.MetaManyToManyPO;
import com.youran.generate.pojo.vo.MetaManyToManyShowVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/7/4
 */
@Mapper(uses = FeatureMapper.class)
public interface MetaManyToManyMapper {

    MetaManyToManyMapper INSTANCE = Mappers.getMapper( MetaManyToManyMapper.class );

    /**
     * addDTO映射po
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
     * @param metaManyToManyPO
     * @param metaManyToManyUpdateDTO
     */
    @Mappings(value = {
        @Mapping(target = "f1", expression = "java(dto.getFeature()==null ? null:dto.getFeature().getF1())"),
        @Mapping(target = "f2", expression = "java(dto.getFeature()==null ? null:dto.getFeature().getF2())")
    })
    void setPO(@MappingTarget MetaManyToManyPO metaManyToManyPO, MetaManyToManyUpdateDTO dto);


    /**
     * po映射showVO
     * @param metaManyToManyPO
     * @return
     */
    MetaManyToManyShowVO toShowVO(MetaManyToManyPO metaManyToManyPO);
}
