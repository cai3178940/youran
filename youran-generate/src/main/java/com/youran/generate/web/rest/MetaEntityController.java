package com.youran.generate.web.rest;

import com.youran.common.constant.BoolConst;
import com.youran.common.pojo.vo.PageVO;
import com.youran.common.pojo.vo.ReplyVO;
import com.youran.generate.constant.GenerateConst;
import com.youran.generate.pojo.dto.MetaEntityAddDTO;
import com.youran.generate.pojo.dto.MetaEntityUpdateDTO;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.qo.MetaEntityQO;
import com.youran.generate.pojo.vo.MetaEntityListVO;
import com.youran.generate.pojo.vo.MetaEntityShowVO;
import com.youran.generate.service.MetaEntityService;
import com.youran.generate.web.api.MetaEntityAPI;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Title:【实体】控制器
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 13:42
 */
@RestController
@RequestMapping(GenerateConst.GENERATE_ROOT_PATH +"/meta_entity")
public class MetaEntityController implements MetaEntityAPI {

    @Autowired
    private MetaEntityService metaEntityService;

    @Override
    @PostMapping(value = "/save")
    public ReplyVO<Integer> save(@Valid @RequestBody MetaEntityAddDTO metaEntityAddDTO) {
        if(metaEntityAddDTO.getCommonCall()==null){
            metaEntityAddDTO.setCommonCall(BoolConst.FALSE);
        }
        MetaEntityPO metaEntityPO = metaEntityService.save(metaEntityAddDTO);
        ReplyVO<Integer> result = ReplyVO.success();
        result.setData(metaEntityPO.getEntityId());
        return result;
    }

    @Override
    @PutMapping(value = "/update")
    public ReplyVO<Void> update(@Valid @RequestBody MetaEntityUpdateDTO metaEntityUpdateDTO) {
        if(metaEntityUpdateDTO.getCommonCall()==null){
            metaEntityUpdateDTO.setCommonCall(BoolConst.FALSE);
        }
        metaEntityService.update(metaEntityUpdateDTO);
        ReplyVO<Void> result = ReplyVO.success();
        return result;
    }

    @Override
    @GetMapping(value = "/list")
    public ReplyVO<PageVO<MetaEntityListVO>> list(@Valid MetaEntityQO metaEntityQO) {
        PageVO<MetaEntityListVO> page = metaEntityService.list(metaEntityQO);
        ReplyVO<PageVO<MetaEntityListVO>> result = ReplyVO.success();
        result.setData(page);
        return result;
    }

    @Override
    @GetMapping(value = "/{entityId}")
    public ReplyVO<MetaEntityShowVO> show(@PathVariable Integer entityId) {
        MetaEntityShowVO metaEntityShowVO = metaEntityService.show(entityId);
        ReplyVO<MetaEntityShowVO> result = ReplyVO.success();
        result.setData(metaEntityShowVO);
        return result;
    }

    @Override
    @DeleteMapping(value = "/{entityId}")
    public ReplyVO<Integer> delete(@PathVariable Integer entityId) {
        int count = metaEntityService.delete(entityId);
        ReplyVO<Integer> result = ReplyVO.success();
        result.setData(count);
        return result;
    }

    @Override
    @PutMapping(value = "deleteBatch")
    public ReplyVO<Integer> deleteBatch(@RequestBody Integer[] entityId) {
        if(ArrayUtils.isEmpty(entityId)){
            return ReplyVO.fail("参数为空");
        }
        int count = metaEntityService.delete(entityId);
        ReplyVO<Integer> result = ReplyVO.success();
        result.setData(count);
        return result;
    }
}
