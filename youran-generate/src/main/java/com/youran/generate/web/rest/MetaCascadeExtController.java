package com.youran.generate.web.rest;

import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.GenerateConst;
import com.youran.generate.pojo.dto.MetaCascadeExtAddDTO;
import com.youran.generate.pojo.dto.MetaCascadeExtUpdateDTO;
import com.youran.generate.pojo.mapper.MetaCascadeExtMapper;
import com.youran.generate.pojo.po.MetaCascadeExtPO;
import com.youran.generate.pojo.qo.MetaCascadeExtQO;
import com.youran.generate.pojo.vo.MetaCascadeExtListVO;
import com.youran.generate.pojo.vo.MetaCascadeExtShowVO;
import com.youran.generate.service.MetaCascadeExtService;
import com.youran.generate.web.AbstractController;
import com.youran.generate.web.api.MetaCascadeExtAPI;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * <p>Title:【级联扩展】控制器</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/12
 */
@RestController
@RequestMapping(GenerateConst.API_PATH +"/meta_cascade_ext")
public class MetaCascadeExtController extends AbstractController implements MetaCascadeExtAPI {

    @Autowired
    private MetaCascadeExtService metaCascadeExtService;

    @Override
    @PostMapping(value = "/save")
    public ResponseEntity<MetaCascadeExtShowVO> save(@Valid @RequestBody MetaCascadeExtAddDTO metaCascadeExtAddDTO) throws Exception {
        MetaCascadeExtPO metaCascadeExtPO = metaCascadeExtService.save(metaCascadeExtAddDTO);
        return ResponseEntity.created(new URI(apiPath +"/meta_cascade_ext/" + metaCascadeExtPO.getCascadeExtId()))
            .body(MetaCascadeExtMapper.INSTANCE.toShowVO(metaCascadeExtPO));
    }

    @Override
    @PutMapping(value = "/update")
    public ResponseEntity<MetaCascadeExtShowVO> update(@Valid @RequestBody MetaCascadeExtUpdateDTO metaCascadeExtUpdateDTO) {
        MetaCascadeExtPO metaCascadeExtPO = metaCascadeExtService.update(metaCascadeExtUpdateDTO);
        return ResponseEntity.ok(MetaCascadeExtMapper.INSTANCE.toShowVO(metaCascadeExtPO));
    }

    @Override
    @GetMapping(value = "/list")
    public ResponseEntity<List<MetaCascadeExtListVO>> list(@Valid MetaCascadeExtQO metaCascadeExtQO) {
        List<MetaCascadeExtListVO> list = metaCascadeExtService.list(metaCascadeExtQO);
        return ResponseEntity.ok(list);
    }

    @Override
    @GetMapping(value = "/{cascadeExtId}")
    public ResponseEntity<MetaCascadeExtShowVO> show(@PathVariable Integer cascadeExtId) {
        MetaCascadeExtShowVO metaCascadeExtShowVO = metaCascadeExtService.show(cascadeExtId);
        return ResponseEntity.ok(metaCascadeExtShowVO);
    }

    @Override
    @DeleteMapping(value = "/{cascadeExtId}")
    public ResponseEntity<Integer> delete(@PathVariable Integer cascadeExtId) {
        int count = metaCascadeExtService.delete(cascadeExtId);
        return ResponseEntity.ok(count);
    }

    @Override
    @PutMapping(value = "deleteBatch")
    public ResponseEntity<Integer> deleteBatch(@RequestBody Integer[] cascadeExtId) {
        if(ArrayUtils.isEmpty(cascadeExtId)){
            throw new BusinessException("参数为空");
        }
        int count = metaCascadeExtService.delete(cascadeExtId);
        return ResponseEntity.ok(count);
    }
}
