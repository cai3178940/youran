package com.youran.generate.service.chart;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.optimistic.OptimisticLock;
import com.youran.generate.pojo.dto.chart.ChartItemDTO;
import com.youran.generate.pojo.dto.chart.PieAddDTO;
import com.youran.generate.pojo.dto.chart.PieUpdateDTO;
import com.youran.generate.pojo.mapper.chart.MetaChartMapper;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.po.chart.PiePO;
import com.youran.generate.pojo.po.chart.source.item.DimensionPO;
import com.youran.generate.pojo.po.chart.source.item.MetricsPO;
import com.youran.generate.pojo.vo.chart.PieVO;
import com.youran.generate.service.MetaProjectService;
import com.youran.generate.util.ChartValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * 饼图服务类
 *
 * @author: cbb
 * @date: 2020-06-05
 */
@Service
public class PieService {

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
        ChartItemDTO<DimensionPO> dimension = po.getDimension();
        ChartItemDTO<MetricsPO> metrics = po.getMetrics();
        if (dimension == null) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "未指定维度");
        }
        if (metrics == null) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "未指定指标");
        }
        ChartValidateUtil.checkItemAliasConflict(Arrays.asList(dimension, metrics));
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
        metaChartService.doSave(po);
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
        metaChartService.doUpdate(po);
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
