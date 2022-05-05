
package com.youran.generate.web.rest.chart;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.SourceItemType;
import com.youran.generate.constant.WebConst;
import com.youran.generate.pojo.dto.chart.source.item.*;
import com.youran.generate.pojo.mapper.chart.MetaChartSourceItemMapper;
import com.youran.generate.pojo.po.chart.source.item.*;
import com.youran.generate.pojo.qo.chart.MetaChartSourceItemQO;
import com.youran.generate.pojo.vo.chart.source.item.*;
import com.youran.generate.service.chart.source.item.*;
import com.youran.generate.web.AbstractController;
import com.youran.generate.web.api.chart.MetaChartSourceItemAPI;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 【图表数据源项】控制器
 *
 * @author cbb
 * @date 2020/04/04
 */
@Deprecated
@RestController
@RequestMapping(WebConst.API_PATH + "/meta_chart_source_item")
public class MetaChartSourceItemController extends AbstractController implements MetaChartSourceItemAPI {

    @Autowired
    private MetaChartSourceItemService metaChartSourceItemService;
    @Autowired
    private AggOrderService aggOrderService;
    @Autowired
    private DetailColumnService detailColumnService;
    @Autowired
    private DetailOrderService detailOrderService;
    @Autowired
    private DimensionService dimensionService;
    @Autowired
    private HavingService havingService;
    @Autowired
    private MetricsService metricsService;
    @Autowired
    private WhereService whereService;

    /**
     * 保存
     */
    @Override
    @PostMapping("/agg_order")
    public ResponseEntity<AggOrderVO> saveAggOrder(@Valid @RequestBody AggOrderAddDTO addDTO) throws Exception {
        AggOrderPO po = aggOrderService.save(addDTO);
        return ResponseEntity.ok(MetaChartSourceItemMapper.INSTANCE.toAggOrderVO(po));
    }
    @Override
    @PostMapping("/detail_column")
    public ResponseEntity<DetailColumnVO> saveDetailColumn(@Valid @RequestBody DetailColumnAddDTO addDTO) throws Exception {
        DetailColumnPO po = detailColumnService.save(addDTO);
        return ResponseEntity.ok(MetaChartSourceItemMapper.INSTANCE.toDetailColumnVO(po));
    }
    @Override
    @PostMapping("/detail_order")
    public ResponseEntity<DetailOrderVO> saveDetailOrder(@Valid @RequestBody DetailOrderAddDTO addDTO) throws Exception {
        DetailOrderPO po = detailOrderService.save(addDTO);
        return ResponseEntity.ok(MetaChartSourceItemMapper.INSTANCE.toDetailOrderVO(po));
    }
    @Override
    @PostMapping("/dimension")
    public ResponseEntity<DimensionVO> saveDimension(@Valid @RequestBody DimensionAddDTO addDTO) throws Exception {
        DimensionPO po = dimensionService.save(addDTO);
        return ResponseEntity.ok(MetaChartSourceItemMapper.INSTANCE.toDimensionVO(po));
    }
    @Override
    @PostMapping("/having")
    public ResponseEntity<HavingVO> saveHaving(@Valid @RequestBody HavingAddDTO addDTO) throws Exception {
        HavingPO po = havingService.save(addDTO);
        return ResponseEntity.ok(MetaChartSourceItemMapper.INSTANCE.toHavingVO(po));
    }
    @Override
    @PostMapping("/metrics")
    public ResponseEntity<MetricsVO> saveMetrics(@Valid @RequestBody MetricsAddDTO addDTO) throws Exception {
        MetricsPO po = metricsService.save(addDTO);
        return ResponseEntity.ok(MetaChartSourceItemMapper.INSTANCE.toMetricsVO(po));
    }
    @Override
    @PostMapping("/where")
    public ResponseEntity<WhereVO> saveWhere(@Valid @RequestBody WhereAddDTO addDTO) throws Exception {
        WherePO po = whereService.save(addDTO);
        return ResponseEntity.ok(MetaChartSourceItemMapper.INSTANCE.toWhereVO(po));
    }

