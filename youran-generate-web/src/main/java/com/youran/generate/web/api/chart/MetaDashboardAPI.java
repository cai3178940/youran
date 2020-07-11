package com.youran.generate.web.api.chart;

import com.youran.generate.pojo.dto.chart.MetaDashboardAddDTO;
import com.youran.generate.pojo.dto.chart.MetaDashboardUpdateDTO;
import com.youran.generate.pojo.qo.chart.MetaDashboardQO;
import com.youran.generate.pojo.vo.chart.MetaDashboardListVO;
import com.youran.generate.pojo.vo.chart.MetaDashboardShowVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * 【看板】API
 * <p>swagger接口文档
 *
 * @author cbb
 * @date 2020/06/13
 */
@Api(tags = "【看板】API")
public interface MetaDashboardAPI {

    /**
     * 新增【看板】
     */
    @ApiOperation(value="新增【看板】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "metaDashboardAddDTO", dataType = "MetaDashboardAddDTO", value = "新增【看板】参数", paramType = "body"),
    })
    ResponseEntity<MetaDashboardShowVO> save(MetaDashboardAddDTO metaDashboardAddDTO) throws Exception;

    /**
     * 修改【看板】
     */
    @ApiOperation(value="修改【看板】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "metaDashboardUpdateDTO", dataType = "MetaDashboardUpdateDTO", value = "修改【看板】参数", paramType = "body"),
    })
    ResponseEntity<MetaDashboardShowVO> update(MetaDashboardUpdateDTO metaDashboardUpdateDTO);

    /**
     * 列表查询【看板】
     */
    @ApiOperation(value="列表查询【看板】")
    ResponseEntity<List<MetaDashboardListVO>> list(MetaDashboardQO metaDashboardQO);

    /**
     * 查看【看板】详情
     */
    @ApiOperation(value="查看【看板】详情")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "dashboardId", dataType = "int", value = "【看板】id", paramType = "path"),
    })
    ResponseEntity<MetaDashboardShowVO> show(Integer dashboardId);

    /**
     * 删除单个【看板】
     */
    @ApiOperation(value="删除单个【看板】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "dashboardId", dataType = "int", value = "【看板】id", paramType = "path"),
    })
    ResponseEntity<Integer> delete(Integer dashboardId);

    /**
     * 批量删除【看板】
     */
    @ApiOperation(value = "批量删除【看板】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", dataType = "int", value = "id数组", paramType = "body"),
    })
    ResponseEntity<Integer> deleteBatch(Integer[] id);

}

