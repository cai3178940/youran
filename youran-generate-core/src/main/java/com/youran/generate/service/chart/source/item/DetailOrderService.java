package com.youran.generate.service.chart.source.item;

import com.youran.common.optimistic.OptimisticLock;
import com.youran.generate.dao.chart.MetaChartSourceItemDAO;
import com.youran.generate.pojo.dto.chart.source.item.DetailOrderAddDTO;
import com.youran.generate.pojo.dto.chart.source.item.DetailOrderUpdateDTO;
import com.youran.generate.pojo.mapper.chart.MetaChartSourceItemMapper;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.po.chart.source.item.DetailOrderPO;
import com.youran.generate.service.MetaProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 【明细排序】服务类
 *
 * @author: cbb
 * @date: 2020-05-10
 */
@Service
public class DetailOrderService {

    @Autowired
    private MetaChartSourceItemDAO metaChartSourceItemDAO;
    @Autowired
    private MetaChartSourceItemService metaChartSourceItemService;
    @Autowired
    private MetaProjectService metaProjectService;
    /**
     * 【明细排序】数据预处理
     *
     * @param po
     */
    public void preparePO(DetailOrderPO po) {
        po.featureSerialize();
        metaChartSourceItemService.preparePO(po);
    }

    /**
     * 新增【明细排序】
     *
     * @param addDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public DetailOrderPO save(DetailOrderAddDTO addDTO) {
        Integer projectId = addDTO.getProjectId();
        MetaProjectPO project = metaProjectService.getAndCheckProject(projectId);
        DetailOrderPO po = this.doSave(addDTO);
        metaProjectService.updateProject(project);
        return po;
    }

    public DetailOrderPO doSave(DetailOrderAddDTO addDTO) {
        DetailOrderPO po = MetaChartSourceItemMapper.INSTANCE
            .fromDetailOrderAddDTO(addDTO);
        this.preparePO(po);
        metaChartSourceItemDAO.save(po);
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
    public DetailOrderPO update(DetailOrderUpdateDTO updateDTO) {
        Integer projectId = updateDTO.getProjectId();
        MetaProjectPO project = metaProjectService.getAndCheckProject(projectId);
        DetailOrderPO po = this.doUpdate(updateDTO);
        metaProjectService.updateProject(project);
        return po;
    }

    public DetailOrderPO doUpdate(DetailOrderUpdateDTO updateDTO) {
        Integer sourceItemId = updateDTO.getSourceItemId();
        DetailOrderPO po = metaChartSourceItemService.getMetaChartSourceItem(sourceItemId, true);
        MetaChartSourceItemMapper.INSTANCE.setDetailOrderUpdateDTO(po, updateDTO);
        this.preparePO(po);
        metaChartSourceItemDAO.update(po);
        return po;
    }


}
