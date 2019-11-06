package com.youran.generate.web.rest;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.WebConst;
import com.youran.generate.pojo.dto.MetaMtmCascadeExtAddDTO;
import com.youran.generate.pojo.dto.MetaMtmCascadeExtUpdateDTO;
import com.youran.generate.pojo.mapper.MetaMtmCascadeExtMapper;
import com.youran.generate.pojo.po.MetaMtmCascadeExtPO;
import com.youran.generate.pojo.qo.MetaMtmCascadeExtQO;
import com.youran.generate.pojo.vo.MetaMtmCascadeExtListVO;
import com.youran.generate.pojo.vo.MetaMtmCascadeExtShowVO;
import com.youran.generate.service.MetaMtmCascadeExtService;
import com.youran.generate.web.AbstractController;
import com.youran.generate.web.api.MetaMtmCascadeExtAPI;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * 【多对多级联扩展】控制器
 *
 * @author cbb
 * @date 2019/09/21
 */
@RestController
@RequestMapping(WebConst.API_PATH + "/meta_mtm_cascade_ext")
public class MetaMtmCascadeExtController extends AbstractController implements MetaMtmCascadeExtAPI {

    @Autowired
    private MetaMtmCascadeExtService metaMtmCascadeExtService;

    @Override
    @PostMapping(value = "/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MetaMtmCascadeExtShowVO> save(@Valid @RequestBody MetaMtmCascadeExtAddDTO metaMtmCascadeExtAddDTO) throws Exception {
        MetaMtmCascadeExtPO metaMtmCascadeExt = metaMtmCascadeExtService.save(metaMtmCascadeExtAddDTO);
        return ResponseEntity.created(new URI(apiPath + "/metaMtmCascadeExt/" + metaMtmCascadeExt.getMtmCascadeExtId()))
            .body(MetaMtmCascadeExtMapper.INSTANCE.toShowVO(metaMtmCascadeExt));
    }

    @Override
    @PutMapping(value = "/update")
    public ResponseEntity<MetaMtmCascadeExtShowVO> update(@Valid @RequestBody MetaMtmCascadeExtUpdateDTO metaMtmCascadeExtUpdateDTO) {
        MetaMtmCascadeExtPO metaMtmCascadeExt = metaMtmCascadeExtService.update(metaMtmCascadeExtUpdateDTO);
        return ResponseEntity.ok(MetaMtmCascadeExtMapper.INSTANCE.toShowVO(metaMtmCascadeExt));
    }

    @Override
    @GetMapping(value = "/list")
    public ResponseEntity<List<MetaMtmCascadeExtListVO>> list(@Valid MetaMtmCascadeExtQO metaMtmCascadeExtQO) {
        List<MetaMtmCascadeExtListVO> list = metaMtmCascadeExtService.list(metaMtmCascadeExtQO);
        return ResponseEntity.ok(list);
    }

    @Override
    @GetMapping(value = "/{mtmCascadeExtId}")
    public ResponseEntity<MetaMtmCascadeExtShowVO> show(@PathVariable Integer mtmCascadeExtId) {
        MetaMtmCascadeExtShowVO metaMtmCascadeExtShowVO = metaMtmCascadeExtService.show(mtmCascadeExtId);
        return ResponseEntity.ok(metaMtmCascadeExtShowVO);
    }

    @Override
    @DeleteMapping(value = "/{mtmCascadeExtId}")
    public ResponseEntity<Integer> delete(@PathVariable Integer mtmCascadeExtId) {
        int count = metaMtmCascadeExtService.delete(mtmCascadeExtId);
        return ResponseEntity.ok(count);
    }

    @Override
    @PutMapping(value = "deleteBatch")
    public ResponseEntity<Integer> deleteBatch(@RequestBody Integer[] id) {
        if (ArrayUtils.isEmpty(id)) {
            throw new BusinessException(ErrorCode.PARAM_IS_NULL);
        }
        int count = metaMtmCascadeExtService.delete(id);
        return ResponseEntity.ok(count);
    }


}


