package com.youran.generate.service.chart.source.item;

import com.youran.common.optimistic.OptimisticLock;
import com.youran.generate.dao.chart.MetaChartSourceItemDAO;
import com.youran.generate.pojo.dto.chart.source.item.MetricsAddDTO;
import com.youran.generate.pojo.dto.chart.source.item.MetricsUpdateDTO;
import com.youran.generate.pojo.mapper.chart.MetaChartSourceItemMapper;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.po.chart.source.item.MetricsPO;
import com.youran.generate.service.MetaProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 【指标】服务类
 *
 * @author: cbb
 * @date: 2020-05-10
 */
@Service
public class MetricsService {

    @Autowired
    private MetaChartSourceItemDAO metaChartSourceItemDAO;
    @Autowired
    private MetaChartSourceItemService metaChartSourceItemService;
    @Autowired
    private MetaProjectService metaProjectService;
    /**
     * 【指标】数据预处理
     *
     * @param po
     */
    public void preparePO(MetricsPO po) {
        po.featureSerialize();
        metaChartSourceItemService.preparePO(po);
    }

    /**
     * 新增【指标】
     *
     * @param addDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public MetricsPO save(MetricsAddDTO addDTO) {
        Integer projectId = addDTO.getProjectId();
        MetaProjectPO project = metaProjectService.getAndCheckProject(projectId);
        MetricsPO po = MetaChartSourceItemMapper.INSTANCE
            .fromMetricsAddDTO(addDTO);
        this.preparePO(po);
        metaChartSourceItemDAO.save(po);
        metaProjectService.updateProject(project);
        return po;
    }


    /**
     * 修改【图表数据源项】
     *
     * @param updateDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @OptimisticLock
    public MetricsPO update(MetricsUpdateDTO updateDTO) {
        Integer sourceItemId = updateDTO.getSourceItemId();
        MetricsPO po = metaChartSourceItemService.getMetaChartSourceItem(sourceItemId, true);
        Integer projectId = po.getProjectId();
        MetaProjectPO project = metaProjectService.getAndCheckProject(projectId);
        MetaChartSourceItemMapper.INSTANCE.setMetricsUpdateDTO(po, updateDTO);
        this.preparePO(po);
        metaChartSourceItemDAO.update(po);
        metaProjectService.updateProject(project);
        return po;
    }


}
