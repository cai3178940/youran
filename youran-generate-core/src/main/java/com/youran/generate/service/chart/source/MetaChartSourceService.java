package com.youran.generate.service.chart.source;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.optimistic.OptimisticLock;
import com.youran.generate.dao.chart.MetaChartSourceDAO;
import com.youran.generate.pojo.dto.chart.source.MetaChartSourceAddDTO;
import com.youran.generate.pojo.dto.chart.source.MetaChartSourceUpdateDTO;
import com.youran.generate.pojo.mapper.chart.MetaChartSourceMapper;
import com.youran.generate.pojo.po.chart.source.MetaChartSourcePO;
import com.youran.generate.pojo.qo.chart.MetaChartSourceQO;
import com.youran.generate.pojo.vo.chart.source.MetaChartSourceListVO;
import com.youran.generate.pojo.vo.chart.source.MetaChartSourceShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 【图表数据源】增删改查服务
 *
 * @author cbb
 * @date 2020/04/04
 */
@Service
public class MetaChartSourceService {

    @Autowired
    private MetaChartSourceDAO metaChartSourceDAO;


    /**
     * 新增【图表数据源】
     *
     * @param metaChartSourceDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public MetaChartSourcePO save(MetaChartSourceAddDTO metaChartSourceDTO) {
        MetaChartSourcePO metaChartSource = MetaChartSourceMapper.INSTANCE.fromAddDTO(metaChartSourceDTO);
        metaChartSourceDAO.save(metaChartSource);
        return metaChartSource;
    }

    /**
     * 修改【图表数据源】
     *
     * @param metaChartSourceUpdateDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @OptimisticLock
    public MetaChartSourcePO update(MetaChartSourceUpdateDTO metaChartSourceUpdateDTO) {
        Integer sourceId = metaChartSourceUpdateDTO.getSourceId();
        MetaChartSourcePO metaChartSource = this.getMetaChartSource(sourceId, true);
        MetaChartSourceMapper.INSTANCE.setUpdateDTO(metaChartSource, metaChartSourceUpdateDTO);
        metaChartSourceDAO.update(metaChartSource);
        return metaChartSource;
    }

    /**
     * 查询列表
     *
     * @param metaChartSourceQO
     * @return
     */
    public List<MetaChartSourceListVO> list(MetaChartSourceQO metaChartSourceQO) {
        List<MetaChartSourceListVO> list = metaChartSourceDAO.findListByQuery(metaChartSourceQO);
        return list;
    }

    /**
     * 根据主键获取【图表数据源】
     *
     * @param sourceId 主键
     * @param force 是否强制获取
     * @return
     */
    public MetaChartSourcePO getMetaChartSource(Integer sourceId, boolean force){
        MetaChartSourcePO metaChartSource = metaChartSourceDAO.findById(sourceId);
        if (force && metaChartSource == null) {
            throw new BusinessException(ErrorCode.RECORD_NOT_FIND);
        }
        return metaChartSource;
    }


    /**
     * 查询【图表数据源】详情
     *
     * @param sourceId
     * @return
     */
    public MetaChartSourceShowVO show(Integer sourceId) {
        MetaChartSourcePO metaChartSource = this.getMetaChartSource(sourceId, true);
        MetaChartSourceShowVO showVO = MetaChartSourceMapper.INSTANCE.toShowVO(metaChartSource);
        return showVO;
    }

    /**
     * 删除【图表数据源】
     *
     * @param sourceIds
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public int delete(Integer... sourceIds) {
        int count = 0;
        for (Integer sourceId : sourceIds) {
            count += metaChartSourceDAO.delete(sourceId);
        }
        return count;
    }


}


