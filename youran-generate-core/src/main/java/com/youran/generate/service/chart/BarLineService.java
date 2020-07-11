package com.youran.generate.service.chart;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.optimistic.OptimisticLock;
import com.youran.generate.dao.chart.MetaChartDAO;
import com.youran.generate.pojo.dto.chart.BarLineAddDTO;
import com.youran.generate.pojo.dto.chart.BarLineUpdateDTO;
import com.youran.generate.pojo.dto.chart.ChartItemDTO;
import com.youran.generate.pojo.mapper.chart.MetaChartMapper;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.po.chart.BarLinePO;
import com.youran.generate.pojo.po.chart.source.item.DimensionPO;
import com.youran.generate.pojo.po.chart.source.item.MetricsPO;
import com.youran.generate.pojo.vo.chart.BarLineVO;
import com.youran.generate.service.MetaProjectService;
import com.youran.generate.util.ChartValidateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 柱线图服务类
 *
 * @author: cbb
 * @date: 2020-06-05
 */
@Service
public class BarLineService {

    @Autowired
    private MetaChartDAO metaChartDAO;
    @Autowired
    private MetaChartService metaChartService;
    @Autowired
    private MetaProjectService metaProjectService;

    /**
     * 校验【柱线图】数据合法性
     *
     * @param po
     */
    public void check(BarLinePO po) {
        ChartItemDTO<DimensionPO> axisX = po.getAxisX();
        if (axisX == null) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "缺少x轴");
        }
        List<ChartItemDTO<MetricsPO>> axisYList = po.getAxisYList();
        if (CollectionUtils.isEmpty(axisYList)) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "缺少y轴");
        }
        List<ChartItemDTO> list = new ArrayList<>();
        list.add(axisX);
        if (po.getAxisX2() != null) {
            list.add(po.getAxisX2());
        }
        list.addAll(axisYList);
        ChartValidateUtil.checkItemAliasConflict(list);
    }

    /**
     * 新增【柱线图】
     *
     * @param addDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public BarLinePO save(BarLineAddDTO addDTO) {
        Integer projectId = addDTO.getProjectId();
        MetaProjectPO project = metaProjectService.getAndCheckProject(projectId);
        BarLinePO po = MetaChartMapper.INSTANCE.fromBarLineAddDTO(addDTO);
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
    public BarLinePO update(BarLineUpdateDTO updateDTO) {
        Integer chartId = updateDTO.getChartId();
        BarLinePO po = metaChartService.getMetaChart(chartId, true);
        Integer projectId = po.getProjectId();
        MetaProjectPO project = metaProjectService.getAndCheckProject(projectId);
        MetaChartMapper.INSTANCE.setBarLineUpdateDTO(po, updateDTO);
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
    public BarLineVO show(Integer chartId) {
        BarLinePO po = metaChartService.getMetaChart(chartId, true);
        BarLineVO vo = MetaChartMapper.INSTANCE.toBarLineVO(po);
        return vo;
    }

}
