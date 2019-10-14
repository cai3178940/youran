package com.youran.generate.pojo.mapper;

import com.youran.generate.pojo.dto.MetaCascadeExtAddDTO;
import com.youran.generate.pojo.dto.MetaCascadeExtUpdateDTO;
import com.youran.generate.pojo.po.MetaCascadeExtPO;
import com.youran.generate.pojo.vo.MetaCascadeExtShowVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2018/5/28
 */
@Mapper
public interface MetaCascadeExtMapper {

    MetaCascadeExtMapper INSTANCE = Mappers.getMapper( MetaCascadeExtMapper.class );

    /**
     * addDTO映射po
     * @param addDTO
     * @return
     */
    MetaCascadeExtPO fromAddDTO(MetaCascadeExtAddDTO addDTO);

    /**
     * 将updateDTO中的值设置到po
     * @param po
     * @param updateDTO
     */
    void setPO(@MappingTarget MetaCascadeExtPO po, MetaCascadeExtUpdateDTO updateDTO);

    /**
     * po映射showVO
     * @param po
     * @return
     */
    MetaCascadeExtShowVO toShowVO(MetaCascadeExtPO po);

    @BeanMapping(ignoreByDefault = true)
    @Mappings({
        @Mapping(target = "alias"),
        @Mapping(target = "list"),
        @Mapping(target = "show"),
        @Mapping(target = "query"),
    })
    MetaCascadeExtPO copy(MetaCascadeExtPO cascadeExtFromJson);


}
