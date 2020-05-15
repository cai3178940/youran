
package com.youran.generate.web.rest.chart;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.WebConst;
import com.youran.generate.pojo.dto.chart.source.MetaChartSourceAddDTO;
import com.youran.generate.pojo.dto.chart.source.MetaChartSourceUpdateDTO;
import com.youran.generate.pojo.mapper.chart.MetaChartSourceMapper;
import com.youran.generate.pojo.po.chart.source.MetaChartSourcePO;
import com.youran.generate.pojo.vo.chart.source.MetaChartSourceShowVO;
import com.youran.generate.service.chart.source.MetaChartSourceService;
import com.youran.generate.web.AbstractController;
import com.youran.generate.web.api.chart.MetaChartSourceAPI;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

/**
 * 【图表数据源】控制器
 *
 * @author cbb
 * @date 2020/04/04
 */
@RestController
@RequestMapping(WebConst.API_PATH + "/meta_chart_source")
public class MetaChartSourceController extends AbstractController implements MetaChartSourceAPI {

    @Autowired
    private MetaChartSourceService metaChartSourceService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MetaChartSourceShowVO> save(@Valid @RequestBody MetaChartSourceAddDTO metaChartSourceAddDTO) throws Exception {
        MetaChartSourcePO metaChartSource = metaChartSourceService.save(metaChartSourceAddDTO);
        return ResponseEntity.created(new URI(apiPath + "/meta_chart_source/" + metaChartSource.getSourceId()))
            .body(MetaChartSourceMapper.INSTANCE.toShowVO(metaChartSource));
    }

    @Override
    @PutMapping
    public ResponseEntity<MetaChartSourceShowVO> update(@Valid @RequestBody MetaChartSourceUpdateDTO metaChartSourceUpdateDTO) {
        MetaChartSourcePO metaChartSource = metaChartSourceService.update(metaChartSourceUpdateDTO);
        return ResponseEntity.ok(MetaChartSourceMapper.INSTANCE.toShowVO(metaChartSource));
    }

    @Override
    @GetMapping(value = "/{sourceId}")
    public ResponseEntity<MetaChartSourceShowVO> show(@PathVariable Integer sourceId) {
        MetaChartSourceShowVO metaChartSourceShowVO = metaChartSourceService.show(sourceId);
        return ResponseEntity.ok(metaChartSourceShowVO);
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Integer> deleteBatch(@RequestBody Integer[] id) {
        if(ArrayUtils.isEmpty(id)){
            throw new BusinessException(ErrorCode.PARAM_IS_NULL);
        }
        int count = metaChartSourceService.delete(id);
        return ResponseEntity.ok(count);
    }

}


