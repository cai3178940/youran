package com.youran.generate.service.chart.source.item;

import com.youran.common.optimistic.OptimisticLock;
import com.youran.generate.dao.chart.MetaChartSourceItemDAO;
import com.youran.generate.pojo.dto.chart.source.item.WhereAddDTO;
import com.youran.generate.pojo.dto.chart.source.item.WhereUpdateDTO;
import com.youran.generate.pojo.mapper.chart.MetaChartSourceItemMapper;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.po.chart.source.item.WherePO;
import com.youran.generate.service.MetaProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 【where条件】服务类
 *
 * @author: cbb
 * @date: 2020-05-10
 */
@Service
public class WhereService {

    @Autowired
    private MetaChartSourceItemDAO metaChartSourceItemDAO;
    @Autowired
    private MetaChartSourceItemService metaChartSourceItemService;
    @Autowired
    private MetaProjectService metaProjectService;
    /**
     * 【where条件】数据预处理
     *
     * @param po
     */
    public void preparePO(WherePO po) {
        po.featureSerialize();
        metaChartSourceItemService.preparePO(po);
    }

    /**
     * 新增【where条件】
     *
     * @param addDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public WherePO save(WhereAddDTO addDTO) {
        Integer projectId = addDTO.getProjectId();
        MetaProjectPO project = metaProjectService.getAndCheckProject(projectId);
        WherePO po = this.doSave(addDTO);
        metaProjectService.updateProject(project);
        return po;
    }

    public WherePO doSave(WhereAddDTO addDTO) {
        WherePO po = MetaChartSourceItemMapper.INSTANCE
            .fromWhereAddDTO(addDTO);
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
    public WherePO update(WhereUpdateDTO updateDTO) {
        Integer projectId = updateDTO.getProjectId();
        MetaProjectPO project = metaProjectService.getAndCheckProject(projectId);
        WherePO po = this.doUpdate(updateDTO);
        metaProjectService.updateProject(project);
        return po;
    }

    public WherePO doUpdate(WhereUpdateDTO updateDTO) {
        Integer sourceItemId = updateDTO.getSourceItemId();
        WherePO po = metaChartSourceItemService.getMetaChartSourceItem(sourceItemId, true);
        MetaChartSourceItemMapper.INSTANCE.setWhereUpdateDTO(po, updateDTO);
        this.preparePO(po);
        metaChartSourceItemDAO.update(po);
        return po;
    }
}
