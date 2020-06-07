package com.youran.generate.service.chart;

import com.youran.common.optimistic.OptimisticLock;
import com.youran.generate.dao.chart.MetaChartDAO;
import com.youran.generate.pojo.dto.chart.PieAddDTO;
import com.youran.generate.pojo.dto.chart.PieUpdateDTO;
import com.youran.generate.pojo.mapper.chart.MetaChartMapper;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.po.chart.PiePO;
import com.youran.generate.pojo.vo.chart.PieVO;
import com.youran.generate.service.MetaProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 饼图服务类
 *
 * @author: cbb
 * @date: 2020-06-05
 */
@Service
public class PieService {

    @Autowired
    private MetaChartDAO metaChartDAO;
    @Autowired
    private MetaChartService metaChartService;
    @Autowired
    private MetaProjectService metaProjectService;

    /**
     * 校验【饼图】数据合法性
     *
     * @param po
     */
    public void check(PiePO po) {
        // TODO
    }

    /**
     * 新增【饼图】
     *
     * @param addDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public PiePO save(PieAddDTO addDTO) {
        Integer projectId = addDTO.getProjectId();
        MetaProjectPO project = metaProjectService.getAndCheckProject(projectId);
        PiePO po = MetaChartMapper.INSTANCE.fromPieAddDTO(addDTO);
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
    public PiePO update(PieUpdateDTO updateDTO) {
        Integer chartId = updateDTO.getChartId();
        PiePO po = metaChartService.getMetaChart(chartId, true);
        Integer projectId = po.getProjectId();
        MetaProjectPO project = metaProjectService.getAndCheckProject(projectId);
        MetaChartMapper.INSTANCE.setPieUpdateDTO(po, updateDTO);
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
    public PieVO show(Integer chartId) {
        PiePO po = metaChartService.getMetaChart(chartId, true);
        PieVO vo = MetaChartMapper.INSTANCE.toPieVO(po);
        return vo;
    }

}