    /**
     * 修改
     */
    @Override
    @PutMapping("/agg_order")
    public ResponseEntity<AggOrderVO> updateAggOrder(@Valid @RequestBody AggOrderUpdateDTO updateDTO) {
        AggOrderPO po = aggOrderService.update(updateDTO);
        return ResponseEntity.ok(MetaChartSourceItemMapper.INSTANCE.toAggOrderVO(po));
    }
    @Override
    @PutMapping("/detail_column")
    public ResponseEntity<DetailColumnVO> updateDetailColumn(@Valid @RequestBody DetailColumnUpdateDTO updateDTO) {
        DetailColumnPO po = detailColumnService.update(updateDTO);
        return ResponseEntity.ok(MetaChartSourceItemMapper.INSTANCE.toDetailColumnVO(po));
    }
    @Override
    @PutMapping("/detail_order")
    public ResponseEntity<DetailOrderVO> updateDetailOrder(@Valid @RequestBody DetailOrderUpdateDTO updateDTO) {
        DetailOrderPO po = detailOrderService.update(updateDTO);
        return ResponseEntity.ok(MetaChartSourceItemMapper.INSTANCE.toDetailOrderVO(po));
    }
    @Override
    @PutMapping("/dimension")
    public ResponseEntity<DimensionVO> updateDimension(@Valid @RequestBody DimensionUpdateDTO updateDTO) {
        DimensionPO po = dimensionService.update(updateDTO);
        return ResponseEntity.ok(MetaChartSourceItemMapper.INSTANCE.toDimensionVO(po));
    }
    @Override
    @PutMapping("/having")
    public ResponseEntity<HavingVO> updateHaving(@Valid @RequestBody HavingUpdateDTO updateDTO) {
        HavingPO po = havingService.update(updateDTO);
        return ResponseEntity.ok(MetaChartSourceItemMapper.INSTANCE.toHavingVO(po));
    }
    @Override
    @PutMapping("/metrics")
    public ResponseEntity<MetricsVO> updateMetrics(@Valid @RequestBody MetricsUpdateDTO updateDTO) {
        MetricsPO po = metricsService.update(updateDTO);
        return ResponseEntity.ok(MetaChartSourceItemMapper.INSTANCE.toMetricsVO(po));
    }
    @Override
    @PutMapping("/where")
    public ResponseEntity<WhereVO> updateWhere(@Valid @RequestBody WhereUpdateDTO updateDTO) {
        WherePO po = whereService.update(updateDTO);
        return ResponseEntity.ok(MetaChartSourceItemMapper.INSTANCE.toWhereVO(po));
    }

    /**
     * 列表查询
     */
    @Override
    @GetMapping("/agg_order")
    public ResponseEntity<List<AggOrderVO>> listAggOrder(@Valid MetaChartSourceItemQO qo) {
        qo.setType(SourceItemType.AGG_ORDER.getValue());
        List<AggOrderVO> list = metaChartSourceItemService.list(qo);
        return ResponseEntity.ok(list);
    }
    @Override
    @GetMapping("/detail_column")
    public ResponseEntity<List<DetailColumnVO>> listDetailColumn(@Valid MetaChartSourceItemQO qo) {
        qo.setType(SourceItemType.DETAIL_COLUMN.getValue());
        List<DetailColumnVO> list = metaChartSourceItemService.list(qo);
        return ResponseEntity.ok(list);
    }
    @Override
    @GetMapping("/detail_order")
    public ResponseEntity<List<DetailOrderVO>> listDetailOrder(@Valid MetaChartSourceItemQO qo) {
        qo.setType(SourceItemType.DETAIL_ORDER.getValue());
        List<DetailOrderVO> list = metaChartSourceItemService.list(qo);
        return ResponseEntity.ok(list);
    }
    @Override
    @GetMapping("/dimension")
    public ResponseEntity<List<DimensionVO>> listDimension(@Valid MetaChartSourceItemQO qo) {
        qo.setType(SourceItemType.DIMENSION.getValue());
        List<DimensionVO> list = metaChartSourceItemService.list(qo);
        return ResponseEntity.ok(list);
    }
    @Override
    @GetMapping("/having")
    public ResponseEntity<List<HavingVO>> listHaving(@Valid MetaChartSourceItemQO qo) {
        qo.setType(SourceItemType.HAVING.getValue());
        List<HavingVO> list = metaChartSourceItemService.list(qo);
        return ResponseEntity.ok(list);
    }
    @Override
    @GetMapping("/metrics")
    public ResponseEntity<List<MetricsVO>> listMetrics(@Valid MetaChartSourceItemQO qo) {
        qo.setType(SourceItemType.METRICS.getValue());
        List<MetricsVO> list = metaChartSourceItemService.list(qo);
        return ResponseEntity.ok(list);
    }
    @Override
    @GetMapping("/where")
    public ResponseEntity<List<WhereVO>> listWhere(@Valid MetaChartSourceItemQO qo) {
        qo.setType(SourceItemType.WHERE.getValue());
        List<WhereVO> list = metaChartSourceItemService.list(qo);
        return ResponseEntity.ok(list);
    }


    /**
     * 删除
     */
    @Override
    @DeleteMapping
    public ResponseEntity<Integer> deleteBatch(@RequestBody Integer[] id) {
        if(ArrayUtils.isEmpty(id)){
            throw new BusinessException(ErrorCode.PARAM_IS_NULL);
        }
        int count = metaChartSourceItemService.delete(id);
        return ResponseEntity.ok(count);
    }

}


