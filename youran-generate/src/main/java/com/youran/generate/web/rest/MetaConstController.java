package com.youran.generate.web.rest;

import com.youran.common.pojo.vo.PageVO;
import com.youran.common.pojo.vo.ReplyVO;
import com.youran.generate.constant.GenerateConst;
import com.youran.generate.pojo.dto.MetaConstAddDTO;
import com.youran.generate.pojo.mapper.MetaConstMapper;
import com.youran.generate.pojo.qo.MetaConstQO;
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
 * <p>Title:【常量】控制器</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/12
 */
@RestController
@RequestMapping(GenerateConst.API_PATH +"/meta_const")
public class MetaConstController implements MetaConstAPI {

    @Autowired
    private MetaConstService metaConstService;

    @Override
    @PostMapping(value = "/save")
    public ReplyVO<MetaConstShowVO> save(@Valid @RequestBody MetaConstAddDTO metaConstAddDTO) {
        MetaConstPO metaConstPO = metaConstService.save(metaConstAddDTO);
        return ReplyVO.success().data(MetaConstMapper.INSTANCE.toShowVO(metaConstPO));
    }

    @Override
    @PutMapping(value = "/update")
    public ReplyVO<MetaConstShowVO> update(@Valid @RequestBody MetaConstUpdateDTO metaConstUpdateDTO) {
        MetaConstPO metaConstPO = metaConstService.update(metaConstUpdateDTO);
        return ReplyVO.success().data(MetaConstMapper.INSTANCE.toShowVO(metaConstPO));
    }

    @Override
    @GetMapping(value = "/list")
    public ReplyVO<PageVO<MetaConstListVO>> list(@Valid MetaConstQO metaConstQO) {
        PageVO<MetaConstListVO> page = metaConstService.list(metaConstQO);
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
