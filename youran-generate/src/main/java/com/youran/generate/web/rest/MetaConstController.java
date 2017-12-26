package com.youran.generate.web.rest;

import com.youran.common.pojo.vo.PageVO;
import com.youran.common.pojo.vo.ReplyVO;
import com.youran.generate.constant.GenerateConst;
import com.youran.generate.pojo.dto.MetaConstAddDTO;
import com.youran.generate.pojo.dto.MetaConstQueryDTO;
import com.youran.generate.pojo.dto.MetaConstUpdateDTO;
import com.youran.generate.pojo.po.MetaConstPO;
import com.youran.generate.pojo.vo.MetaConstListVO;
import com.youran.generate.pojo.vo.MetaConstShowVO;
import com.youran.generate.service.MetaConstService;
import com.youran.generate.web.api.MetaConstAPI;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Title:元数据常量控制器
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 13:42
 */
@RestController
@RequestMapping(GenerateConst.GENERATE_ROOT_PATH + "/meta_const")
public class MetaConstController implements MetaConstAPI {

    @Autowired
    private MetaConstService metaConstService;

    @Override
    @PostMapping(value = "/save")
    public ReplyVO<Integer> save(@Valid @RequestBody MetaConstAddDTO metaConstAddDTO) {
        MetaConstPO metaConstPO = metaConstService.save(metaConstAddDTO);
        ReplyVO<Integer> result = ReplyVO.success();
        result.setData(metaConstPO.getConstId());
        return result;
    }

    @Override
    @PutMapping(value = "/update")
    public ReplyVO<Void> update(@Valid @RequestBody MetaConstUpdateDTO metaConstUpdateDTO) {
        metaConstService.update(metaConstUpdateDTO);
        ReplyVO<Void> result = ReplyVO.success();
        return result;
    }

    @Override
    @GetMapping(value = "/list")
    public ReplyVO<PageVO<MetaConstListVO>> list(@Valid MetaConstQueryDTO metaConstQueryDTO) {
        PageVO<MetaConstListVO> page = metaConstService.list(metaConstQueryDTO);
        ReplyVO<PageVO<MetaConstListVO>> result = ReplyVO.success();
        result.setData(page);
        return result;
    }

    @Override
    @GetMapping(value = "/{constId}")
    public ReplyVO<MetaConstShowVO> show(@PathVariable Integer constId) {
        MetaConstShowVO metaConstShowVO = metaConstService.show(constId);
        ReplyVO<MetaConstShowVO> result = ReplyVO.success();
        result.setData(metaConstShowVO);
        return result;
    }

    @Override
    @DeleteMapping(value = "/{constId}")
    public ReplyVO<Integer> delete(@PathVariable Integer constId) {
        int count = metaConstService.delete(constId);
        ReplyVO<Integer> result = ReplyVO.success();
        result.setData(count);
        return result;
    }

    @Override
    @PutMapping(value = "deleteBatch")
    public ReplyVO<Integer> deleteBatch(@RequestBody Integer[] constId) {
        if(ArrayUtils.isEmpty(constId)){
            return ReplyVO.fail("参数为空");
        }
        int count = metaConstService.delete(constId);
        ReplyVO<Integer> result = ReplyVO.success();
        result.setData(count);
        return result;
    }
}
