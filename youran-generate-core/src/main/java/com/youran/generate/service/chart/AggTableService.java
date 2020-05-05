package com.youran.generate.service.chart;

import com.youran.common.optimistic.OptimisticLock;
import com.youran.generate.dao.chart.MetaChartDAO;
import com.youran.generate.pojo.dto.chart.AggTableAddDTO;
import com.youran.generate.pojo.dto.chart.AggTableUpdateDTO;
import com.youran.generate.pojo.mapper.chart.MetaChartMapper;
import com.youran.generate.pojo.po.chart.AggTablePO;
import com.youran.generate.pojo.vo.chart.AggTableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    /**
     * 校验【聚合表】数据合法性
     *
     * @param po
     */
    public void check(AggTablePO po) {
        // TODO
    }

    /**
     * 新增【聚合表】
     *
     * @param addDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public AggTablePO save(AggTableAddDTO addDTO) {
        AggTablePO po = MetaChartMapper.INSTANCE.fromAggTableAddDTO(addDTO);
        po.featureSerialize();
        this.check(po);
        metaChartDAO.save(po);
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
        MetaChartMapper.INSTANCE.setAggTableUpdateDTO(po, updateDTO);
        po.featureSerialize();
        this.check(po);
        metaChartDAO.update(po);
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
