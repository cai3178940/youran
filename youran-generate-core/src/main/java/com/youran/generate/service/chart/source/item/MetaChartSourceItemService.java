package com.youran.generate.service.chart.source.item;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.dao.chart.MetaChartSourceItemDAO;
import com.youran.generate.pojo.mapper.chart.MetaChartSourceItemMapper;
import com.youran.generate.pojo.po.chart.source.MetaChartSourcePO;
import com.youran.generate.pojo.po.chart.source.item.MetaChartSourceItemPO;
import com.youran.generate.pojo.qo.chart.MetaChartSourceItemQO;
import com.youran.generate.pojo.vo.chart.source.item.MetaChartSourceItemVO;
import com.youran.generate.service.MetaProjectService;
import com.youran.generate.service.chart.source.MetaChartSourceService;
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
    @Autowired
    private MetaProjectService metaProjectService;
    @Autowired
    private MetaChartSourceService metaChartSourceService;
    /**
     * 【图表数据源项】数据预处理
     *
     * @param po
     */
    public void preparePO(MetaChartSourceItemPO po){
        metaProjectService.getAndCheckProject(po.getProjectId());
        MetaChartSourcePO chartSource = metaChartSourceService.getMetaChartSource(po.getSourceId(), true);
        po.setChartSource(chartSource);
        if(po.getParentId()!=null){
            MetaChartSourceItemPO parent = this.getMetaChartSourceItem(po.getParentId(), true);
            po.setParent(parent);
        }
    }

    /**
     * 查询列表
     *
     * @param metaChartSourceItemQO
     * @return
     */
    public <T extends MetaChartSourceItemVO> List<T> list(MetaChartSourceItemQO metaChartSourceItemQO) {
        if (metaChartSourceItemQO.getType() == null) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "type参数不能为空");
        }
        List<MetaChartSourceItemPO> list = metaChartSourceItemDAO.findListByQuery(metaChartSourceItemQO);
        return list.stream()
            .map(po -> po.castSubType(true))
            .map(po -> (T) MetaChartSourceItemMapper.INSTANCE.poToVO((MetaChartSourceItemPO) po))
            .collect(Collectors.toList());
    }

    /**
     * 根据主键获取【图表数据源项】
     *
     * @param sourceItemId 主键
     * @param force        是否强制获取
     * @return
     */
    public <T extends MetaChartSourceItemPO> T getMetaChartSourceItem(Integer sourceItemId, boolean force) {
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


