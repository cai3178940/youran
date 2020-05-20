package com.youran.generate.dao.chart;

import com.youran.common.dao.DAO;
import com.youran.generate.pojo.po.chart.source.item.MetaChartSourceItemPO;
import com.youran.generate.pojo.qo.chart.MetaChartSourceItemQO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 【图表数据源项】数据库操作
 *
 * @author cbb
 * @date 2020/04/04
 */
@Repository
@Mapper
public interface MetaChartSourceItemDAO extends DAO<MetaChartSourceItemPO> {

    /**
     * 根据条件查询【图表数据源项】列表
     * @param metaChartSourceItemQO
     * @return
     */
    List<MetaChartSourceItemPO> findListByQuery(MetaChartSourceItemQO metaChartSourceItemQO);


    List<MetaChartSourceItemPO> findByProjectId(Integer projectId);


    List<MetaChartSourceItemPO> findBySourceId(Integer sourceId);
}



