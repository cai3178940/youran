package com.youran.generate.web.rest;

import com.youran.common.pojo.vo.ReplyVO;
import com.youran.generate.constant.GenerateConst;
import com.youran.generate.pojo.dto.MetaFieldAddDTO;
import com.youran.generate.pojo.qo.MetaFieldQO;
import com.youran.generate.pojo.dto.MetaFieldUpdateDTO;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.vo.MetaFieldListVO;
import com.youran.generate.pojo.vo.MetaFieldShowVO;
import com.youran.generate.service.MetaFieldService;
import com.youran.generate.web.api.MetaFieldAPI;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Title:元数据字段控制器
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 13:42
 */
@RestController
@RequestMapping("/meta_field")
public class MetaFieldController implements MetaFieldAPI {

    @Autowired
    private MetaFieldService metaFieldService;

    @Override
    @PostMapping(value = "/save")
    public ReplyVO<Integer> save(@Valid @RequestBody MetaFieldAddDTO metaFieldAddDTO) {
        if(metaFieldAddDTO.getDefaultValue()==null){
            metaFieldAddDTO.setDefaultValue(GenerateConst.METAFIELD_NULL_VALUE);
        }
        MetaFieldPO metaFieldPO = metaFieldService.save(metaFieldAddDTO);
        ReplyVO<Integer> result = ReplyVO.success();
        result.setData(metaFieldPO.getFieldId());
        return result;
    }

    @Override
    @PutMapping(value = "/update")
    public ReplyVO<Void> update(@Valid @RequestBody MetaFieldUpdateDTO metaFieldUpdateDTO) {
        if(metaFieldUpdateDTO.getDefaultValue()==null){
            metaFieldUpdateDTO.setDefaultValue(GenerateConst.METAFIELD_NULL_VALUE);
        }
        metaFieldService.update(metaFieldUpdateDTO);
        ReplyVO<Void> result = ReplyVO.success();
        return result;
    }

    @Override
    @GetMapping(value = "/list")
    public ReplyVO<List<MetaFieldListVO>> list(@Valid MetaFieldQO metaFieldQO) {
        List<MetaFieldListVO> list = metaFieldService.list(metaFieldQO);
        ReplyVO<List<MetaFieldListVO>> result = ReplyVO.success();
        result.setData(list);
        return result;
    }

    @Override
    @GetMapping(value = "/{fieldId}")
    public ReplyVO<MetaFieldShowVO> show(@PathVariable Integer fieldId) {
        MetaFieldShowVO metaFieldShowVO = metaFieldService.show(fieldId);
        ReplyVO<MetaFieldShowVO> result = ReplyVO.success();
        result.setData(metaFieldShowVO);
        return result;
    }

    @Override
    @DeleteMapping(value = "/{fieldId}")
    public ReplyVO<Integer> delete(@PathVariable Integer fieldId) {
        int count = metaFieldService.delete(fieldId);
        ReplyVO<Integer> result = ReplyVO.success();
        result.setData(count);
        return result;
    }


    @Override
    @PutMapping(value = "deleteBatch")
    public ReplyVO<Integer> deleteBatch(@RequestBody Integer[] fieldId) {
        if(ArrayUtils.isEmpty(fieldId)){
            return ReplyVO.fail("参数为空");
        }
        int count = metaFieldService.delete(fieldId);
        ReplyVO<Integer> result = ReplyVO.success();
        result.setData(count);
        return result;
    }
}
