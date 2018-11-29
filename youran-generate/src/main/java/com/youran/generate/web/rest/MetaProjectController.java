package com.youran.generate.web.rest;

import com.youran.common.pojo.vo.ReplyVO;
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
 * Title:【项目】控制器
 * Description:
 * Author: cbb
 * Create Time:2017/5/24
 */
@RestController
@RequestMapping(GenerateConst.API_PATH +"/meta_project")
public class MetaProjectController implements MetaProjectAPI {

    @Autowired
    private MetaProjectService metaProjectService;

    @Override
    @PostMapping(value = "/save")
    public ReplyVO<MetaProjectShowVO> save(@Valid @RequestBody MetaProjectAddDTO metaProjectAddDTO) {
        MetaProjectPO metaProjectPO = metaProjectService.save(metaProjectAddDTO);
        return ReplyVO.success().data(MetaProjectMapper.INSTANCE.toShowVO(metaProjectPO));
    }

    @Override
    @PutMapping(value = "/update")
    public ReplyVO<MetaProjectShowVO> update(@Valid @RequestBody MetaProjectUpdateDTO metaProjectUpdateDTO) {
        MetaProjectPO metaProjectPO = metaProjectService.update(metaProjectUpdateDTO);
        return ReplyVO.success().data(MetaProjectMapper.INSTANCE.toShowVO(metaProjectPO));
    }

    @Override
    @GetMapping(value = "/list")
    public ReplyVO<List<MetaProjectListVO>> list(@Valid MetaProjectQO metaProjectQO) {
        List<MetaProjectListVO> list = metaProjectService.list(metaProjectQO);
        ReplyVO<List<MetaProjectListVO>> result = ReplyVO.success();
        result.setData(list);
        return result;
    }

    @Override
    @GetMapping(value = "/{projectId}")
    public ReplyVO<MetaProjectShowVO> show(@PathVariable Integer projectId) {
        MetaProjectShowVO metaProjectShowVO = metaProjectService.show(projectId);
        ReplyVO<MetaProjectShowVO> result = ReplyVO.success();
        result.setData(metaProjectShowVO);
        return result;
    }

    @Override
    @DeleteMapping(value = "/{projectId}")
    public ReplyVO<Integer> delete(@PathVariable Integer projectId) {
        int count = metaProjectService.delete(projectId);
        ReplyVO<Integer> result = ReplyVO.success();
        result.setData(count);
        return result;
    }

    @Override
    @PutMapping(value = "deleteBatch")
    public ReplyVO<Integer> deleteBatch(@RequestBody Integer[] projectId) {
        if(ArrayUtils.isEmpty(projectId)){
            return ReplyVO.fail("参数为空");
        }
        int count = metaProjectService.delete(projectId);
        ReplyVO<Integer> result = ReplyVO.success();
        result.setData(count);
        return result;
    }
}
