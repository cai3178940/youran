package com.youran.generate.web.rest;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.WebConst;
import com.youran.generate.pojo.dto.MetaConstDetailAddDTO;
import com.youran.generate.pojo.dto.MetaConstDetailUpdateDTO;
import com.youran.generate.pojo.mapper.MetaConstDetailMapper;
import com.youran.generate.pojo.po.MetaConstDetailPO;
import com.youran.generate.pojo.qo.MetaConstDetailQO;
import com.youran.generate.pojo.vo.MetaConstDetailListVO;
import com.youran.generate.pojo.vo.MetaConstDetailShowVO;
import com.youran.generate.service.MetaConstDetailService;
import com.youran.generate.web.AbstractController;
import com.youran.generate.web.api.MetaConstDetailAPI;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * <p>Title:【常量值】控制器</p>
 * <p>Description: 常量值增删改查</p>
 * @author: cbb
 * @date: 2017/5/12
 */
@RestController
@RequestMapping(WebConst.API_PATH +"/meta_const_detail")
public class MetaConstDetailController extends AbstractController implements MetaConstDetailAPI {

    @Autowired
    private MetaConstDetailService metaConstDetailService;

    @Override
    @PostMapping(value = "/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MetaConstDetailShowVO> save(@Valid @RequestBody MetaConstDetailAddDTO metaConstDetailAddDTO) throws Exception {
        MetaConstDetailPO metaConstDetailPO = metaConstDetailService.save(metaConstDetailAddDTO);
        return ResponseEntity.created(new URI(apiPath +"/meta_const_detail/" + metaConstDetailPO.getConstDetailId()))
            .body(MetaConstDetailMapper.INSTANCE.toShowVO(metaConstDetailPO));
    }

    @Override
    @PutMapping(value = "/update")
    public ResponseEntity<MetaConstDetailShowVO> update(@Valid @RequestBody MetaConstDetailUpdateDTO metaConstDetailUpdateDTO) {
        MetaConstDetailPO metaConstDetailPO = metaConstDetailService.update(metaConstDetailUpdateDTO);
        return ResponseEntity.ok(MetaConstDetailMapper.INSTANCE.toShowVO(metaConstDetailPO));
    }

    @Override
    @GetMapping(value = "/list")
    public ResponseEntity<List<MetaConstDetailListVO>> list(@Valid MetaConstDetailQO metaConstDetailQO) {
        List<MetaConstDetailListVO> list = metaConstDetailService.list(metaConstDetailQO);
        return ResponseEntity.ok(list);
    }

    @Override
    @GetMapping(value = "/{constDetailId}")
    public ResponseEntity<MetaConstDetailShowVO> show(@PathVariable Integer constDetailId) {
        MetaConstDetailShowVO metaConstDetailShowVO = metaConstDetailService.show(constDetailId);
        return ResponseEntity.ok(metaConstDetailShowVO);
    }

    @Override
    @DeleteMapping(value = "/{constDetailId}")
    public ResponseEntity<Integer> delete(@PathVariable Integer constDetailId) {
        int count = metaConstDetailService.delete(constDetailId);
        return ResponseEntity.ok(count);
    }

    @Override
    @PutMapping(value = "deleteBatch")
    public ResponseEntity<Integer> deleteBatch(@RequestBody Integer[] constDetailId) {
        if(ArrayUtils.isEmpty(constDetailId)){
            throw new BusinessException(ErrorCode.PARAM_IS_NULL);
        }
        int count = metaConstDetailService.delete(constDetailId);
        return ResponseEntity.ok(count);
    }
}
