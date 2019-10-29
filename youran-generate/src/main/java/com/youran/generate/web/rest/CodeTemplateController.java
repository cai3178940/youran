package com.youran.generate.web.rest;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.pojo.vo.PageVO;
import com.youran.generate.constant.WebConst;
import com.youran.generate.pojo.dto.CodeTemplateAddDTO;
import com.youran.generate.pojo.dto.CodeTemplateUpdateDTO;
import com.youran.generate.pojo.mapper.CodeTemplateMapper;
import com.youran.generate.pojo.po.CodeTemplatePO;
import com.youran.generate.pojo.qo.CodeTemplateQO;
import com.youran.generate.pojo.vo.CodeTemplateListVO;
import com.youran.generate.pojo.vo.CodeTemplateShowVO;
import com.youran.generate.service.CodeTemplateService;
import com.youran.generate.web.AbstractController;
import com.youran.generate.web.api.CodeTemplateAPI;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

/**
 * <p>Title: 【代码模板】控制器</p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/10/24
 */
@RestController
@RequestMapping(WebConst.API_PATH + "/code_template")
public class CodeTemplateController extends AbstractController implements CodeTemplateAPI {

    @Autowired
    private CodeTemplateService codeTemplateService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CodeTemplateShowVO> save(@Valid @RequestBody CodeTemplateAddDTO codeTemplateAddDTO) throws Exception {
        CodeTemplatePO codeTemplate = codeTemplateService.save(codeTemplateAddDTO);
        return ResponseEntity.created(new URI(apiPath +"/code_template/" + codeTemplate.getTemplateId()))
            .body(CodeTemplateMapper.INSTANCE.toShowVO(codeTemplate));
    }

    @Override
    @PutMapping
    public ResponseEntity<CodeTemplateShowVO> update(@Valid @RequestBody CodeTemplateUpdateDTO codeTemplateUpdateDTO) {
        CodeTemplatePO codeTemplate = codeTemplateService.update(codeTemplateUpdateDTO);
        return ResponseEntity.ok(CodeTemplateMapper.INSTANCE.toShowVO(codeTemplate));
    }

    @Override
    @GetMapping
    public ResponseEntity<PageVO<CodeTemplateListVO>> list(@Valid CodeTemplateQO codeTemplateQO) {
        PageVO<CodeTemplateListVO> page = codeTemplateService.list(codeTemplateQO);
        return ResponseEntity.ok(page);
    }

    @Override
    @GetMapping(value = "/{templateId}")
    public ResponseEntity<CodeTemplateShowVO> show(@PathVariable Integer templateId) {
        CodeTemplateShowVO codeTemplateShowVO = codeTemplateService.show(templateId);
        return ResponseEntity.ok(codeTemplateShowVO);
    }

    @Override
    @DeleteMapping(value = "/{templateId}")
    public ResponseEntity<Integer> delete(@PathVariable Integer templateId) {
        int count = codeTemplateService.delete(templateId);
        return ResponseEntity.ok(count);
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Integer> deleteBatch(@RequestBody Integer[] id) {
        if(ArrayUtils.isEmpty(id)){
            throw new BusinessException(ErrorCode.PARAM_IS_NULL);
        }
        int count = codeTemplateService.delete(id);
        return ResponseEntity.ok(count);
    }


}


