
package com.youran.generate.web.rest.chart;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.SourceItemType;
import com.youran.generate.constant.WebConst;
import com.youran.generate.pojo.dto.chart.source.item.AggOrderAddDTO;
import com.youran.generate.pojo.dto.chart.source.item.AggOrderUpdateDTO;
import com.youran.generate.pojo.mapper.chart.MetaChartSourceItemMapper;
import com.youran.generate.pojo.po.chart.source.item.AggOrderPO;
import com.youran.generate.pojo.qo.chart.MetaChartSourceItemQO;
import com.youran.generate.pojo.vo.chart.source.item.AggOrderVO;
import com.youran.generate.service.chart.source.item.AggOrderService;
import com.youran.generate.service.chart.source.item.MetaChartSourceItemService;
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
@RestController
@RequestMapping(WebConst.API_PATH + "/metaChartSourceItem")
public class MetaChartSourceItemController extends AbstractController implements MetaChartSourceItemAPI {

    @Autowired
    private MetaChartSourceItemService metaChartSourceItemService;
    @Autowired
    private AggOrderService aggOrderService;

    @Override
    @PostMapping("/aggOrder")
    public ResponseEntity<AggOrderVO> saveAggOrder(@Valid @RequestBody AggOrderAddDTO addDTO) throws Exception {
        AggOrderPO po = aggOrderService.save(addDTO);
        return ResponseEntity.ok(MetaChartSourceItemMapper.INSTANCE.toAggOrderVO(po));
    }

    @Override
    @PutMapping("/aggOrder")
    public ResponseEntity<AggOrderVO> updateAggOrder(@Valid @RequestBody AggOrderUpdateDTO updateDTO) {
        AggOrderPO po = aggOrderService.update(updateDTO);
        return ResponseEntity.ok(MetaChartSourceItemMapper.INSTANCE.toAggOrderVO(po));
    }

    @Override
    @GetMapping("/aggOrder")
    public ResponseEntity<List<AggOrderVO>> listAggOrder(@Valid MetaChartSourceItemQO qo) {
        qo.setType(SourceItemType.AGG_ORDER.getValue());
        List<AggOrderVO> list = metaChartSourceItemService.list(qo);
        return ResponseEntity.ok(list);
    }


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


