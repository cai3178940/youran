package com.youran.generate.pojo.mapper.chart;

import com.youran.generate.pojo.dto.chart.source.item.*;
import com.youran.generate.pojo.po.chart.source.item.*;
import com.youran.generate.pojo.vo.chart.source.item.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * 【图表数据源项】映射
 *
 * @author cbb
 * @date 2020/04/04
 */
@Mapper
public interface MetaChartSourceItemMapper {

    MetaChartSourceItemMapper INSTANCE = Mappers.getMapper(MetaChartSourceItemMapper.class);

    void copyProperties(@MappingTarget MetaChartSourceItemPO target,
                        MetaChartSourceItemPO source);


    AggOrderPO fromAggOrderAddDTO(AggOrderAddDTO addDTO);
    WherePO fromWhereAddDTO(WhereAddDTO addDTO);
    MetricsPO fromMetricsAddDTO(MetricsAddDTO addDTO);
    HavingPO fromHavingAddDTO(HavingAddDTO addDTO);
    DimensionPO fromDimensionAddDTO(DimensionAddDTO addDTO);
    DetailOrderPO fromDetailOrderAddDTO(DetailOrderAddDTO addDTO);
    DetailColumnPO fromDetailColumnAddDTO(DetailColumnAddDTO addDTO);


    void setAggOrderUpdateDTO(@MappingTarget AggOrderPO po,
                              AggOrderUpdateDTO updateDTO);
    void setDetailColumnUpdateDTO(@MappingTarget DetailColumnPO po,
                                  DetailColumnUpdateDTO updateDTO);
    void setDetailOrderUpdateDTO(@MappingTarget DetailOrderPO po,
                                 DetailOrderUpdateDTO updateDTO);
    void setDimensionUpdateDTO(@MappingTarget DimensionPO po,
                               DimensionUpdateDTO updateDTO);
    void setHavingUpdateDTO(@MappingTarget HavingPO po,
                            HavingUpdateDTO updateDTO);
    void setMetricsUpdateDTO(@MappingTarget MetricsPO po,
                             MetricsUpdateDTO updateDTO);
    void setWhereUpdateDTO(@MappingTarget WherePO po,
                           WhereUpdateDTO updateDTO);


    default <T extends MetaChartSourceItemVO> T poToVO(MetaChartSourceItemPO po){
        if (po instanceof HavingPO) {
            return (T)this.toHavingVO((HavingPO) po);
        } else if (po instanceof DetailColumnPO) {
            return (T)this.toDetailColumnVO((DetailColumnPO) po);
        } else if (po instanceof MetricsPO) {
            return (T)this.toMetricsVO((MetricsPO) po);
        } else if (po instanceof DetailOrderPO) {
            return (T)this.toDetailOrderVO((DetailOrderPO) po);
        } else if (po instanceof WherePO) {
            return (T)this.toWhereVO((WherePO) po);
        } else if (po instanceof DimensionPO) {
            return (T)this.toDimensionVO((DimensionPO) po);
        } else if (po instanceof AggOrderPO) {
            return (T)this.toAggOrderVO((AggOrderPO) po);
        }
        return null;
    }

    HavingVO toHavingVO(HavingPO po);
    DetailColumnVO toDetailColumnVO(DetailColumnPO po);
    MetricsVO toMetricsVO(MetricsPO po);
    DetailOrderVO toDetailOrderVO(DetailOrderPO po);
    WhereVO toWhereVO(WherePO po);
    DimensionVO toDimensionVO(DimensionPO po);
    AggOrderVO toAggOrderVO(AggOrderPO po);

    @BeanMapping(ignoreByDefault = true)
    @Mappings({
        @Mapping(target = "key"),
        @Mapping(target = "joinIndex"),
        @Mapping(target = "type"),
        @Mapping(target = "parentId"),
        @Mapping(target = "parentKey"),
        @Mapping(target = "feature"),
    })
    MetaChartSourceItemPO copy(MetaChartSourceItemPO metaChartSourceItemFromJson);
}

