package com.youran.generate.web.api.chart;

import com.youran.generate.pojo.dto.chart.source.MetaChartSourceAddDTO;
import com.youran.generate.pojo.dto.chart.source.MetaChartSourceUpdateDTO;
import com.youran.generate.pojo.dto.chart.source.MetaChartSourceWithItemsAddDTO;
import com.youran.generate.pojo.dto.chart.source.MetaChartSourceWithItemsUpdateDTO;
import com.youran.generate.pojo.vo.chart.source.MetaChartSourceShowVO;
import com.youran.generate.pojo.vo.chart.source.MetaChartSourceWithItemsShowVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

/**
 * 图表数据源
 *
 * @author: cbb
 * @date: 2020-05-07
 */
@Api(tags = "【图表数据源】API")
public interface MetaChartSourceAPI {

    /**
     * 新增【图表数据源】
     */
    @ApiOperation(value="新增【图表数据源】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "metaChartSourceAddDTO", dataType = "MetaChartSourceAddDTO", value = "新增【图表数据源】参数", paramType = "body"),
    })
    ResponseEntity<MetaChartSourceShowVO> save(MetaChartSourceAddDTO metaChartSourceAddDTO) throws Exception;

    /**
     * 修改【图表数据源】
     */
    @ApiOperation(value="修改【图表数据源】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "metaChartSourceUpdateDTO", dataType = "MetaChartSourceUpdateDTO", value = "修改【图表数据源】参数", paramType = "body"),
    })
    ResponseEntity<MetaChartSourceShowVO> update(MetaChartSourceUpdateDTO metaChartSourceUpdateDTO);

    /**
     * 新增【图表数据源】带数据项
     */
    @ApiOperation(value="新增【图表数据源】带数据项")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "addDTO", dataType = "MetaChartSourceAddDTO", value = "新增【图表数据源】参数", paramType = "body"),
    })
    ResponseEntity<MetaChartSourceShowVO> saveWithItems(MetaChartSourceWithItemsAddDTO addDTO) throws Exception;

    /**
     * 修改【图表数据源】带数据项
     */
    @ApiOperation(value="修改【图表数据源】带数据项")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "updateDTO", dataType = "MetaChartSourceUpdateDTO", value = "修改【图表数据源】参数", paramType = "body"),
    })
    ResponseEntity<MetaChartSourceShowVO> updateWithItems(MetaChartSourceWithItemsUpdateDTO updateDTO);

    /**
     * 查看【图表数据源】详情
     */
    @ApiOperation(value="查看【图表数据源】详情")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "sourceId", dataType = "int", value = "【图表数据源】id", paramType = "path"),
    })
    ResponseEntity<MetaChartSourceShowVO> show(Integer sourceId);

    /**
     * 查看【图表数据源】带数据项的详情
     */
    @ApiOperation(value="查看【图表数据源】带数据项的详情")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "sourceId", dataType = "int", value = "【图表数据源】id", paramType = "path"),
    })
    ResponseEntity<MetaChartSourceWithItemsShowVO> showWithItems(Integer sourceId);

    /**
     * 批量删除【图表数据源】
     */
    @ApiOperation(value = "批量删除【图表数据源】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", dataType = "int", value = "id数组", paramType = "body"),
    })
    ResponseEntity<Integer> deleteBatch(Integer[] id);



}
