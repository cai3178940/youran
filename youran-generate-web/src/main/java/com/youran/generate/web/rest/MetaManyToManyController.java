package com.youran.generate.web.rest;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.WebConst;
import com.youran.generate.pojo.dto.MetaManyToManyAddDTO;
import com.youran.generate.pojo.dto.MetaManyToManyUpdateDTO;
import com.youran.generate.pojo.mapper.MetaManyToManyMapper;
import com.youran.generate.pojo.po.MetaManyToManyPO;
import com.youran.generate.pojo.qo.MetaManyToManyQO;
import com.youran.generate.pojo.vo.MetaManyToManyListVO;
import com.youran.generate.pojo.vo.MetaManyToManyShowVO;
import com.youran.generate.service.MetaManyToManyService;
import com.youran.generate.web.AbstractController;
import com.youran.generate.web.api.MetaManyToManyAPI;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * 【多对多关联】控制器
 * <p> 多对多关系增删改查
 *
 * @author: cbb
 * @date: 2017/5/12
 */
@RestController
@RequestMapping(WebConst.API_PATH + "/meta_mtm")
public class MetaManyToManyController extends AbstractController implements MetaManyToManyAPI {

    @Autowired
    private MetaManyToManyService metaManyToManyService;

    @Override
    @PostMapping(value = "/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MetaManyToManyShowVO> save(@Valid @RequestBody MetaManyToManyAddDTO metaManyToManyAddDTO) throws Exception {
        MetaManyToManyPO metaManyToManyPO = metaManyToManyService.save(metaManyToManyAddDTO);
        return ResponseEntity.created(new URI(apiPath + "/meta_mtm/" + metaManyToManyPO.getMtmId()))
            .body(MetaManyToManyMapper.INSTANCE.toShowVO(metaManyToManyPO));
    }

    @Override
    @PutMapping(value = "/update")
    public ResponseEntity<MetaManyToManyShowVO> update(@Valid @RequestBody MetaManyToManyUpdateDTO metaManyToManyUpdateDTO) {
        MetaManyToManyPO metaManyToManyPO = metaManyToManyService.update(metaManyToManyUpdateDTO);
        return ResponseEntity.ok(MetaManyToManyMapper.INSTANCE.toShowVO(metaManyToManyPO));
    }

    @Override
    @GetMapping(value = "/list")
    public ResponseEntity<List<MetaManyToManyListVO>> list(@Valid MetaManyToManyQO metaManyToManyQO) {
        List<MetaManyToManyListVO> list = metaManyToManyService.list(metaManyToManyQO);
        return ResponseEntity.ok(list);
    }

    @Override
    @GetMapping(value = "/{mtmId}")
    public ResponseEntity<MetaManyToManyShowVO> show(@PathVariable Integer mtmId) {
        MetaManyToManyShowVO metaManyToManyShowVO = metaManyToManyService.show(mtmId);
        return ResponseEntity.ok(metaManyToManyShowVO);
    }

    @Override
    @DeleteMapping(value = "/{mtmId}")
    public ResponseEntity<Integer> delete(@PathVariable Integer mtmId) {
        int count = metaManyToManyService.delete(mtmId);
        return ResponseEntity.ok(count);
    }

    @Override
    @PutMapping(value = "delete_batch")
    public ResponseEntity<Integer> deleteBatch(@RequestBody Integer[] mtmId) {
        if (ArrayUtils.isEmpty(mtmId)) {
            throw new BusinessException(ErrorCode.PARAM_IS_NULL);
        }
        int count = metaManyToManyService.delete(mtmId);
        return ResponseEntity.ok(count);
    }
}
