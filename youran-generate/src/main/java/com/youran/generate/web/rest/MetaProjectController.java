package com.youran.generate.web.rest;

import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.GenerateConst;
import com.youran.generate.pojo.dto.MetaProjectAddDTO;
import com.youran.generate.pojo.dto.MetaProjectUpdateDTO;
import com.youran.generate.pojo.mapper.MetaProjectMapper;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.qo.MetaProjectQO;
import com.youran.generate.pojo.vo.MetaProjectListVO;
import com.youran.generate.pojo.vo.MetaProjectShowVO;
import com.youran.generate.service.MetaProjectService;
import com.youran.generate.web.api.MetaProjectAPI;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>Title:【项目】控制器</p>
 * <p>Description:</p>
 * @author: cbb
 * Create Time:2017/5/24
 */
@RestController
@RequestMapping(GenerateConst.API_PATH +"/meta_project")
public class MetaProjectController implements MetaProjectAPI {

    @Autowired
    private MetaProjectService metaProjectService;

    @Override
    @PostMapping(value = "/save")
    public MetaProjectShowVO save(@Valid @RequestBody MetaProjectAddDTO metaProjectAddDTO) {
        MetaProjectPO metaProjectPO = metaProjectService.save(metaProjectAddDTO);
        return MetaProjectMapper.INSTANCE.toShowVO(metaProjectPO);
    }

    @Override
    @PutMapping(value = "/update")
    public MetaProjectShowVO update(@Valid @RequestBody MetaProjectUpdateDTO metaProjectUpdateDTO) {
        MetaProjectPO metaProjectPO = metaProjectService.update(metaProjectUpdateDTO);
        return MetaProjectMapper.INSTANCE.toShowVO(metaProjectPO);
    }

    @Override
    @GetMapping(value = "/list")
    public List<MetaProjectListVO> list(@Valid MetaProjectQO metaProjectQO) {
        List<MetaProjectListVO> list = metaProjectService.list(metaProjectQO);
        return list;
    }

    @Override
    @GetMapping(value = "/{projectId}")
    public MetaProjectShowVO show(@PathVariable Integer projectId) {
        MetaProjectShowVO metaProjectShowVO = metaProjectService.show(projectId);
        return metaProjectShowVO;
    }

    @Override
    @DeleteMapping(value = "/{projectId}")
    public Integer delete(@PathVariable Integer projectId) {
        int count = metaProjectService.delete(projectId);
        return count;
    }

    @Override
    @PutMapping(value = "deleteBatch")
    public Integer deleteBatch(@RequestBody Integer[] projectId) {
        if(ArrayUtils.isEmpty(projectId)){
            throw new BusinessException("参数为空");
        }
        int count = metaProjectService.delete(projectId);
        return count;
    }
}
