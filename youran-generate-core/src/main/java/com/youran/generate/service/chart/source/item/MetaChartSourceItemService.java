package com.youran.generate.service.chart.source.item;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.dao.chart.MetaChartSourceItemDAO;
import com.youran.generate.pojo.mapper.chart.MetaChartSourceItemMapper;
import com.youran.generate.pojo.po.chart.source.item.MetaChartSourceItemPO;
import com.youran.generate.pojo.qo.chart.MetaChartSourceItemQO;
import com.youran.generate.pojo.vo.chart.source.item.MetaChartSourceItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 【图表数据源项】增删改查服务
 *
 * @author cbb
 * @date 2020/04/04
 */
@Service
public class MetaChartSourceItemService {

    @Autowired
    private MetaChartSourceItemDAO metaChartSourceItemDAO;


    /**
     * 查询列表
     *
     * @param metaChartSourceItemQO
     * @return
     */
    public List<MetaChartSourceItemVO> list(MetaChartSourceItemQO metaChartSourceItemQO) {
        List<MetaChartSourceItemPO> list = metaChartSourceItemDAO.findListByQuery(metaChartSourceItemQO);
        return list.stream()
            .map(po -> po.castSubType(true))
            .map(po -> MetaChartSourceItemMapper.INSTANCE.poToVO((MetaChartSourceItemPO) po))
            .collect(Collectors.toList());
    }

    /**
     * 根据主键获取【图表数据源项】
     *
     * @param sourceItemId 主键
     * @param force 是否强制获取
     * @return
     */
    public <T extends MetaChartSourceItemPO> T getMetaChartSourceItem(Integer sourceItemId, boolean force){
        MetaChartSourceItemPO po = metaChartSourceItemDAO.findById(sourceItemId);
        if (force && po == null) {
            throw new BusinessException(ErrorCode.RECORD_NOT_FIND);
        }
        return po.castSubType(true);
    }


    /**
     * 删除【图表数据源项】
     *
     * @param sourceItemIds
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public int delete(Integer... sourceItemIds) {
        int count = 0;
        for (Integer sourceItemId : sourceItemIds) {
            count += metaChartSourceItemDAO.delete(sourceItemId);
        }
        return count;
    }


}


