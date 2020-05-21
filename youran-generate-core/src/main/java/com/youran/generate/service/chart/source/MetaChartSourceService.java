package com.youran.generate.service.chart.source;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.optimistic.OptimisticLock;
import com.youran.generate.constant.SourceItemType;
import com.youran.generate.dao.chart.MetaChartSourceDAO;
import com.youran.generate.pojo.dto.chart.source.MetaChartSourceAddDTO;
import com.youran.generate.pojo.dto.chart.source.MetaChartSourceUpdateDTO;
import com.youran.generate.pojo.dto.chart.source.MetaChartSourceWithItemsAddDTO;
import com.youran.generate.pojo.dto.chart.source.MetaChartSourceWithItemsUpdateDTO;
import com.youran.generate.pojo.dto.chart.source.item.*;
import com.youran.generate.pojo.mapper.chart.MetaChartSourceItemMapper;
import com.youran.generate.pojo.mapper.chart.MetaChartSourceMapper;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.po.chart.source.MetaChartSourcePO;
import com.youran.generate.pojo.po.chart.source.item.*;
import com.youran.generate.pojo.vo.chart.source.MetaChartSourceShowVO;
import com.youran.generate.pojo.vo.chart.source.MetaChartSourceWithItemsShowVO;
import com.youran.generate.pojo.vo.chart.source.item.*;
import com.youran.generate.service.MetaProjectService;
import com.youran.generate.service.chart.source.item.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 【图表数据源】增删改查服务
 *
 * @author cbb
 * @date 2020/04/04
 */
@Service
public class MetaChartSourceService {

    @Autowired
    private MetaChartSourceDAO metaChartSourceDAO;
    @Autowired
    private MetaProjectService metaProjectService;
    @Autowired
    private MetaChartSourceItemService metaChartSourceItemService;
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

    private void checkProjectId(MetaProjectPO project, Integer projectId) {
        if (!Objects.equals(project.getProjectId(), projectId)) {
            throw new BusinessException(ErrorCode.RECORD_NOT_FIND, "禁止跨项目操作");
        }
    }

