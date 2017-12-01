package com.youran.generate.pojo.mapper;

import com.youran.generate.pojo.dto.MetaManyToManyAddDTO;
import com.youran.generate.pojo.dto.MetaManyToManyUpdateDTO;
import com.youran.generate.pojo.po.MetaManyToManyPO;
import com.youran.generate.pojo.vo.MetaManyToManyShowVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/7/4 16:43
 */
@Mapper
public interface MetaManyToManyMapper {

    MetaManyToManyMapper INSTANCE = Mappers.getMapper( MetaManyToManyMapper.class );

    /**
     * addDTO映射po
     * @param addDTO
     * @return
     */
    MetaManyToManyPO fromAddDTO(MetaManyToManyAddDTO addDTO);

    /**
     * 将updateDTO中的值设置到po
     * @param metaManyToManyPO
     * @param metaManyToManyUpdateDTO
     */
    void setPO(@MappingTarget MetaManyToManyPO metaManyToManyPO, MetaManyToManyUpdateDTO metaManyToManyUpdateDTO);


    /**
     * po映射showVO
     * @param metaManyToManyPO
     * @return
     */
    MetaManyToManyShowVO toShowVO(MetaManyToManyPO metaManyToManyPO);
}
