package com.youran.generate.service.chart;

import com.google.common.collect.Lists;
import com.youran.common.optimistic.OptimisticLock;
import com.youran.generate.dao.chart.MetaChartDAO;
import com.youran.generate.pojo.dto.chart.AggTableAddDTO;
import com.youran.generate.pojo.dto.chart.AggTableUpdateDTO;
import com.youran.generate.pojo.dto.chart.ChartItemDTO;
import com.youran.generate.pojo.mapper.chart.MetaChartMapper;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.po.chart.AggTablePO;
import com.youran.generate.pojo.po.chart.source.item.DimensionPO;
import com.youran.generate.pojo.po.chart.source.item.MetricsPO;
import com.youran.generate.pojo.vo.chart.AggTableVO;
import com.youran.generate.service.MetaProjectService;
import com.youran.generate.util.ChartValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 聚合表服务类
 *
 * @author: cbb
 * @date: 2020-05-05
 */
@Service
public class AggTableService {

    @Autowired
    private MetaChartDAO metaChartDAO;
    @Autowired
    private MetaChartService metaChartService;
    @Autowired
    private MetaProjectService metaProjectService;

    /**
     * 校验【聚合表】数据合法性
     *
     * @param po
     */
    public void check(AggTablePO po) {
        List<ChartItemDTO<DimensionPO>> dimensionList = po.getDimensionList();
        List<ChartItemDTO<MetricsPO>> metricsList = po.getMetricsList();
        List<ChartItemDTO> list = Lists.newArrayList(dimensionList);
        list.addAll(metricsList);
        ChartValidateUtil.checkItemAliasConflict(list);
    }

    /**
     * 新增【聚合表】
     *
     * @param addDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public AggTablePO save(AggTableAddDTO addDTO) {
        Integer projectId = addDTO.getProjectId();
        MetaProjectPO project = metaProjectService.getAndCheckProject(projectId);
        AggTablePO po = MetaChartMapper.INSTANCE.fromAggTableAddDTO(addDTO);
        po.featureSerialize();
        this.check(po);
        metaChartDAO.save(po);
        metaProjectService.updateProject(project);
        return po;
    }


    /**
     * 修改【图表】
     *
     * @param updateDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @OptimisticLock
    public AggTablePO update(AggTableUpdateDTO updateDTO) {
        Integer chartId = updateDTO.getChartId();
        AggTablePO po = metaChartService.getMetaChart(chartId, true);
        Integer projectId = po.getProjectId();
        MetaProjectPO project = metaProjectService.getAndCheckProject(projectId);
        MetaChartMapper.INSTANCE.setAggTableUpdateDTO(po, updateDTO);
        po.featureSerialize();
        this.check(po);
        metaChartDAO.update(po);
        metaProjectService.updateProject(project);
        return po;
    }


    /**
     * 查询【图表】详情
     *
     * @param chartId
     * @return
     */
    public AggTableVO show(Integer chartId) {
        AggTablePO po = metaChartService.getMetaChart(chartId, true);
        AggTableVO vo = MetaChartMapper.INSTANCE.toAggTableVO(po);
        return vo;
    }

}
