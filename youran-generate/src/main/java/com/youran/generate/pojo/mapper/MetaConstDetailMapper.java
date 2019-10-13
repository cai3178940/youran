package com.youran.generate.pojo.mapper;

import com.youran.generate.pojo.dto.MetaConstDetailAddDTO;
import com.youran.generate.pojo.dto.MetaConstDetailUpdateDTO;
import com.youran.generate.pojo.po.MetaConstDetailPO;
import com.youran.generate.pojo.vo.MetaConstDetailShowVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * <p>Title:常量值映射</p>
 * <p>Description:基于mapstruct来实现，编译器自动生成实现类</p>
 * @author: cbb
 * @date: 2017/5/12
 */
@Mapper
public interface MetaConstDetailMapper {

    MetaConstDetailMapper INSTANCE = Mappers.getMapper( MetaConstDetailMapper.class );

    /**
     * addDTO映射po
     * @param addDTO
     * @return
     */
    MetaConstDetailPO fromAddDTO(MetaConstDetailAddDTO addDTO);

    /**
     * 将updateDTO中的值设置到po
     * @param metaConstDetailPO
     * @param metaConstDetailUpdateDTO
     */
    void setPO(@MappingTarget MetaConstDetailPO metaConstDetailPO, MetaConstDetailUpdateDTO metaConstDetailUpdateDTO);

    /**
     * po映射showVO
     * @param metaConstDetailPO
     * @return
     */
    MetaConstDetailShowVO toShowVO(MetaConstDetailPO metaConstDetailPO);

    @Mappings({
        @Mapping(target = "constDetailId",ignore = true)
    })
    MetaConstDetailPO copy(MetaConstDetailPO constDetailFromJson);


}
