package com.youran.generate.web.api.chart;

import com.youran.generate.pojo.dto.chart.*;
import com.youran.generate.pojo.qo.chart.MetaChartQO;
import com.youran.generate.pojo.vo.chart.AggTableVO;
import com.youran.generate.pojo.vo.chart.BarLineVO;
import com.youran.generate.pojo.vo.chart.DetailListVO;
import com.youran.generate.pojo.vo.chart.MetaChartListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * 【图表】API
 * <p>swagger接口文档
 *
 * @author cbb
 * @date 2020/04/04
 */
@Api(tags = "【图表】API")
public interface MetaChartAPI {

    /**
     * 新增【聚合表】
     */
    @ApiOperation(value="新增【聚合表】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "addDTO", dataType = "AggTableAddDTO", value = "新增【聚合表】参数", paramType = "body"),
    })
    ResponseEntity<AggTableVO> saveAggTable(AggTableAddDTO addDTO) throws Exception;

    /**
     * 修改【聚合表】
     */
    @ApiOperation(value="修改【聚合表】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "updateDTO", dataType = "AggTableUpdateDTO", value = "修改【聚合表】参数", paramType = "body"),
    })
    ResponseEntity<AggTableVO> updateAggTable(AggTableUpdateDTO updateDTO);

    /**
     * 查看【聚合表】详情
     */
    @ApiOperation(value="查看【聚合表】详情")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "chartId", dataType = "int", value = "【聚合表】id", paramType = "path"),
    })
    ResponseEntity<AggTableVO> showAggTable(Integer chartId);

    /**
     * 新增【明细表】
     */
    @ApiOperation(value="新增【明细表】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "addDTO", dataType = "DetailListAddDTO", value = "新增【明细表】参数", paramType = "body"),
    })
    ResponseEntity<DetailListVO> saveDetailList(DetailListAddDTO addDTO) throws Exception;

    /**
     * 修改【明细表】
     */
    @ApiOperation(value="修改【明细表】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "updateDTO", dataType = "DetailListUpdateDTO", value = "修改【明细表】参数", paramType = "body"),
    })
    ResponseEntity<DetailListVO> updateDetailList(DetailListUpdateDTO updateDTO);

    /**
     * 查看【明细表】详情
     */
    @ApiOperation(value="查看【明细表】详情")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "chartId", dataType = "int", value = "【明细表】id", paramType = "path"),
    })
    ResponseEntity<DetailListVO> showDetailList(Integer chartId);

    /**
     * 新增【柱线图】
     */
    @ApiOperation(value="新增【柱线图】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "addDTO", dataType = "BarLineAddDTO", value = "新增【柱线图】参数", paramType = "body"),
    })
    ResponseEntity<BarLineVO> saveBarLine(BarLineAddDTO addDTO) throws Exception;

    /**
     * 修改【柱线图】
     */
    @ApiOperation(value="修改【柱线图】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "updateDTO", dataType = "BarLineUpdateDTO", value = "修改【柱线图】参数", paramType = "body"),
    })
    ResponseEntity<BarLineVO> updateBarLine(BarLineUpdateDTO updateDTO);

    /**
     * 查看【柱线图】详情
     */
    @ApiOperation(value="查看【柱线图】详情")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "chartId", dataType = "int", value = "【柱线图】id", paramType = "path"),
    })
    ResponseEntity<BarLineVO> showBarLine(Integer chartId);



    /**
     * 列表查询【图表】
     */
    @ApiOperation(value="列表查询【图表】")
    ResponseEntity<List<MetaChartListVO>> list(MetaChartQO metaChartQO);

    /**
     * 批量删除【图表】
     */
    @ApiOperation(value = "批量删除【图表】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", dataType = "int", value = "id数组", paramType = "body"),
    })
    ResponseEntity<Integer> deleteBatch(Integer[] id);

}
