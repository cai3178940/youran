package com.youran.generate.web.api.chart;

import com.youran.generate.pojo.dto.chart.source.item.*;
import com.youran.generate.pojo.qo.chart.MetaChartSourceItemQO;
import com.youran.generate.pojo.vo.chart.source.item.*;
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
     * 新增
     */
    @ApiOperation(value="新增【聚合排序】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "addDTO", dataType = "AggOrderAddDTO", value = "新增【聚合排序】参数", paramType = "body"),
    })
    ResponseEntity<AggOrderVO> saveAggOrder(AggOrderAddDTO addDTO) throws Exception;
    @ApiOperation(value="新增【明细列】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "addDTO", dataType = "DetailColumnAddDTO", value = "新增【明细列】参数", paramType = "body"),
    })
    ResponseEntity<DetailColumnVO> saveDetailColumn(DetailColumnAddDTO addDTO) throws Exception;
    @ApiOperation(value="新增【明细排序】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "addDTO", dataType = "DetailOrderAddDTO", value = "新增【明细排序】参数", paramType = "body"),
    })
    ResponseEntity<DetailOrderVO> saveDetailOrder(DetailOrderAddDTO addDTO) throws Exception;
    @ApiOperation(value="新增【维度】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "addDTO", dataType = "DimensionAddDTO", value = "新增【维度】参数", paramType = "body"),
    })
    ResponseEntity<DimensionVO> saveDimension(DimensionAddDTO addDTO) throws Exception;
    @ApiOperation(value="新增【having条件】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "addDTO", dataType = "HavingAddDTO", value = "新增【having条件】参数", paramType = "body"),
    })
    ResponseEntity<HavingVO> saveHaving(HavingAddDTO addDTO) throws Exception;
    @ApiOperation(value="新增【指标】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "addDTO", dataType = "MetricsAddDTO", value = "新增【指标】参数", paramType = "body"),
    })
    ResponseEntity<MetricsVO> saveMetrics(MetricsAddDTO addDTO) throws Exception;
    @ApiOperation(value="新增【where条件】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "addDTO", dataType = "WhereAddDTO", value = "新增【where条件】参数", paramType = "body"),
    })
    ResponseEntity<WhereVO> saveWhere(WhereAddDTO addDTO) throws Exception;

    /**
     * 修改
     */
    @ApiOperation(value="修改【聚合排序】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "updateDTO", dataType = "AggOrderUpdateDTO", value = "修改【聚合排序】参数", paramType = "body"),
    })
    ResponseEntity<AggOrderVO> updateAggOrder(AggOrderUpdateDTO updateDTO);
    @ApiOperation(value="修改【明细列】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "updateDTO", dataType = "DetailColumnUpdateDTO", value = "修改【明细列】参数", paramType = "body"),
    })
    ResponseEntity<DetailColumnVO> updateDetailColumn(DetailColumnUpdateDTO updateDTO);
    @ApiOperation(value="修改【明细排序】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "updateDTO", dataType = "DetailOrderUpdateDTO", value = "修改【明细排序】参数", paramType = "body"),
    })
    ResponseEntity<DetailOrderVO> updateDetailOrder(DetailOrderUpdateDTO updateDTO);
    @ApiOperation(value="修改【维度】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "updateDTO", dataType = "DimensionUpdateDTO", value = "修改【维度】参数", paramType = "body"),
    })
    ResponseEntity<DimensionVO> updateDimension(DimensionUpdateDTO updateDTO);
    @ApiOperation(value="修改【having条件】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "updateDTO", dataType = "HavingUpdateDTO", value = "修改【having条件】参数", paramType = "body"),
    })
    ResponseEntity<HavingVO> updateHaving(HavingUpdateDTO updateDTO);
    @ApiOperation(value="修改【指标】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "updateDTO", dataType = "MetricsUpdateDTO", value = "修改【指标】参数", paramType = "body"),
    })
    ResponseEntity<MetricsVO> updateMetrics(MetricsUpdateDTO updateDTO);
    @ApiOperation(value="修改【where条件】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "updateDTO", dataType = "WhereUpdateDTO", value = "修改【where条件】参数", paramType = "body"),
    })
    ResponseEntity<WhereVO> updateWhere(WhereUpdateDTO updateDTO);

    /**
     * 列表查询
     */
    @ApiOperation(value="列表查询【聚合排序】")
    ResponseEntity<List<AggOrderVO>> listAggOrder(MetaChartSourceItemQO qo);
    @ApiOperation(value="列表查询【明细列】")
    ResponseEntity<List<DetailColumnVO>> listDetailColumn(MetaChartSourceItemQO qo);
    @ApiOperation(value="列表查询【明细排序】")
    ResponseEntity<List<DetailOrderVO>> listDetailOrder(MetaChartSourceItemQO qo);
    @ApiOperation(value="列表查询【维度】")
    ResponseEntity<List<DimensionVO>> listDimension(MetaChartSourceItemQO qo);
    @ApiOperation(value="列表查询【having条件】")
    ResponseEntity<List<HavingVO>> listHaving(MetaChartSourceItemQO qo);
    @ApiOperation(value="列表查询【指标】")
    ResponseEntity<List<MetricsVO>> listMetrics(MetaChartSourceItemQO qo);
    @ApiOperation(value="列表查询【where条件】")
    ResponseEntity<List<WhereVO>> listWhere(MetaChartSourceItemQO qo);

    /**
     * 批量删除【图表数据源项】
     */
    @ApiOperation(value = "批量删除【图表数据源项】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", dataType = "int", value = "id数组", paramType = "body"),
    })
    ResponseEntity<Integer> deleteBatch(Integer[] id);

}

