package com.youran.generate.service.chart;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.optimistic.OptimisticLock;
import com.youran.generate.dao.chart.MetaDashboardDAO;
import com.youran.generate.pojo.dto.chart.MetaDashboardAddDTO;
import com.youran.generate.pojo.dto.chart.MetaDashboardUpdateDTO;
import com.youran.generate.pojo.mapper.chart.MetaDashboardMapper;
import com.youran.generate.pojo.po.chart.MetaDashboardPO;
import com.youran.generate.pojo.qo.chart.MetaDashboardQO;
import com.youran.generate.pojo.vo.chart.MetaDashboardListVO;
import com.youran.generate.pojo.vo.chart.MetaDashboardShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 【看板】增删改查服务
 *
 * @author cbb
 * @date 2020/06/13
 */
@Service
public class MetaDashboardService {

    @Autowired
    private MetaDashboardDAO metaDashboardDAO;


    /**
     * 新增【看板】
     *
     * @param metaDashboardDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public MetaDashboardPO save(MetaDashboardAddDTO metaDashboardDTO) {
        MetaDashboardPO metaDashboard = MetaDashboardMapper.INSTANCE.fromAddDTO(metaDashboardDTO);
        metaDashboard.featureSerialize();
        metaDashboardDAO.save(metaDashboard);
        return metaDashboard;
    }

    /**
     * 修改【看板】
     *
     * @param metaDashboardUpdateDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @OptimisticLock
    public MetaDashboardPO update(MetaDashboardUpdateDTO metaDashboardUpdateDTO) {
        Integer dashboardId = metaDashboardUpdateDTO.getDashboardId();
        MetaDashboardPO metaDashboard = this.getMetaDashboard(dashboardId, true);
        MetaDashboardMapper.INSTANCE.setUpdateDTO(metaDashboard, metaDashboardUpdateDTO);
        metaDashboard.featureSerialize();
        metaDashboardDAO.update(metaDashboard);
        return metaDashboard;
    }

    /**
     * 查询列表
     *
     * @param metaDashboardQO
     * @return
     */
    public List<MetaDashboardListVO> list(MetaDashboardQO metaDashboardQO) {
        List<MetaDashboardListVO> list = metaDashboardDAO.findListByQuery(metaDashboardQO);
        return list;
    }

    /**
     * 根据主键获取【看板】
     *
     * @param dashboardId 主键
     * @param force 是否强制获取
     * @return
     */
    public MetaDashboardPO getMetaDashboard(Integer dashboardId, boolean force){
        MetaDashboardPO metaDashboard = metaDashboardDAO.findById(dashboardId);
        if (force && metaDashboard == null) {
            throw new BusinessException(ErrorCode.RECORD_NOT_FIND);
        }
        metaDashboard.featureDeserialize();
        return metaDashboard;
    }


    /**
     * 查询【看板】详情
     *
     * @param dashboardId
     * @return
     */
    public MetaDashboardShowVO show(Integer dashboardId) {
        MetaDashboardPO metaDashboard = this.getMetaDashboard(dashboardId, true);
        MetaDashboardShowVO showVO = MetaDashboardMapper.INSTANCE.toShowVO(metaDashboard);
        return showVO;
    }

    /**
     * 删除【看板】
     *
     * @param dashboardIds
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public int delete(Integer... dashboardIds) {
        int count = 0;
        for (Integer dashboardId : dashboardIds) {
            count += metaDashboardDAO.delete(dashboardId);
        }
        return count;
    }


}


