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
import com.youran.generate.pojo.mapper.chart.MetaChartSourceItemMapper;
import com.youran.generate.pojo.mapper.chart.MetaChartSourceMapper;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.po.chart.source.MetaChartSourcePO;
import com.youran.generate.pojo.po.chart.source.item.*;
import com.youran.generate.pojo.vo.chart.source.MetaChartSourceShowVO;
import com.youran.generate.pojo.vo.chart.source.MetaChartSourceWithItemsShowVO;
import com.youran.generate.pojo.vo.chart.source.item.*;
import com.youran.generate.service.MetaProjectService;
import com.youran.generate.service.chart.source.item.MetaChartSourceItemService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        // TODO
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

        // TODO
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
        if(CollectionUtils.isNotEmpty(detailColumnList)) {
            showVO.setDetailColumnList(detailColumnList.stream()
                .<DetailColumnVO>map(MetaChartSourceItemMapper.INSTANCE::poToVO)
                .collect(Collectors.toList()));
        }
        List<WherePO> whereList = (List<WherePO>) map.get(SourceItemType.WHERE.getValue());
        if(CollectionUtils.isNotEmpty(whereList)) {
            showVO.setWhereList(whereList.stream()
                .<WhereVO>map(MetaChartSourceItemMapper.INSTANCE::poToVO)
                .collect(Collectors.toList()));
        }
        List<DetailOrderPO> detailOrderList = (List<DetailOrderPO>) map.get(SourceItemType.DETAIL_ORDER.getValue());
        if(CollectionUtils.isNotEmpty(detailOrderList)) {
            showVO.setDetailOrderList(detailOrderList.stream()
                .<DetailOrderVO>map(MetaChartSourceItemMapper.INSTANCE::poToVO)
                .collect(Collectors.toList()));
        }
        List<DimensionPO> dimensionList = (List<DimensionPO>) map.get(SourceItemType.DIMENSION.getValue());
        if(CollectionUtils.isNotEmpty(dimensionList)) {
            showVO.setDimensionList(dimensionList.stream()
                .<DimensionVO>map(MetaChartSourceItemMapper.INSTANCE::poToVO)
                .collect(Collectors.toList()));
        }
        List<MetricsPO> metricsList = (List<MetricsPO>) map.get(SourceItemType.METRICS.getValue());
        if(CollectionUtils.isNotEmpty(metricsList)) {
            showVO.setMetricsList(metricsList.stream()
                .<MetricsVO>map(MetaChartSourceItemMapper.INSTANCE::poToVO)
                .collect(Collectors.toList()));
        }
        List<HavingPO> havingList = (List<HavingPO>) map.get(SourceItemType.HAVING.getValue());
        if(CollectionUtils.isNotEmpty(havingList)) {
            showVO.setHavingList(havingList.stream()
                .<HavingVO>map(MetaChartSourceItemMapper.INSTANCE::poToVO)
                .collect(Collectors.toList()));
        }
        List<AggOrderPO> aggOrderList = (List<AggOrderPO>) map.get(SourceItemType.AGG_ORDER.getValue());
        if(CollectionUtils.isNotEmpty(aggOrderList)) {
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


