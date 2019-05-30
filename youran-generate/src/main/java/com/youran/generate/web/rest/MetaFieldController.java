package com.youran.generate.web.rest;

import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.GenerateConst;
import com.youran.generate.pojo.dto.MetaFieldAddDTO;
import com.youran.generate.pojo.dto.MetaFieldUpdateDTO;
import com.youran.generate.pojo.mapper.MetaFieldMapper;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.qo.MetaFieldQO;
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
 * <p>Title:【字段】控制器</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/12
 */
@RestController
@RequestMapping(GenerateConst.API_PATH +"/meta_field")
public class MetaFieldController implements MetaFieldAPI {

    @Autowired
    private MetaFieldService metaFieldService;

    @Override
    @PostMapping(value = "/save")
    public MetaFieldShowVO save(@Valid @RequestBody MetaFieldAddDTO metaFieldAddDTO) {
        if(metaFieldAddDTO.getDefaultValue()==null){
            metaFieldAddDTO.setDefaultValue(GenerateConst.METAFIELD_NULL_VALUE);
        }
        MetaFieldPO metaFieldPO = metaFieldService.save(metaFieldAddDTO);
        return MetaFieldMapper.INSTANCE.toShowVO(metaFieldPO);
    }

    @Override
    @PutMapping(value = "/update")
    public MetaFieldShowVO update(@Valid @RequestBody MetaFieldUpdateDTO metaFieldUpdateDTO) {
        if(metaFieldUpdateDTO.getDefaultValue()==null){
            metaFieldUpdateDTO.setDefaultValue(GenerateConst.METAFIELD_NULL_VALUE);
        }
        MetaFieldPO metaFieldPO = metaFieldService.update(metaFieldUpdateDTO);
        return MetaFieldMapper.INSTANCE.toShowVO(metaFieldPO);
    }

    @Override
    @GetMapping(value = "/list")
    public List<MetaFieldListVO> list(@Valid MetaFieldQO metaFieldQO) {
        List<MetaFieldListVO> list = metaFieldService.list(metaFieldQO);
        return list;
    }

    @Override
    @GetMapping(value = "/{fieldId}")
    public MetaFieldShowVO show(@PathVariable Integer fieldId) {
        MetaFieldShowVO metaFieldShowVO = metaFieldService.show(fieldId);
        return metaFieldShowVO;
    }

    @Override
    @DeleteMapping(value = "/{fieldId}")
    public Integer delete(@PathVariable Integer fieldId) {
        int count = metaFieldService.delete(fieldId);
        return count;
    }


    @Override
    @PutMapping(value = "deleteBatch")
    public Integer deleteBatch(@RequestBody Integer[] fieldId) {
        if(ArrayUtils.isEmpty(fieldId)){
            throw new BusinessException("参数为空");
        }
        int count = metaFieldService.delete(fieldId);
        return count;
    }
}
