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
 * <p>Title: 【多对多级联扩展】控制器</p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/09/21
 */
@RestController
@RequestMapping(WebConst.API_PATH + "/metaMtmCascadeExt")
public class MetaMtmCascadeExtController extends AbstractController implements MetaMtmCascadeExtAPI {

    @Autowired
    private MetaMtmCascadeExtService metaMtmCascadeExtService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MetaMtmCascadeExtShowVO> save(@Valid @RequestBody MetaMtmCascadeExtAddDTO metaMtmCascadeExtAddDTO) throws Exception {
        MetaMtmCascadeExtPO metaMtmCascadeExt = metaMtmCascadeExtService.save(metaMtmCascadeExtAddDTO);
        return ResponseEntity.created(new URI(apiPath +"/metaMtmCascadeExt/" + metaMtmCascadeExt.getCascadeMtmExtId()))
            .body(MetaMtmCascadeExtMapper.INSTANCE.toShowVO(metaMtmCascadeExt));
    }

    @Override
    @PutMapping
    public ResponseEntity<MetaMtmCascadeExtShowVO> update(@Valid @RequestBody MetaMtmCascadeExtUpdateDTO metaMtmCascadeExtUpdateDTO) {
        MetaMtmCascadeExtPO metaMtmCascadeExt = metaMtmCascadeExtService.update(metaMtmCascadeExtUpdateDTO);
        return ResponseEntity.ok(MetaMtmCascadeExtMapper.INSTANCE.toShowVO(metaMtmCascadeExt));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<MetaMtmCascadeExtListVO>> list(@Valid MetaMtmCascadeExtQO metaMtmCascadeExtQO) {
        List<MetaMtmCascadeExtListVO> list = metaMtmCascadeExtService.list(metaMtmCascadeExtQO);
        return ResponseEntity.ok(list);
    }

    @Override
    @GetMapping(value = "/{cascadeMtmExtId}")
    public ResponseEntity<MetaMtmCascadeExtShowVO> show(@PathVariable Integer cascadeMtmExtId) {
        MetaMtmCascadeExtShowVO metaMtmCascadeExtShowVO = metaMtmCascadeExtService.show(cascadeMtmExtId);
        return ResponseEntity.ok(metaMtmCascadeExtShowVO);
    }

    @Override
    @DeleteMapping(value = "/{cascadeMtmExtId}")
    public ResponseEntity<Integer> delete(@PathVariable Integer cascadeMtmExtId) {
        int count = metaMtmCascadeExtService.delete(cascadeMtmExtId);
        return ResponseEntity.ok(count);
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Integer> deleteBatch(@RequestBody Integer[] id) {
        if(ArrayUtils.isEmpty(id)){
            throw new BusinessException(ErrorCode.PARAM_IS_NULL);
        }
        int count = metaMtmCascadeExtService.delete(id);
        return ResponseEntity.ok(count);
    }


}


