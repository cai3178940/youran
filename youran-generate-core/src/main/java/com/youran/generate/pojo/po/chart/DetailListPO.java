package com.youran.generate.pojo.po.chart;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.util.JsonUtil;
import com.youran.generate.constant.ChartType;
import com.youran.generate.pojo.dto.chart.ChartItemDTO;
import com.youran.generate.pojo.mapper.chart.MetaChartMapper;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaManyToManyPO;
import com.youran.generate.pojo.po.chart.source.MetaChartSourcePO;
import com.youran.generate.pojo.po.chart.source.item.DetailColumnPO;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
     * 隐藏明细列
     */
    private List<ChartItemDTO<DetailColumnPO>> hiddenColumnList;
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
            }
        }
        // 过滤掉无效的列
        this.columnList = this.columnList.stream().filter(item -> item.getSourceItem() != null)
            .collect(Collectors.toList());
    }

    @Override
    public void check(Map<Integer, MetaEntityPO> entityMap, Map<Integer, MetaManyToManyPO> mtmMap) {
        super.check(entityMap, mtmMap);
        if (CollectionUtils.isEmpty(columnList)) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR,
                "图表【" + this.getTitle() + "】未配置明细列");
        }
        for (ChartItemDTO chartItem : columnList) {
            if (chartItem.getSourceItem() == null) {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR,
                    "图表【" + this.getTitle() + "】明细列不存在：" + chartItem.getTitleAlias());
            }
        }
    }

    @Override
    public void featureDeserialize() {
        FeatureDTO featureDTO = JsonUtil.parseObject(this.getFeature(), FeatureDTO.class);
        this.columnList = featureDTO.getColumnList();
        this.hiddenColumnList = featureDTO.getHiddenColumnList();
        this.defaultPageSize = featureDTO.getDefaultPageSize();
    }

    @Override
    public void featureSerialize() {
        FeatureDTO featureDTO = new FeatureDTO();
        featureDTO.setColumnList(this.columnList);
        featureDTO.setHiddenColumnList(this.hiddenColumnList);
        featureDTO.setDefaultPageSize(this.defaultPageSize);
        this.setFeature(JsonUtil.toJSONString(featureDTO));
    }

    public List<ChartItemDTO<DetailColumnPO>> getHiddenColumnList() {
        return hiddenColumnList;
    }

    public void setHiddenColumnList(List<ChartItemDTO<DetailColumnPO>> hiddenColumnList) {
        this.hiddenColumnList = hiddenColumnList;
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


    static class FeatureDTO {
        private List<ChartItemDTO<DetailColumnPO>> columnList;
        private List<ChartItemDTO<DetailColumnPO>> hiddenColumnList;
        private Integer defaultPageSize;

        public List<ChartItemDTO<DetailColumnPO>> getHiddenColumnList() {
            return hiddenColumnList;
        }

        public void setHiddenColumnList(List<ChartItemDTO<DetailColumnPO>> hiddenColumnList) {
            this.hiddenColumnList = hiddenColumnList;
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

}
