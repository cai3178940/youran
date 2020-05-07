package com.youran.generate.web.api.chart;

import com.youran.generate.pojo.dto.chart.source.item.AggOrderAddDTO;
import com.youran.generate.pojo.dto.chart.source.item.AggOrderUpdateDTO;
import com.youran.generate.pojo.qo.chart.MetaChartSourceItemQO;
import com.youran.generate.pojo.vo.chart.source.item.AggOrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * 【图表数据源项】API
 * <p>swagger接口文档
 *
 * @author cbb
 * @date 2020/04/04
 */
@Api(tags = "【图表数据源项】API")
public interface MetaChartSourceItemAPI {

    /**
     * 新增【聚合排序】
     */
    @ApiOperation(value="新增【聚合排序】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "addDTO", dataType = "AggOrderAddDTO", value = "新增【聚合排序】参数", paramType = "body"),
    })
    ResponseEntity<AggOrderVO> saveAggOrder(AggOrderAddDTO addDTO) throws Exception;

    /**
     * 修改【聚合排序】
     */
    @ApiOperation(value="修改【聚合排序】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "updateDTO", dataType = "AggOrderUpdateDTO", value = "修改【聚合排序】参数", paramType = "body"),
    })
    ResponseEntity<AggOrderVO> updateAggOrder(AggOrderUpdateDTO updateDTO);

    /**
     * 列表查询【聚合排序】
     */
    @ApiOperation(value="列表查询【聚合排序】")
    ResponseEntity<List<AggOrderVO>> listAggOrder(MetaChartSourceItemQO qo);

    /**
     * 批量删除【图表数据源项】
     */
    @ApiOperation(value = "批量删除【图表数据源项】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", dataType = "int", value = "id数组", paramType = "body"),
    })
    ResponseEntity<Integer> deleteBatch(Integer[] id);

}

