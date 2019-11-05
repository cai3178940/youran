package com.youran.generate.web.rest;

import com.youran.generate.constant.WebConst;
import com.youran.generate.pojo.vo.MetaEntityInnerValidateVO;
import com.youran.generate.service.MetaValidateService;
import com.youran.generate.web.AbstractController;
import com.youran.generate.web.api.MetaValidateAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 【元数据校验】控制器
 *
 * @author: cbb
 * @date: 2019/10/10
 */
@RestController
@RequestMapping(WebConst.API_PATH + "/meta_validate")
public class MetaValidateController extends AbstractController implements MetaValidateAPI {

    @Autowired
    private MetaValidateService metaValidateService;

    @Override
    @GetMapping(value = "/entity_inner/{entityId}")
    public ResponseEntity<MetaEntityInnerValidateVO> validateEntityInner(@PathVariable Integer entityId) {
        MetaEntityInnerValidateVO vo = metaValidateService.validateEntityInner(entityId);
        return ResponseEntity.ok(vo);
    }


}
