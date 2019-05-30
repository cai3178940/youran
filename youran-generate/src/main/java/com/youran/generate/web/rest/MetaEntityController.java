package com.youran.generate.web.rest;

import com.youran.common.constant.BoolConst;
import com.youran.common.exception.BusinessException;
import com.youran.common.pojo.vo.PageVO;
import com.youran.generate.constant.GenerateConst;
import com.youran.generate.pojo.dto.MetaEntityAddDTO;
import com.youran.generate.pojo.dto.MetaEntityUpdateDTO;
import com.youran.generate.pojo.mapper.MetaEntityMapper;
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
 * <p>Title:【实体】控制器</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/12
 */
@RestController
@RequestMapping(GenerateConst.API_PATH +"/meta_entity")
public class MetaEntityController implements MetaEntityAPI {

    @Autowired
    private MetaEntityService metaEntityService;

    @Override
    @PostMapping(value = "/save")
    public MetaEntityShowVO save(@Valid @RequestBody MetaEntityAddDTO metaEntityAddDTO) {
        if(metaEntityAddDTO.getCommonCall()==null){
            metaEntityAddDTO.setCommonCall(BoolConst.FALSE);
        }
        MetaEntityPO metaEntityPO = metaEntityService.save(metaEntityAddDTO);
        return MetaEntityMapper.INSTANCE.toShowVO(metaEntityPO);
    }

    @Override
    @PutMapping(value = "/update")
    public MetaEntityShowVO update(@Valid @RequestBody MetaEntityUpdateDTO metaEntityUpdateDTO) {
        if(metaEntityUpdateDTO.getCommonCall()==null){
            metaEntityUpdateDTO.setCommonCall(BoolConst.FALSE);
        }
        MetaEntityPO metaEntityPO = metaEntityService.update(metaEntityUpdateDTO);
        return MetaEntityMapper.INSTANCE.toShowVO(metaEntityPO);
    }

    @Override
    @GetMapping(value = "/list")
    public PageVO<MetaEntityListVO> list(@Valid MetaEntityQO metaEntityQO) {
        PageVO<MetaEntityListVO> page = metaEntityService.list(metaEntityQO);
        return page;
    }

    @Override
    @GetMapping(value = "/{entityId}")
    public MetaEntityShowVO show(@PathVariable Integer entityId) {
        MetaEntityShowVO metaEntityShowVO = metaEntityService.show(entityId);
        return metaEntityShowVO;
    }

    @Override
    @DeleteMapping(value = "/{entityId}")
    public Integer delete(@PathVariable Integer entityId) {
        int count = metaEntityService.delete(entityId);
        return count;
    }

    @Override
    @PutMapping(value = "deleteBatch")
    public Integer deleteBatch(@RequestBody Integer[] entityId) {
        if(ArrayUtils.isEmpty(entityId)){
            throw new BusinessException("参数为空");
        }
        int count = metaEntityService.delete(entityId);
        return count;
    }
}
