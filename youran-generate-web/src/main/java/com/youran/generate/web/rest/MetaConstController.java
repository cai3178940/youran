package com.youran.generate.web.rest;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.pojo.vo.PageVO;
import com.youran.generate.constant.WebConst;
import com.youran.generate.pojo.dto.MetaConstAddDTO;
import com.youran.generate.pojo.dto.MetaConstUpdateDTO;
import com.youran.generate.pojo.mapper.MetaConstMapper;
import com.youran.generate.pojo.po.MetaConstPO;
import com.youran.generate.pojo.qo.MetaConstQO;
import com.youran.generate.pojo.vo.MetaConstListVO;
import com.youran.generate.pojo.vo.MetaConstShowVO;
import com.youran.generate.service.MetaConstService;
import com.youran.generate.web.AbstractController;
import com.youran.generate.web.api.MetaConstAPI;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * 【常量】控制器
 * <p> 常量增删改查
 *
 * @author: cbb
 * @date: 2017/5/12
 */
@RestController
@RequestMapping(WebConst.API_PATH + "/meta_const")
public class MetaConstController extends AbstractController implements MetaConstAPI {

    @Autowired
    private MetaConstService metaConstService;

    @Override
    @PostMapping(value = "/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MetaConstShowVO> save(@Valid @RequestBody MetaConstAddDTO metaConstAddDTO) throws Exception {
        MetaConstPO metaConstPO = metaConstService.save(metaConstAddDTO);
        return ResponseEntity.created(new URI(apiPath + "/meta_const/" + metaConstPO.getConstId()))
            .body(MetaConstMapper.INSTANCE.toShowVO(metaConstPO));
    }

    @Override
    @PutMapping(value = "/update")
    public ResponseEntity<MetaConstShowVO> update(@Valid @RequestBody MetaConstUpdateDTO metaConstUpdateDTO) {
        MetaConstPO metaConstPO = metaConstService.update(metaConstUpdateDTO);
        return ResponseEntity.ok(MetaConstMapper.INSTANCE.toShowVO(metaConstPO));
    }

    @Override
    @GetMapping(value = "/list")
    public ResponseEntity<List<MetaConstListVO>> list(@Valid MetaConstQO metaConstQO) {
        List<MetaConstListVO> list = metaConstService.list(metaConstQO);
        return ResponseEntity.ok(list);
    }

    @Override
    @GetMapping(value = "/{constId}")
    public ResponseEntity<MetaConstShowVO> show(@PathVariable Integer constId) {
        MetaConstShowVO metaConstShowVO = metaConstService.show(constId);
        return ResponseEntity.ok(metaConstShowVO);
    }

    @Override
    @DeleteMapping(value = "/{constId}")
    public ResponseEntity<Integer> delete(@PathVariable Integer constId) {
        int count = metaConstService.delete(constId);
        return ResponseEntity.ok(count);
    }

    @Override
    @PutMapping(value = "delete_batch")
    public ResponseEntity<Integer> deleteBatch(@RequestBody Integer[] constId) {
        if (ArrayUtils.isEmpty(constId)) {
            throw new BusinessException(ErrorCode.PARAM_IS_NULL);
        }
        int count = metaConstService.delete(constId);
        return ResponseEntity.ok(count);
    }
}
