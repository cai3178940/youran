package com.youran.generate.dao.chart;

import com.youran.common.dao.DAO;
import com.youran.generate.pojo.po.chart.MetaDashboardPO;
import com.youran.generate.pojo.qo.chart.MetaDashboardQO;
import com.youran.generate.pojo.vo.chart.MetaDashboardListVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 【看板】数据库操作
 *
 * @author cbb
 * @date 2020/06/13
 */
@Repository
@Mapper
public interface MetaDashboardDAO extends DAO<MetaDashboardPO> {

    /**
     * 根据条件查询【看板】列表
     * @param metaDashboardQO
     * @return
     */
    List<MetaDashboardListVO> findListByQuery(MetaDashboardQO metaDashboardQO);


    /**
     * 根据项目id查询所有【看板】列表
     * @param projectId
     * @return
     */
    List<MetaDashboardPO> findByProjectId(Integer projectId);


}



