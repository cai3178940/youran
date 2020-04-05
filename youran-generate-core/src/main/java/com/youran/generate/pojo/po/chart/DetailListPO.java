package com.youran.generate.pojo.po.chart;

import com.youran.generate.constant.ChartType;
import com.youran.generate.pojo.dto.chart.ChartFeatureDTO;
import com.youran.generate.pojo.dto.chart.ChartItemDTO;
import com.youran.generate.pojo.mapper.FeatureMapper;

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
    private List<ChartItemDTO> columnList;

    public DetailListPO() {
        this.setChartType(ChartType.DETAIL_LIST.getValue());
    }

    @Override
    public void featureDeserialize() {
        ChartFeatureDTO featureDTO = FeatureMapper.asChartFeatureDTO(this.getFeature());
        this.columnList = featureDTO.getColumnList();
    }

    @Override
    public void featureSerialize() {
        ChartFeatureDTO featureDTO = new ChartFeatureDTO();
        featureDTO.setColumnList(this.columnList);
        this.setFeature(FeatureMapper.asString(featureDTO));
    }

    public List<ChartItemDTO> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<ChartItemDTO> columnList) {
        this.columnList = columnList;
    }
}
