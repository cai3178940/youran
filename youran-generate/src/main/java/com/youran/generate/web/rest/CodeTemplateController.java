package com.youran.generate.web.rest;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.WebConst;
import com.youran.generate.pojo.dto.CodeTemplateAddDTO;
import com.youran.generate.pojo.dto.CodeTemplateUpdateDTO;
import com.youran.generate.pojo.mapper.CodeTemplateMapper;
import com.youran.generate.pojo.po.CodeTemplatePO;
import com.youran.generate.pojo.qo.CodeTemplateQO;
import com.youran.generate.pojo.qo.TemplateFileQO;
import com.youran.generate.pojo.vo.*;
import com.youran.generate.service.CodeTemplateService;
import com.youran.generate.service.TemplateFileService;
import com.youran.generate.service.TemplateImportExportService;
import com.youran.generate.service.TmpDirService;
import com.youran.generate.util.FileNodeUtil;
import com.youran.generate.web.AbstractController;
import com.youran.generate.web.api.CodeTemplateAPI;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.net.URI;
import java.util.List;

/**
 * 【代码模板】控制器
 *
 * @author cbb
 * @date 2019/10/24
 */
@RestController
@RequestMapping(WebConst.API_PATH + "/code_template")
public class CodeTemplateController extends AbstractController implements CodeTemplateAPI {

    @Autowired
    private CodeTemplateService codeTemplateService;
    @Autowired
    private TemplateFileService templateFileService;
    @Autowired
    private TmpDirService tmpDirService;
    @Autowired
    private TemplateImportExportService templateImportExportService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CodeTemplateShowVO> save(@Valid @RequestBody CodeTemplateAddDTO codeTemplateAddDTO) throws Exception {
        CodeTemplatePO codeTemplate = codeTemplateService.save(codeTemplateAddDTO);
        return ResponseEntity.created(new URI(apiPath + "/code_template/" + codeTemplate.getTemplateId()))
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
    public ResponseEntity<List<CodeTemplateListVO>> list(@Valid CodeTemplateQO codeTemplateQO) {
        List<CodeTemplateListVO> page = codeTemplateService.list(codeTemplateQO);
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
        if (ArrayUtils.isEmpty(id)) {
            throw new BusinessException(ErrorCode.PARAM_IS_NULL);
        }
        int count = codeTemplateService.delete(id);
        return ResponseEntity.ok(count);
    }

    @Override
    @GetMapping(value = "/{templateId}/dir_tree")
    public ResponseEntity<TemplateDirTreeVO> dirTree(@PathVariable Integer templateId) {
        TemplateFileQO templateFileQO = new TemplateFileQO();
        templateFileQO.setTemplateId(templateId);
        templateFileQO.setFileDirSortSign(1);
        List<TemplateFileListVO> list = templateFileService.list(templateFileQO);
        List<FileNodeVO> tree = FileNodeUtil.templateFileListToNodeTree(list);
        FileNodeUtil.treeSort(tree);
        TemplateDirTreeVO treeVO = new TemplateDirTreeVO();
        treeVO.setTemplateId(templateId);
        treeVO.setTree(tree);
        return ResponseEntity.ok(treeVO);
    }

    @Override
    @GetMapping(value = "/{templateId}/export")
    public void export(@PathVariable Integer templateId, HttpServletResponse response) {
        File zipFile = templateImportExportService.exportTemplate(templateId);
        if (zipFile == null || !zipFile.exists()) {
            this.replyNotFound(response);
        } else {
            CodeTemplatePO codeTemplate = codeTemplateService.getCodeTemplate(templateId, true);
            String downloadFileName = codeTemplate.getName() + "-"
                + codeTemplate.getTemplateVersion() + ".zip";
            this.replyDownloadFile(response, zipFile, downloadFileName);
        }
    }

    @Override
    @PostMapping(value = "/import")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CodeTemplateShowVO> importTemplate(@RequestParam(value = "file") MultipartFile file) throws Exception {
        String importFilePath = tmpDirService.getTemplateImportFilePath();
        File zipFile = new File(importFilePath);
        File parentFile = zipFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        file.transferTo(zipFile);
        CodeTemplatePO codeTemplate = templateImportExportService.importTemplate(zipFile);
        return ResponseEntity.created(new URI(apiPath + "/code_template/" + codeTemplate.getTemplateId()))
            .body(CodeTemplateMapper.INSTANCE.toShowVO(codeTemplate));
    }

}


