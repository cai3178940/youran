package com.youran.generate.web.rest;

import com.youran.common.pojo.vo.ReplyVO;
import com.youran.generate.constant.GenerateConst;
import com.youran.generate.pojo.dto.MetaCascadeExtAddDTO;
import com.youran.generate.pojo.dto.MetaCascadeExtUpdateDTO;
import com.youran.generate.pojo.po.MetaCascadeExtPO;
import com.youran.generate.pojo.qo.MetaCascadeExtQO;
import com.youran.generate.pojo.vo.MetaCascadeExtListVO;
import com.youran.generate.pojo.vo.MetaCascadeExtShowVO;
import com.youran.generate.service.MetaCascadeExtService;
import com.youran.generate.web.api.MetaCascadeExtAPI;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Title: 级联扩展控制器
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 13:42
 */
@RestController
@RequestMapping(GenerateConst.GENERATE_ROOT_PATH +"/meta_cascade_ext")
public class MetaCascadeExtController implements MetaCascadeExtAPI {

    @Autowired
    private MetaCascadeExtService metaCascadeExtService;

    @Override
    @PostMapping(value = "/save")
    public ReplyVO<Integer> save(@Valid @RequestBody MetaCascadeExtAddDTO metaCascadeExtAddDTO) {
        MetaCascadeExtPO metaCascadeExtPO = metaCascadeExtService.save(metaCascadeExtAddDTO);
        ReplyVO<Integer> result = ReplyVO.success();
        result.setData(metaCascadeExtPO.getCascadeExtId());
        return result;
    }

    @Override
    @PutMapping(value = "/update")
    public ReplyVO<Void> update(@Valid @RequestBody MetaCascadeExtUpdateDTO metaCascadeExtUpdateDTO) {
        metaCascadeExtService.update(metaCascadeExtUpdateDTO);
        ReplyVO<Void> result = ReplyVO.success();
        return result;
    }

    @Override
    @GetMapping(value = "/list")
    public ReplyVO<List<MetaCascadeExtListVO>> list(@Valid MetaCascadeExtQO metaCascadeExtQO) {
        List<MetaCascadeExtListVO> list = metaCascadeExtService.list(metaCascadeExtQO);
        ReplyVO<List<MetaCascadeExtListVO>> result = ReplyVO.success();
        result.setData(list);
        return result;
    }

    @Override
    @GetMapping(value = "/{cascadeExtId}")
    public ReplyVO<MetaCascadeExtShowVO> show(@PathVariable Integer cascadeExtId) {
        MetaCascadeExtShowVO metaCascadeExtShowVO = metaCascadeExtService.show(cascadeExtId);
        ReplyVO<MetaCascadeExtShowVO> result = ReplyVO.success();
        result.setData(metaCascadeExtShowVO);
        return result;
    }

    @Override
    @DeleteMapping(value = "/{cascadeExtId}")
    public ReplyVO<Integer> delete(@PathVariable Integer cascadeExtId) {
        int count = metaCascadeExtService.delete(cascadeExtId);
        ReplyVO<Integer> result = ReplyVO.success();
        result.setData(count);
        return result;
    }

    @Override
    @PutMapping(value = "deleteBatch")
    public ReplyVO<Integer> deleteBatch(@RequestBody Integer[] cascadeExtId) {
        if(ArrayUtils.isEmpty(cascadeExtId)){
            return ReplyVO.fail("参数为空");
        }
        int count = metaCascadeExtService.delete(cascadeExtId);
        ReplyVO<Integer> result = ReplyVO.success();
        result.setData(count);
        return result;
    }
}
