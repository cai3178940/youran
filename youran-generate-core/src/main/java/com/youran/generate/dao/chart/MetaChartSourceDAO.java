package com.youran.generate.dao.chart;

import com.youran.common.dao.DAO;
import com.youran.generate.pojo.po.chart.source.MetaChartSourcePO;
import com.youran.generate.pojo.qo.chart.MetaChartSourceQO;
import com.youran.generate.pojo.vo.chart.source.MetaChartSourceListVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 【图表数据源】数据库操作
 *
 * @author cbb
 * @date 2020/04/04
 */
@Repository
@Mapper
public interface MetaChartSourceDAO extends DAO<MetaChartSourcePO> {

    /**
     * 根据条件查询【图表数据源】列表
     * @param metaChartSourceQO
     * @return
     */
    List<MetaChartSourceListVO> findListByQuery(MetaChartSourceQO metaChartSourceQO);


}



