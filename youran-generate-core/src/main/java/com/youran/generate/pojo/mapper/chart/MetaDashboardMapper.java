package com.youran.generate.pojo.mapper.chart;

import com.youran.generate.pojo.dto.chart.MetaDashboardAddDTO;
import com.youran.generate.pojo.dto.chart.MetaDashboardUpdateDTO;
import com.youran.generate.pojo.po.chart.MetaDashboardPO;
import com.youran.generate.pojo.vo.chart.MetaDashboardShowVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * 【看板】映射
 *
 * @author cbb
 * @date 2020/06/13
 */
@Mapper
public interface MetaDashboardMapper {

    MetaDashboardMapper INSTANCE = Mappers.getMapper( MetaDashboardMapper.class );

    /**
     * addDTO映射po
     *
     * @param metaDashboardAddDTO
     * @return
     */
    MetaDashboardPO fromAddDTO(MetaDashboardAddDTO metaDashboardAddDTO);

    /**
     * 将updateDTO中的值设置到po
     *
     * @param metaDashboardPO
     * @param metaDashboardUpdateDTO
     */
    void setUpdateDTO(@MappingTarget MetaDashboardPO metaDashboardPO, MetaDashboardUpdateDTO metaDashboardUpdateDTO);

    /**
     * po映射showVO
     *
     * @param metaDashboardPO
     * @return
     */
    MetaDashboardShowVO toShowVO(MetaDashboardPO metaDashboardPO);


}

