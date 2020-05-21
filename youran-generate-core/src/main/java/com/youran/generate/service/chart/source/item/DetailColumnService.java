package com.youran.generate.service.chart.source.item;

import com.youran.common.optimistic.OptimisticLock;
import com.youran.generate.dao.chart.MetaChartSourceItemDAO;
import com.youran.generate.pojo.dto.chart.source.item.DetailColumnAddDTO;
import com.youran.generate.pojo.dto.chart.source.item.DetailColumnUpdateDTO;
import com.youran.generate.pojo.mapper.chart.MetaChartSourceItemMapper;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.po.chart.source.item.DetailColumnPO;
import com.youran.generate.service.MetaProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 【明细列】服务类
 *
 * @author: cbb
 * @date: 2020-05-10
 */
@Service
public class DetailColumnService {

    @Autowired
    private MetaChartSourceItemDAO metaChartSourceItemDAO;
    @Autowired
    private MetaChartSourceItemService metaChartSourceItemService;
    @Autowired
    private MetaProjectService metaProjectService;
    /**
     * 【明细列】数据预处理
     *
     * @param po
     */
    public void preparePO(DetailColumnPO po) {
        po.featureSerialize();
        metaChartSourceItemService.preparePO(po);
    }

    /**
     * 新增【明细列】
     *
     * @param addDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public DetailColumnPO save(DetailColumnAddDTO addDTO) {
        Integer projectId = addDTO.getProjectId();
        MetaProjectPO project = metaProjectService.getAndCheckProject(projectId);
        DetailColumnPO po = this.doSave(addDTO);
        metaProjectService.updateProject(project);
        return po;
    }

    public DetailColumnPO doSave(DetailColumnAddDTO addDTO) {
        DetailColumnPO po = MetaChartSourceItemMapper.INSTANCE
            .fromDetailColumnAddDTO(addDTO);
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
    public DetailColumnPO update(DetailColumnUpdateDTO updateDTO) {
        Integer projectId = updateDTO.getProjectId();
        MetaProjectPO project = metaProjectService.getAndCheckProject(projectId);
        DetailColumnPO po = this.doUpdate(updateDTO);
        metaProjectService.updateProject(project);
        return po;
    }

    public DetailColumnPO doUpdate(DetailColumnUpdateDTO updateDTO) {
        Integer sourceItemId = updateDTO.getSourceItemId();
        DetailColumnPO po = metaChartSourceItemService.getMetaChartSourceItem(sourceItemId, true);
        MetaChartSourceItemMapper.INSTANCE.setDetailColumnUpdateDTO(po, updateDTO);
        this.preparePO(po);
        metaChartSourceItemDAO.update(po);
        return po;
    }

}
