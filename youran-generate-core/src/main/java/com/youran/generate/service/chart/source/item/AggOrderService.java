package com.youran.generate.service.chart.source.item;

import com.youran.common.optimistic.OptimisticLock;
import com.youran.generate.dao.chart.MetaChartSourceItemDAO;
import com.youran.generate.pojo.dto.chart.source.item.AggOrderAddDTO;
import com.youran.generate.pojo.dto.chart.source.item.AggOrderUpdateDTO;
import com.youran.generate.pojo.mapper.chart.MetaChartSourceItemMapper;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.po.chart.source.item.AggOrderPO;
import com.youran.generate.service.MetaProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 【聚合排序】服务类
 *
 * @author: cbb
 * @date: 2020-05-05
 */
@Service
public class AggOrderService {

    @Autowired
    private MetaChartSourceItemDAO metaChartSourceItemDAO;
    @Autowired
    private MetaChartSourceItemService metaChartSourceItemService;
    @Autowired
    private MetaProjectService metaProjectService;
    /**
     * 【聚合排序】数据预处理
     *
     * @param po
     */
    public void preparePO(AggOrderPO po) {
        po.featureSerialize();
        metaChartSourceItemService.preparePO(po);
    }

    /**
     * 新增【聚合排序】
     *
     * @param addDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public AggOrderPO save(AggOrderAddDTO addDTO) {
        Integer projectId = addDTO.getProjectId();
        MetaProjectPO project = metaProjectService.getAndCheckProject(projectId);
        AggOrderPO po = MetaChartSourceItemMapper.INSTANCE
            .fromAggOrderAddDTO(addDTO);
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
    public AggOrderPO update(AggOrderUpdateDTO updateDTO) {
        Integer sourceItemId = updateDTO.getSourceItemId();
        AggOrderPO po = metaChartSourceItemService.getMetaChartSourceItem(sourceItemId, true);
        Integer projectId = po.getProjectId();
        MetaProjectPO project = metaProjectService.getAndCheckProject(projectId);
        MetaChartSourceItemMapper.INSTANCE.setAggOrderUpdateDTO(po, updateDTO);
        this.preparePO(po);
        metaChartSourceItemDAO.update(po);
        metaProjectService.updateProject(project);
        return po;
    }


}
