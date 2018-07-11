package com.youran.generate.web.rest;

import com.youran.common.pojo.vo.ReplyVO;
import com.youran.generate.constant.GenerateConst;
import com.youran.generate.pojo.dto.MetaIndexAddDTO;
import com.youran.generate.pojo.dto.MetaIndexUpdateDTO;
import com.youran.generate.pojo.po.MetaIndexPO;
import com.youran.generate.pojo.qo.MetaIndexQO;
import com.youran.generate.pojo.vo.MetaIndexListVO;
import com.youran.generate.pojo.vo.MetaIndexShowVO;
import com.youran.generate.service.MetaIndexService;
import com.youran.generate.web.api.MetaIndexAPI;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Title:【索引】控制器
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 13:42
 */
@RestController
@RequestMapping(GenerateConst.GENERATE_ROOT_PATH +"/meta_index")
public class MetaIndexController implements MetaIndexAPI {

    @Autowired
    private MetaIndexService metaIndexService;

    @Override
    @PostMapping(value = "/save")
    public ReplyVO<Integer> save(@Valid @RequestBody MetaIndexAddDTO metaIndexAddDTO) {
        MetaIndexPO metaIndex = metaIndexService.save(metaIndexAddDTO);
        ReplyVO<Integer> result = ReplyVO.success();
        result.setData(metaIndex.getIndexId());
        return result;
    }

    @Override
    @PutMapping(value = "/update")
    public ReplyVO<Void> update(@Valid @RequestBody MetaIndexUpdateDTO metaIndexUpdateDTO) {
        metaIndexService.update(metaIndexUpdateDTO);
        ReplyVO<Void> result = ReplyVO.success();
        return result;
    }

    @Override
    @GetMapping(value = "/list")
    public ReplyVO<List<MetaIndexListVO>> list(@Valid MetaIndexQO metaIndexQO) {
        List<MetaIndexListVO> list = metaIndexService.list(metaIndexQO);
        ReplyVO<List<MetaIndexListVO>> result = ReplyVO.success();
        result.setData(list);
        return result;
    }

    @Override
    @GetMapping(value = "/{indexId}")
    public ReplyVO<MetaIndexShowVO> show(@PathVariable Integer indexId) {
        MetaIndexShowVO metaIndexShowVO = metaIndexService.show(indexId);
        ReplyVO<MetaIndexShowVO> result = ReplyVO.success();
        result.setData(metaIndexShowVO);
        return result;
    }

    @Override
    @DeleteMapping(value = "/{indexId}")
    public ReplyVO<Integer> delete(@PathVariable Integer indexId) {
        int count = metaIndexService.delete(indexId);
        ReplyVO<Integer> result = ReplyVO.success();
        result.setData(count);
        return result;
    }

    @Override
    @PutMapping(value = "deleteBatch")
    public ReplyVO<Integer> deleteBatch(@RequestBody Integer[] indexId) {
        if(ArrayUtils.isEmpty(indexId)){
            return ReplyVO.fail("参数为空");
        }
        int count = metaIndexService.delete(indexId);
        ReplyVO<Integer> result = ReplyVO.success();
        result.setData(count);
        return result;
    }

    @Override
    @PutMapping(value = "/{indexId}/removeField")
    public ReplyVO<Integer> removeField(@PathVariable Integer indexId, @RequestBody List<Integer> fieldIds){
        int count = metaIndexService.removeField(indexId,fieldIds);
        ReplyVO<Integer> result = ReplyVO.success();
        result.setData(count);
        return result;
    }
}