    /**
     * 新增【图表数据源】
     *
     * @param addDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public MetaChartSourcePO save(MetaChartSourceAddDTO addDTO) {
        Integer entityId = addDTO.getEntityId();
        // 获取项目，并校验操作人
        MetaProjectPO project = metaProjectService.getProjectByEntityId(entityId, true);
        this.checkProjectId(project, addDTO.getProjectId());
        MetaChartSourcePO metaChartSource = MetaChartSourceMapper.INSTANCE.fromAddDTO(addDTO);
        metaChartSource.featureSerialize();
        metaChartSourceDAO.save(metaChartSource);
        metaProjectService.updateProject(project);
        return metaChartSource;
    }

    /**
     * 新增【图表数据源】带数据项
     *
     * @param addDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public MetaChartSourcePO saveWithItems(MetaChartSourceWithItemsAddDTO addDTO) {
        Integer entityId = addDTO.getEntityId();
        // 获取项目，并校验操作人
        MetaProjectPO project = metaProjectService.getProjectByEntityId(entityId, true);
        this.checkProjectId(project, addDTO.getProjectId());
        MetaChartSourcePO metaChartSource = MetaChartSourceMapper.INSTANCE.fromAddDTO(addDTO);
        metaChartSource.featureSerialize();
        metaChartSourceDAO.save(metaChartSource);
        Integer sourceId = metaChartSource.getSourceId();
        Integer projectId = project.getProjectId();
        // 保存明细列
        List<DetailColumnAddDTO> detailColumnList = addDTO.getDetailColumnList();
        Map<String, DetailColumnPO> detailColumnMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(detailColumnList)) {
            for (DetailColumnAddDTO columnAddDTO : detailColumnList) {
                columnAddDTO.setSourceId(sourceId);
                columnAddDTO.setProjectId(projectId);
                DetailColumnPO columnPO = detailColumnService.doSave(columnAddDTO);
                detailColumnMap.put(columnAddDTO.getKey(), columnPO);
            }
        }
        // 保存过滤条件
        List<WhereAddDTO> whereList = addDTO.getWhereList();
        if (CollectionUtils.isNotEmpty(whereList)) {
            for (WhereAddDTO columnAddDTO : whereList) {
                columnAddDTO.setSourceId(sourceId);
                columnAddDTO.setProjectId(projectId);
                whereService.doSave(columnAddDTO);
            }
        }
        // 保存维度
        List<DimensionAddDTO> dimensionList = addDTO.getDimensionList();
        Map<String, DimensionPO> dimensionMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(dimensionList)) {
            for (DimensionAddDTO columnAddDTO : dimensionList) {
                columnAddDTO.setSourceId(sourceId);
                columnAddDTO.setProjectId(projectId);
                DimensionPO columnPO = dimensionService.doSave(columnAddDTO);
                dimensionMap.put(columnAddDTO.getKey(), columnPO);
            }
        }
        // 保存指标
        List<MetricsAddDTO> metricsList = addDTO.getMetricsList();
        Map<String, MetricsPO> metricsMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(metricsList)) {
            for (MetricsAddDTO columnAddDTO : metricsList) {
                columnAddDTO.setSourceId(sourceId);
                columnAddDTO.setProjectId(projectId);
                MetricsPO columnPO = metricsService.doSave(columnAddDTO);
                metricsMap.put(columnAddDTO.getKey(), columnPO);
            }
        }
        // 保存明细排序
        List<DetailOrderAddDTO> detailOrderList = addDTO.getDetailOrderList();
        if (CollectionUtils.isNotEmpty(detailOrderList)) {
            for (DetailOrderAddDTO columnAddDTO : detailOrderList) {
                columnAddDTO.setSourceId(sourceId);
                columnAddDTO.setProjectId(projectId);
                DetailColumnPO parent = detailColumnMap.get(columnAddDTO.getParentKey());
                columnAddDTO.setParentId(parent.getSourceItemId());
                detailOrderService.doSave(columnAddDTO);
            }
        }
        // 保存having条件
        List<HavingAddDTO> havingList = addDTO.getHavingList();
        if (CollectionUtils.isNotEmpty(havingList)) {
            for (HavingAddDTO columnAddDTO : havingList) {
                columnAddDTO.setSourceId(sourceId);
                columnAddDTO.setProjectId(projectId);
                MetricsPO parent = metricsMap.get(columnAddDTO.getParentKey());
                columnAddDTO.setParentId(parent.getSourceItemId());
                havingService.doSave(columnAddDTO);
            }
        }
        // 保存聚合排序
        List<AggOrderAddDTO> aggOrderList = addDTO.getAggOrderList();
        if (CollectionUtils.isNotEmpty(aggOrderList)) {
            for (AggOrderAddDTO columnAddDTO : aggOrderList) {
                columnAddDTO.setSourceId(sourceId);
                columnAddDTO.setProjectId(projectId);
                MetaChartSourceItemPO parent = dimensionMap.get(columnAddDTO.getParentKey());
                if (parent == null) {
                    parent = metricsMap.get(columnAddDTO.getParentKey());
                }
                columnAddDTO.setParentId(parent.getSourceItemId());
                aggOrderService.doSave(columnAddDTO);
            }
        }
        metaProjectService.updateProject(project);
        return metaChartSource;
    }


    /**
     * 修改【图表数据源】
     *
     * @param updateDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @OptimisticLock
    public MetaChartSourcePO update(MetaChartSourceUpdateDTO updateDTO) {
        Integer sourceId = updateDTO.getSourceId();
        Integer entityId = updateDTO.getEntityId();
        // 获取项目，并校验操作人
        MetaProjectPO project = metaProjectService.getProjectByEntityId(entityId, true);
        this.checkProjectId(project, updateDTO.getProjectId());
        MetaChartSourcePO metaChartSource = this.getMetaChartSource(sourceId, true);
        MetaChartSourceMapper.INSTANCE.setUpdateDTO(metaChartSource, updateDTO);
        metaChartSource.featureSerialize();
        metaChartSourceDAO.update(metaChartSource);
        metaProjectService.updateProject(project);
        return metaChartSource;
    }

    /**
     * 修改【图表数据源】带数据项
     *
     * @param updateDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @OptimisticLock
    public MetaChartSourcePO updateWithItems(MetaChartSourceWithItemsUpdateDTO updateDTO) {
        Integer sourceId = updateDTO.getSourceId();
        Integer entityId = updateDTO.getEntityId();
        // 获取项目，并校验操作人
        MetaProjectPO project = metaProjectService.getProjectByEntityId(entityId, true);
        this.checkProjectId(project, updateDTO.getProjectId());
        MetaChartSourcePO metaChartSource = this.getMetaChartSource(sourceId, true);
        MetaChartSourceMapper.INSTANCE.setUpdateDTO(metaChartSource, updateDTO);
        metaChartSource.featureSerialize();
        metaChartSourceDAO.update(metaChartSource);
        List<Integer> oldItemIds = updateDTO.fetchSourceItemIds();
        // 先删除其他数据项id
        metaChartSourceItemService.deleteOtherItems(sourceId, oldItemIds);
        Integer projectId = project.getProjectId();
        // 保存明细列
        List<DetailColumnUpdateDTO> detailColumnList = updateDTO.getDetailColumnList();
        Map<String, DetailColumnPO> detailColumnMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(detailColumnList)) {
            for (DetailColumnUpdateDTO columnUpdateDTO : detailColumnList) {
                columnUpdateDTO.setSourceId(sourceId);
                columnUpdateDTO.setProjectId(projectId);
                DetailColumnPO columnPO;
                if (columnUpdateDTO.getSourceItemId() != null) {
                    columnPO = detailColumnService.doUpdate(columnUpdateDTO);
                } else {
                    columnPO = detailColumnService.doSave(columnUpdateDTO);
                }
                detailColumnMap.put(columnUpdateDTO.getKey(), columnPO);
            }
        }
        // 保存过滤条件
        List<WhereUpdateDTO> whereList = updateDTO.getWhereList();
        if (CollectionUtils.isNotEmpty(whereList)) {
            for (WhereUpdateDTO columnUpdateDTO : whereList) {
                columnUpdateDTO.setSourceId(sourceId);
                columnUpdateDTO.setProjectId(projectId);
                if (columnUpdateDTO.getSourceItemId() != null) {
                    whereService.doUpdate(columnUpdateDTO);
                } else {
                    whereService.doSave(columnUpdateDTO);
                }
            }
        }
        // 保存维度
        List<DimensionUpdateDTO> dimensionList = updateDTO.getDimensionList();
        Map<String, DimensionPO> dimensionMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(dimensionList)) {
            for (DimensionUpdateDTO columnUpdateDTO : dimensionList) {
                columnUpdateDTO.setSourceId(sourceId);
                columnUpdateDTO.setProjectId(projectId);
                DimensionPO columnPO;
                if (columnUpdateDTO.getSourceItemId() != null) {
                    columnPO = dimensionService.doUpdate(columnUpdateDTO);
                } else {
                    columnPO = dimensionService.doSave(columnUpdateDTO);
                }
                dimensionMap.put(columnUpdateDTO.getKey(), columnPO);
            }
        }
        // 保存指标
        List<MetricsUpdateDTO> metricsList = updateDTO.getMetricsList();
        Map<String, MetricsPO> metricsMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(metricsList)) {
            for (MetricsUpdateDTO columnUpdateDTO : metricsList) {
                columnUpdateDTO.setSourceId(sourceId);
                columnUpdateDTO.setProjectId(projectId);
                MetricsPO columnPO;
                if (columnUpdateDTO.getSourceItemId() != null) {
                    columnPO = metricsService.doUpdate(columnUpdateDTO);
                } else {
                    columnPO = metricsService.doSave(columnUpdateDTO);
                }
                metricsMap.put(columnUpdateDTO.getKey(), columnPO);
            }
        }
        // 保存明细排序
        List<DetailOrderUpdateDTO> detailOrderList = updateDTO.getDetailOrderList();
        if (CollectionUtils.isNotEmpty(detailOrderList)) {
            for (DetailOrderUpdateDTO columnUpdateDTO : detailOrderList) {
                columnUpdateDTO.setSourceId(sourceId);
                columnUpdateDTO.setProjectId(projectId);
                DetailColumnPO parent = detailColumnMap.get(columnUpdateDTO.getParentKey());
                columnUpdateDTO.setParentId(parent.getSourceItemId());
                if (columnUpdateDTO.getSourceItemId() != null) {
                    detailOrderService.doUpdate(columnUpdateDTO);
                } else {
                    detailOrderService.doSave(columnUpdateDTO);
                }
            }
        }
        // 保存having条件
        List<HavingUpdateDTO> havingList = updateDTO.getHavingList();
        if (CollectionUtils.isNotEmpty(havingList)) {
            for (HavingUpdateDTO columnUpdateDTO : havingList) {
                columnUpdateDTO.setSourceId(sourceId);
                columnUpdateDTO.setProjectId(projectId);
                MetricsPO parent = metricsMap.get(columnUpdateDTO.getParentKey());
                columnUpdateDTO.setParentId(parent.getSourceItemId());
                if (columnUpdateDTO.getSourceItemId() != null) {
                    havingService.doUpdate(columnUpdateDTO);
                } else {
                    havingService.doSave(columnUpdateDTO);
                }
            }
        }
        // 保存聚合排序
        List<AggOrderUpdateDTO> aggOrderList = updateDTO.getAggOrderList();
        if (CollectionUtils.isNotEmpty(aggOrderList)) {
            for (AggOrderUpdateDTO columnUpdateDTO : aggOrderList) {
                columnUpdateDTO.setSourceId(sourceId);
                columnUpdateDTO.setProjectId(projectId);
                MetaChartSourceItemPO parent = dimensionMap.get(columnUpdateDTO.getParentKey());
                if (parent == null) {
                    parent = metricsMap.get(columnUpdateDTO.getParentKey());
                }
                columnUpdateDTO.setParentId(parent.getSourceItemId());
                if (columnUpdateDTO.getSourceItemId() != null) {
                    aggOrderService.doUpdate(columnUpdateDTO);
                } else {
                    aggOrderService.doSave(columnUpdateDTO);
                }
            }
        }
        metaProjectService.updateProject(project);
        return metaChartSource;
    }

    /**
     * 根据主键获取【图表数据源】
     *
     * @param sourceId 主键
     * @param force    是否强制获取
     * @return
     */
    public MetaChartSourcePO getMetaChartSource(Integer sourceId,
                                                boolean force) {
        MetaChartSourcePO metaChartSource = metaChartSourceDAO.findById(sourceId);
        if (force && metaChartSource == null) {
            throw new BusinessException(ErrorCode.RECORD_NOT_FIND);
        }
        metaChartSource.featureDeserialize();
        return metaChartSource;
    }


