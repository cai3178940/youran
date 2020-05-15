package com.youran.generate.web.rest;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.WebConst;
import com.youran.generate.pojo.dto.MetaProjectAddDTO;
import com.youran.generate.pojo.dto.MetaProjectUpdateDTO;
import com.youran.generate.pojo.mapper.MetaProjectMapper;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.qo.MetaProjectQO;
import com.youran.generate.pojo.vo.MetaProjectListVO;
import com.youran.generate.pojo.vo.MetaProjectShowVO;
import com.youran.generate.service.MetaProjectService;
import com.youran.generate.web.AbstractController;
import com.youran.generate.web.api.MetaProjectAPI;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * 【项目】控制器
 * <p> 项目增删改查功能
 *
 * @author: cbb
 * @date 2017/5/24
 */
@RestController
@RequestMapping(WebConst.API_PATH + "/meta_project")
public class MetaProjectController extends AbstractController implements MetaProjectAPI {

    @Autowired
    private MetaProjectService metaProjectService;

    @Override
    @PostMapping(value = "/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MetaProjectShowVO> save(@Valid @RequestBody MetaProjectAddDTO metaProjectAddDTO) throws Exception {
        MetaProjectPO metaProjectPO = metaProjectService.save(metaProjectAddDTO);
        return ResponseEntity.created(new URI(apiPath + "/meta_project/" + metaProjectPO.getProjectId()))
            .body(MetaProjectMapper.INSTANCE.toShowVO(metaProjectPO));
    }

    @Override
    @PutMapping(value = "/update")
    public ResponseEntity<MetaProjectShowVO> update(@Valid @RequestBody MetaProjectUpdateDTO metaProjectUpdateDTO) {
        MetaProjectPO metaProjectPO = metaProjectService.update(metaProjectUpdateDTO);
        return ResponseEntity.ok(MetaProjectMapper.INSTANCE.toShowVO(metaProjectPO));
    }

    @Override
    @GetMapping(value = "/list")
    public ResponseEntity<List<MetaProjectListVO>> list(@Valid MetaProjectQO metaProjectQO) {
        List<MetaProjectListVO> list = metaProjectService.list(metaProjectQO);
        return ResponseEntity.ok(list);
    }

    @Override
    @GetMapping(value = "/{projectId}")
    public ResponseEntity<MetaProjectShowVO> show(@PathVariable Integer projectId) {
        MetaProjectShowVO metaProjectShowVO = metaProjectService.show(projectId);
        return ResponseEntity.ok(metaProjectShowVO);
    }

    @Override
    @DeleteMapping(value = "/{projectId}")
    public ResponseEntity<Integer> delete(@PathVariable Integer projectId) {
        int count = metaProjectService.delete(projectId);
        return ResponseEntity.ok(count);
    }

    @Override
    @PutMapping(value = "/delete_batch")
    public ResponseEntity<Integer> deleteBatch(@RequestBody Integer[] projectId) {
        if (ArrayUtils.isEmpty(projectId)) {
            throw new BusinessException(ErrorCode.PARAM_IS_NULL);
        }
        int count = metaProjectService.delete(projectId);
        return ResponseEntity.ok(count);
    }

    @Override
    @GetMapping(value = "/{projectId}/modules")
    public ResponseEntity<List<String>> findModules(@PathVariable Integer projectId) {
        List<String> modules = metaProjectService.findModules(projectId);
        return ResponseEntity.ok(modules);
    }
}
