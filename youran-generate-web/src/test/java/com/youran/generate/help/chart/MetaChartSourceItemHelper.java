package com.youran.generate.help.chart;

import com.youran.generate.constant.*;
import com.youran.generate.pojo.dto.chart.source.item.*;
import com.youran.generate.pojo.po.chart.source.item.*;
import com.youran.generate.service.chart.source.item.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class MetaChartSourceItemHelper {

    @Autowired
    private AggOrderService aggOrderService;
    @Autowired
    private DetailColumnService detailColumnService;
    @Autowired
    private DetailOrderService detailOrderService;
    @Autowired
    private DimensionService dimensionService;
    @Autowired
    private HavingService havingService;
    @Autowired
    private MetricsService metricsService;
    @Autowired
    private WhereService whereService;

    @Autowired
    private MetaChartSourceItemService metaChartSourceItemService;

    /**
     * 生成add测试数据
     */
    public AggOrderAddDTO getAggOrderAddDTO(Integer projectId,
                                            Integer sourceId,
                                            Integer parentId) {
        AggOrderAddDTO dto = new AggOrderAddDTO();
        dto.setProjectId(projectId);
        dto.setSourceId(sourceId);
        dto.setJoinIndex(0);
        dto.setParentId(parentId);
        dto.setSortType(SortType.ASC.getValue());
        return dto;
    }

    public DetailColumnAddDTO getDetailColumnAddDTO(Integer projectId,
                                                    Integer sourceId,
                                                    Integer fieldId) {
        DetailColumnAddDTO dto = new DetailColumnAddDTO();
        dto.setProjectId(projectId);
        dto.setSourceId(sourceId);
        dto.setJoinIndex(0);
        dto.setFieldId(fieldId);
        dto.setCustom(false);
        dto.setCustomContent(null);
        dto.setCustomFieldType(null);
        return dto;
    }

    public DetailOrderAddDTO getDetailOrderAddDTO(Integer projectId,
                                                  Integer sourceId,
                                                  Integer parentId) {
        DetailOrderAddDTO dto = new DetailOrderAddDTO();
        dto.setProjectId(projectId);
        dto.setSourceId(sourceId);
        dto.setJoinIndex(0);
        dto.setParentId(parentId);
        dto.setSortType(SortType.ASC.getValue());
        return dto;
    }

    public DimensionAddDTO getDimensionAddDTO(Integer projectId,
                                              Integer sourceId,
                                              Integer fieldId) {
        DimensionAddDTO dto = new DimensionAddDTO();
        dto.setProjectId(projectId);
        dto.setSourceId(sourceId);
        dto.setJoinIndex(0);
        dto.setFieldId(fieldId);
        dto.setGranularity(Granularity.SINGLE_VALUE.getValue());
        return dto;
    }

    public HavingAddDTO getHavingAddDTO(Integer projectId,
                                        Integer sourceId,
                                        Integer parentId) {
        HavingAddDTO dto = new HavingAddDTO();
        dto.setProjectId(projectId);
        dto.setSourceId(sourceId);
        dto.setJoinIndex(0);
        dto.setParentId(parentId);
        dto.setFilterOperator(FilterOperator.EQUAL.getValue());
        dto.setFilterValue(new String[]{"1"});
        return dto;
    }

    public MetricsAddDTO getMetricsAddDTO(Integer projectId,
                                          Integer sourceId,
                                          Integer fieldId) {
        MetricsAddDTO dto = new MetricsAddDTO();
        dto.setProjectId(projectId);
        dto.setSourceId(sourceId);
        dto.setJoinIndex(0);
        dto.setFieldId(fieldId);
        dto.setAggFunction(AggFunction.COUNT.getValue());
        dto.setCustom(false);
        dto.setCustomContent(null);
        dto.setCustomFieldType(null);
        return dto;
    }

    public WhereAddDTO getWhereAddDTO(Integer projectId,
                                      Integer sourceId,
                                      Integer fieldId) {
        WhereAddDTO dto = new WhereAddDTO();
        dto.setProjectId(projectId);
        dto.setSourceId(sourceId);
        dto.setJoinIndex(0);
        dto.setFieldId(fieldId);
        dto.setFilterOperator(FilterOperator.EQUAL.getValue());
        dto.setFilterValue(new String[]{"1"});
        dto.setTimeGranularity(TimeGranularity.MONTH.getValue());
        dto.setCustom(false);
        dto.setCustomContent(null);
        return dto;
    }

    /**
     * 生成update测试数据
     */
    public AggOrderUpdateDTO getAggOrderUpdateDTO(AggOrderPO po) {
        AggOrderUpdateDTO dto = new AggOrderUpdateDTO();
        dto.setSourceItemId(po.getSourceItemId());
        dto.setProjectId(po.getProjectId());
        dto.setSourceId(po.getSourceId());
        dto.setJoinIndex(po.getJoinIndex());
        dto.setParentId(po.getParentId());
        dto.setSortType(SortType.DESC.getValue());
        return dto;
    }

    public DetailColumnUpdateDTO getDetailColumnUpdateDTO(DetailColumnPO po) {
        DetailColumnUpdateDTO dto = new DetailColumnUpdateDTO();
        dto.setSourceItemId(po.getSourceItemId());
        dto.setProjectId(po.getProjectId());
        dto.setSourceId(po.getSourceId());
        dto.setJoinIndex(po.getJoinIndex());
        dto.setParentId(po.getParentId());
        dto.setFieldId(po.getFieldId());
        dto.setCustom(po.getCustom());
        dto.setCustomContent(po.getCustomContent());
        dto.setCustomFieldType(po.getCustomFieldType());
        return dto;
    }

    public DetailOrderUpdateDTO getDetailOrderUpdateDTO(DetailOrderPO po) {
        DetailOrderUpdateDTO dto = new DetailOrderUpdateDTO();
        dto.setSourceItemId(po.getSourceItemId());
        dto.setProjectId(po.getProjectId());
        dto.setSourceId(po.getSourceId());
        dto.setJoinIndex(po.getJoinIndex());
        dto.setParentId(po.getParentId());
        dto.setSortType(SortType.DESC.getValue());
        return dto;
    }

    public DimensionUpdateDTO getDimensionUpdateDTO(DimensionPO po) {
        DimensionUpdateDTO dto = new DimensionUpdateDTO();
        dto.setSourceItemId(po.getSourceItemId());
        dto.setProjectId(po.getProjectId());
        dto.setSourceId(po.getSourceId());
        dto.setJoinIndex(po.getJoinIndex());
        dto.setParentId(po.getParentId());
        dto.setFieldId(po.getFieldId());
        dto.setGranularity(po.getGranularity());
        return dto;
    }

    public HavingUpdateDTO getHavingUpdateDTO(HavingPO po) {
        HavingUpdateDTO dto = new HavingUpdateDTO();
        dto.setSourceItemId(po.getSourceItemId());
        dto.setProjectId(po.getProjectId());
        dto.setSourceId(po.getSourceId());
        dto.setJoinIndex(po.getJoinIndex());
        dto.setParentId(po.getParentId());
        dto.setFilterOperator(po.getFilterOperator());
        dto.setFilterValue(po.getFilterValue());
        return dto;
    }

    public MetricsUpdateDTO getMetricsUpdateDTO(MetricsPO po) {
        MetricsUpdateDTO dto = new MetricsUpdateDTO();
        dto.setSourceItemId(po.getSourceItemId());
        dto.setProjectId(po.getProjectId());
        dto.setSourceId(po.getSourceId());
        dto.setJoinIndex(po.getJoinIndex());
        dto.setParentId(po.getParentId());
        dto.setFieldId(po.getFieldId());
        dto.setAggFunction(po.getAggFunction());
        dto.setCustom(po.getCustom());
        dto.setCustomContent(po.getCustomContent());
        dto.setCustomFieldType(po.getCustomFieldType());
        return dto;
    }

    public WhereUpdateDTO getWhereUpdateDTO(WherePO po) {
        WhereUpdateDTO dto = new WhereUpdateDTO();
        dto.setSourceItemId(po.getSourceItemId());
        dto.setProjectId(po.getProjectId());
        dto.setSourceId(po.getSourceId());
        dto.setJoinIndex(po.getJoinIndex());
        dto.setParentId(po.getParentId());
        dto.setFieldId(po.getFieldId());
        dto.setFilterOperator(po.getFilterOperator());
        dto.setFilterValue(po.getFilterValue());
        dto.setTimeGranularity(po.getTimeGranularity());
        dto.setCustom(po.getCustom());
        dto.setCustomContent(po.getCustomContent());
        return dto;
    }

    /**
     * 保存示例
     *
     * @return
     */
    public AggOrderPO saveAggOrderExample(Integer projectId,
                                          Integer sourceId,
                                          Integer parentId) {
        AggOrderAddDTO addDTO = this.getAggOrderAddDTO(projectId, sourceId, parentId);
        return aggOrderService.save(addDTO);
    }

    public DetailColumnPO saveDetailColumnExample(Integer projectId,
                                                  Integer sourceId,
                                                  Integer fieldId) {
        DetailColumnAddDTO addDTO = this.getDetailColumnAddDTO(projectId, sourceId, fieldId);
        return detailColumnService.save(addDTO);
    }

    public DetailOrderPO saveDetailOrderExample(Integer projectId,
                                                Integer sourceId,
                                                Integer parentId) {
        DetailOrderAddDTO addDTO = this.getDetailOrderAddDTO(projectId, sourceId, parentId);
        return detailOrderService.save(addDTO);
    }

    public DimensionPO saveDimensionExample(Integer projectId,
                                            Integer sourceId,
                                            Integer fieldId) {
        DimensionAddDTO addDTO = this.getDimensionAddDTO(projectId, sourceId, fieldId);
        return dimensionService.save(addDTO);
    }

    public HavingPO saveHavingExample(Integer projectId,
                                      Integer sourceId,
                                      Integer parentId) {
        HavingAddDTO addDTO = this.getHavingAddDTO(projectId, sourceId, parentId);
        return havingService.save(addDTO);
    }

    public MetricsPO saveMetricsExample(Integer projectId,
                                        Integer sourceId,
                                        Integer fieldId) {
        MetricsAddDTO addDTO = this.getMetricsAddDTO(projectId, sourceId, fieldId);
        return metricsService.save(addDTO);
    }

    public WherePO saveWhereExample(Integer projectId,
                                    Integer sourceId,
                                    Integer fieldId) {
        WhereAddDTO addDTO = this.getWhereAddDTO(projectId, sourceId, fieldId);
        return whereService.save(addDTO);
    }


}

