package com.youran.generate.web.rest;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.LabelType;
import com.youran.generate.constant.WebConst;
import com.youran.generate.pojo.dto.MetaLabelDTO;
import com.youran.generate.service.MetaLabelService;
import com.youran.generate.web.AbstractController;
import com.youran.generate.web.api.MetaLabelAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 【标签元数据】控制器
 *
 * @author: cbb
 * @date: 2020-09-13
 */
@RestController
@RequestMapping(WebConst.API_PATH + "/meta_label")
public class MetaLabelController extends AbstractController implements MetaLabelAPI {

    @Autowired
    private MetaLabelService metaLabelService;

    @Override
    @GetMapping
    public ResponseEntity<List<MetaLabelDTO>> getMetaLabel(@RequestParam(required = false) Integer projectId,
                                                           @RequestParam(required = false) List<Integer> templateId,
                                                           @RequestParam String labelType) {
        if (!LabelType.check(labelType)) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "标签类型不存在");
        }
        List<MetaLabelDTO> list;
        if (projectId != null) {
            list = metaLabelService.getMetaLabelByProjectId(projectId, labelType);
        } else {
            list = metaLabelService.getMetaLabelByTemplateId(templateId, labelType);
        }

        return ResponseEntity.ok(list);
    }

}
