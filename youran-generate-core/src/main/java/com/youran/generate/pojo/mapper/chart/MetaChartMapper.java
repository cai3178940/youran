package com.youran.generate.pojo.mapper.chart;

import com.youran.generate.pojo.dto.chart.*;
import com.youran.generate.pojo.po.chart.*;
import com.youran.generate.pojo.vo.chart.AggTableVO;
import com.youran.generate.pojo.vo.chart.BarLineVO;
import com.youran.generate.pojo.vo.chart.DetailListVO;
import com.youran.generate.pojo.vo.chart.PieVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * 【图表】映射
 *
 * @author cbb
 * @date 2020/04/04
 */
@Mapper
public interface MetaChartMapper {

    MetaChartMapper INSTANCE = Mappers.getMapper( MetaChartMapper.class );

    void copyProperties(@MappingTarget MetaChartPO target,
                        MetaChartPO source);

    AggTablePO fromAggTableAddDTO(AggTableAddDTO addDTO);
    BarLinePO fromBarLineAddDTO(BarLineAddDTO addDTO);
    DetailListPO fromDetailListAddDTO(DetailListAddDTO addDTO);
    PiePO fromPieAddDTO(PieAddDTO addDTO);



    void setAggTableUpdateDTO(@MappingTarget AggTablePO po,
                              AggTableUpdateDTO updateDTO);
    void setBarLineUpdateDTO(@MappingTarget BarLinePO po,
                              BarLineUpdateDTO updateDTO);
    void setDetailListUpdateDTO(@MappingTarget DetailListPO po,
                              DetailListUpdateDTO updateDTO);
    void setPieUpdateDTO(@MappingTarget PiePO po,
                              PieUpdateDTO updateDTO);



    AggTableVO toAggTableVO(AggTablePO po);
    BarLineVO toBarLineVO(BarLinePO po);
    DetailListVO toDetailListVO(DetailListPO po);
    PieVO toPieVO(PiePO po);

    @BeanMapping(ignoreByDefault = true)
    @Mappings({
        @Mapping(target = "chartType"),
        @Mapping(target = "chartName"),
        @Mapping(target = "module"),
        @Mapping(target = "title"),
        @Mapping(target = "feature"),
    })
    MetaChartPO copy(MetaChartPO metaChartSourceFromJson);


}

