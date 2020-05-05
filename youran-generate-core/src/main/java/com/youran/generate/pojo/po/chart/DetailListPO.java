package com.youran.generate.pojo.po.chart;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.ChartType;
import com.youran.generate.pojo.dto.chart.ChartFeatureDTO;
import com.youran.generate.pojo.dto.chart.ChartItemDTO;
import com.youran.generate.pojo.mapper.FeatureMapper;
import com.youran.generate.pojo.mapper.chart.MetaChartMapper;
import com.youran.generate.pojo.po.chart.source.MetaChartSourcePO;
import com.youran.generate.pojo.po.chart.source.item.DetailColumnPO;

import java.util.List;

/**
 * 明细表
 *
 * @author: cbb
 * @date: 2020-04-05
 */
public class DetailListPO extends MetaChartPO {

    /**
     * 明细列
     */
    private List<ChartItemDTO<DetailColumnPO>> columnList;
    /**
     * 默认每页记录数
     */
    private Integer defaultPageSize;

    public DetailListPO() {
        this.setChartType(ChartType.DETAIL_LIST.getValue());
    }


    /**
     * 将超类转成当前类型
     *
     * @param superPO
     * @param featureDeserialize
     * @return
     */
    public static DetailListPO fromSuperType(MetaChartPO superPO,
                                             boolean featureDeserialize) {
        if (!ChartType.DETAIL_LIST.getValue().equals(superPO.getChartType())) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "类型转换异常");
        }
        DetailListPO po = new DetailListPO();
        MetaChartMapper.INSTANCE.copyProperties(po, superPO);
        if (featureDeserialize) {
            po.featureDeserialize();
        }
        return po;
    }


    @Override
    public void assemble(MetaChartSourcePO chartSource) {
        super.assemble(chartSource);
        for (ChartItemDTO chartItem : columnList) {
            Integer sourceItemId = chartItem.getSourceItemId();
            DetailColumnPO detailColumn = chartSource.getDetailColumnMap().get(sourceItemId);
            if (detailColumn != null) {
                chartItem.setSourceItem(detailColumn);
            } else {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR,
                    "图表【" + this.getTitle() + "】明细列不存在，sourceItemId=" + sourceItemId);
            }
        }
    }

    @Override
    public void featureDeserialize() {
        ChartFeatureDTO featureDTO = FeatureMapper.asChartFeatureDTO(this.getFeature());
        this.columnList = (List<ChartItemDTO<DetailColumnPO>>) featureDTO.getColumnList();
        this.defaultPageSize = featureDTO.getDefaultPageSize();
    }

    @Override
    public void featureSerialize() {
        ChartFeatureDTO featureDTO = new ChartFeatureDTO();
        featureDTO.setColumnList(this.columnList);
        featureDTO.setDefaultPageSize(this.defaultPageSize);
        this.setFeature(FeatureMapper.asString(featureDTO));
    }

    public List<ChartItemDTO<DetailColumnPO>> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<ChartItemDTO<DetailColumnPO>> columnList) {
        this.columnList = columnList;
    }

    public Integer getDefaultPageSize() {
        return defaultPageSize;
    }

    public void setDefaultPageSize(Integer defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }
}
