package com.youran.generate.web.rest.chart;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.WebConst;
import com.youran.generate.pojo.dto.chart.AggTableAddDTO;
import com.youran.generate.pojo.dto.chart.AggTableUpdateDTO;
import com.youran.generate.pojo.dto.chart.DetailListAddDTO;
import com.youran.generate.pojo.dto.chart.DetailListUpdateDTO;
import com.youran.generate.pojo.mapper.chart.MetaChartMapper;
import com.youran.generate.pojo.po.chart.AggTablePO;
import com.youran.generate.pojo.po.chart.DetailListPO;
import com.youran.generate.pojo.qo.chart.MetaChartQO;
import com.youran.generate.pojo.vo.chart.AggTableVO;
import com.youran.generate.pojo.vo.chart.DetailListVO;
import com.youran.generate.pojo.vo.chart.MetaChartListVO;
import com.youran.generate.service.chart.AggTableService;
import com.youran.generate.service.chart.DetailListService;
import com.youran.generate.service.chart.MetaChartService;
import com.youran.generate.web.AbstractController;
import com.youran.generate.web.api.chart.MetaChartAPI;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * 【图表】控制器
 *
 * @author cbb
 * @date 2020/04/04
 */
@RestController
@RequestMapping(WebConst.API_PATH + "/metaChart")
public class MetaChartController extends AbstractController implements MetaChartAPI {


    @Autowired
    private MetaChartService metaChartService;
    @Autowired
    private AggTableService aggTableService;
    @Autowired
    private DetailListService detailListService;

    @Override
    @PostMapping("/aggTable")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AggTableVO> saveAggTable(@Valid @RequestBody AggTableAddDTO addDTO) throws Exception {
        AggTablePO po = aggTableService.save(addDTO);
        return ResponseEntity.created(new URI(apiPath + "/metaChart/aggTable/" + po.getChartId()))
            .body(MetaChartMapper.INSTANCE.toAggTableVO(po));
    }

    @Override
    @PutMapping("/aggTable")
    public ResponseEntity<AggTableVO> updateAggTable(@Valid @RequestBody AggTableUpdateDTO updateDTO) {
        AggTablePO po = aggTableService.update(updateDTO);
        return ResponseEntity.ok(MetaChartMapper.INSTANCE.toAggTableVO(po));
    }

    @Override
    @GetMapping("/aggTable/{chartId}")
    public ResponseEntity<AggTableVO> showAggTable(@PathVariable Integer chartId) {
        AggTableVO vo = aggTableService.show(chartId);
        return ResponseEntity.ok(vo);
    }

    @Override
    @PostMapping("/detailList")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DetailListVO> saveDetailList(@Valid @RequestBody DetailListAddDTO addDTO) throws Exception {
        DetailListPO po = detailListService.save(addDTO);
        return ResponseEntity.created(new URI(WebConst.API_PATH + "/metaChart/detailList/" + po.getChartId()))
            .body(MetaChartMapper.INSTANCE.toDetailListVO(po));
    }

    @Override
    @PutMapping("/detailList")
    public ResponseEntity<DetailListVO> updateDetailList(@Valid @RequestBody DetailListUpdateDTO updateDTO) {
        DetailListPO po = detailListService.update(updateDTO);
        return ResponseEntity.ok(MetaChartMapper.INSTANCE.toDetailListVO(po));
    }

    @Override
    @GetMapping("/detailList/{chartId}")
    public ResponseEntity<DetailListVO> showDetailList(@PathVariable Integer chartId) {
        DetailListVO vo = detailListService.show(chartId);
        return ResponseEntity.ok(vo);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<MetaChartListVO>> list(@Valid MetaChartQO metaChartQO) {
        List<MetaChartListVO> list = metaChartService.list(metaChartQO);
        return ResponseEntity.ok(list);
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Integer> deleteBatch(@RequestBody Integer[] id) {
        if(ArrayUtils.isEmpty(id)){
            throw new BusinessException(ErrorCode.PARAM_IS_NULL);
        }
        int count = metaChartService.delete(id);
        return ResponseEntity.ok(count);
    }


}