    /**
     * 查询【图表数据源】详情
     *
     * @param sourceId
     * @return
     */
    public MetaChartSourceShowVO show(Integer sourceId) {
        MetaChartSourcePO metaChartSource = this.getMetaChartSource(sourceId, true);
        MetaChartSourceShowVO showVO = MetaChartSourceMapper.INSTANCE.toShowVO(metaChartSource);
        return showVO;
    }

    /**
     * 查询【图表数据源】带数据项详情
     *
     * @param sourceId
     * @return
     */
    public MetaChartSourceWithItemsShowVO showWithItems(Integer sourceId) {
        MetaChartSourcePO metaChartSource = this.getMetaChartSource(sourceId, true);
        MetaChartSourceWithItemsShowVO showVO = MetaChartSourceMapper.INSTANCE.toWithItemsShowVO(metaChartSource);
        List<MetaChartSourceItemPO> items = metaChartSourceItemService.findBySourceId(sourceId, true);
        // 对图表数据项按类型分组
        Map<Integer, ? extends List<? extends MetaChartSourceItemPO>> map = items.stream()
            .collect(Collectors.groupingBy(e -> e.getType()));
        List<DetailColumnPO> detailColumnList = (List<DetailColumnPO>) map.get(SourceItemType.DETAIL_COLUMN.getValue());
        if (CollectionUtils.isNotEmpty(detailColumnList)) {
            showVO.setDetailColumnList(detailColumnList.stream()
                .<DetailColumnVO>map(MetaChartSourceItemMapper.INSTANCE::poToVO)
                .collect(Collectors.toList()));
        }
        List<WherePO> whereList = (List<WherePO>) map.get(SourceItemType.WHERE.getValue());
        if (CollectionUtils.isNotEmpty(whereList)) {
            showVO.setWhereList(whereList.stream()
                .<WhereVO>map(MetaChartSourceItemMapper.INSTANCE::poToVO)
                .collect(Collectors.toList()));
        }
        List<DetailOrderPO> detailOrderList = (List<DetailOrderPO>) map.get(SourceItemType.DETAIL_ORDER.getValue());
        if (CollectionUtils.isNotEmpty(detailOrderList)) {
            showVO.setDetailOrderList(detailOrderList.stream()
                .<DetailOrderVO>map(MetaChartSourceItemMapper.INSTANCE::poToVO)
                .collect(Collectors.toList()));
        }
        List<DimensionPO> dimensionList = (List<DimensionPO>) map.get(SourceItemType.DIMENSION.getValue());
        if (CollectionUtils.isNotEmpty(dimensionList)) {
            showVO.setDimensionList(dimensionList.stream()
                .<DimensionVO>map(MetaChartSourceItemMapper.INSTANCE::poToVO)
                .collect(Collectors.toList()));
        }
        List<MetricsPO> metricsList = (List<MetricsPO>) map.get(SourceItemType.METRICS.getValue());
        if (CollectionUtils.isNotEmpty(metricsList)) {
            showVO.setMetricsList(metricsList.stream()
                .<MetricsVO>map(MetaChartSourceItemMapper.INSTANCE::poToVO)
                .collect(Collectors.toList()));
        }
        List<HavingPO> havingList = (List<HavingPO>) map.get(SourceItemType.HAVING.getValue());
        if (CollectionUtils.isNotEmpty(havingList)) {
            showVO.setHavingList(havingList.stream()
                .<HavingVO>map(MetaChartSourceItemMapper.INSTANCE::poToVO)
                .collect(Collectors.toList()));
        }
        List<AggOrderPO> aggOrderList = (List<AggOrderPO>) map.get(SourceItemType.AGG_ORDER.getValue());
        if (CollectionUtils.isNotEmpty(aggOrderList)) {
            showVO.setAggOrderList(aggOrderList.stream()
                .<AggOrderVO>map(MetaChartSourceItemMapper.INSTANCE::poToVO)
                .collect(Collectors.toList()));
        }
        return showVO;
    }

    /**
     * 删除【图表数据源】
     *
     * @param sourceIds
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public int delete(Integer... sourceIds) {
        int count = 0;
        for (Integer sourceId : sourceIds) {
            MetaChartSourcePO po = this.getMetaChartSource(sourceId, true);
            if (po == null) {
                continue;
            }
            Integer projectId = po.getProjectId();
            //校验操作人
            MetaProjectPO project = metaProjectService.getAndCheckProject(projectId);
            count += metaChartSourceDAO.delete(sourceId);
            metaProjectService.updateProject(project);
        }
        return count;
    }


    /**
     * 根据项目id,查询所有图表数据源
     *
     * @param projectId
     * @return
     */
    public List<MetaChartSourcePO> findByProjectId(Integer projectId) {
        List<MetaChartSourcePO> list = metaChartSourceDAO.findByProjectId(projectId);
        list.forEach(po -> po.featureDeserialize());
        return list;
    }


}


