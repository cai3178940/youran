package com.youran.generate.web.rest;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.WebConst;
import com.youran.generate.pojo.dto.TemplateFileAddDTO;
import com.youran.generate.pojo.dto.TemplateFileContentUpdateDTO;
import com.youran.generate.pojo.dto.TemplateFileUpdateDTO;
import com.youran.generate.pojo.mapper.TemplateFileMapper;
import com.youran.generate.pojo.po.TemplateFilePO;
import com.youran.generate.pojo.qo.TemplateFileQO;
import com.youran.generate.pojo.vo.TemplateFileListVO;
import com.youran.generate.pojo.vo.TemplateFileShowVO;
import com.youran.generate.service.TemplateFileService;
import com.youran.generate.web.AbstractController;
import com.youran.generate.web.api.TemplateFileAPI;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * 【模板文件】控制器
 *
 * @author cbb
 * @date 2019/10/24
 */
@RestController
@RequestMapping(WebConst.API_PATH + "/template_file")
public class TemplateFileController extends AbstractController implements TemplateFileAPI {

    @Autowired
    private TemplateFileService templateFileService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TemplateFileShowVO> save(@Valid @RequestBody TemplateFileAddDTO templateFileAddDTO) throws Exception {
        TemplateFilePO templateFile = templateFileService.save(templateFileAddDTO);
        return ResponseEntity.created(new URI(apiPath + "/template_file/" + templateFile.getFileId()))
            .body(TemplateFileMapper.INSTANCE.toShowVO(templateFile));
    }

    @Override
    @PostMapping(value = "/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TemplateFileShowVO> upload(@RequestParam Integer templateId,
                                                     @RequestParam String fileDir,
                                                     MultipartFile file) throws Exception {
        if (file == null) {
            throw new BusinessException(ErrorCode.BAD_PARAMETER, "文件为空");
        }
        String filename = file.getOriginalFilename();
        if (StringUtils.isBlank(filename)) {
            throw new BusinessException(ErrorCode.BAD_PARAMETER, "文件名为空");
        }
        if (filename.length() > 100) {
            throw new BusinessException(ErrorCode.BAD_PARAMETER, "文件名长度不能超过100");
        }
        byte[] bytes = file.getBytes();
        if (file.getSize() == 0 || ArrayUtils.isEmpty(bytes)) {
            throw new BusinessException(ErrorCode.BAD_PARAMETER, "文件内容不能为空");
        }
        if (file.getSize() > TemplateFilePO.TEMPLATE_FILE_LENGTH_LIMIT) {
            throw new BusinessException(ErrorCode.BAD_PARAMETER, "文件超过最大长度限制:" +
                FileUtils.byteCountToDisplaySize(TemplateFilePO.TEMPLATE_FILE_LENGTH_LIMIT));
        }
        TemplateFilePO templateFile = templateFileService.saveBinary(templateId, fileDir, filename, bytes);
        return ResponseEntity.created(new URI(apiPath + "/template_file/" + templateFile.getFileId()))
            .body(TemplateFileMapper.INSTANCE.toShowVO(templateFile));
    }


    @Override
    @PutMapping
    public ResponseEntity<TemplateFileShowVO> update(@Valid @RequestBody TemplateFileUpdateDTO templateFileUpdateDTO) {
        TemplateFilePO templateFile = templateFileService.update(templateFileUpdateDTO);
        return ResponseEntity.ok(TemplateFileMapper.INSTANCE.toShowVO(templateFile));
    }

    @Override
    @PutMapping(value = "/{fileId}/content")
    public ResponseEntity<Integer> updateContent(@PathVariable Integer fileId,
                                                 @Valid @RequestBody TemplateFileContentUpdateDTO dto) {
        dto.setFileId(fileId);
        TemplateFilePO templateFile = templateFileService.updateContent(dto);
        return ResponseEntity.ok(templateFile.getVersion());
    }

    @Override
    @GetMapping
    public ResponseEntity<List<TemplateFileListVO>> list(@Valid TemplateFileQO templateFileQO) {
        List<TemplateFileListVO> list = templateFileService.list(templateFileQO);
        return ResponseEntity.ok(list);
    }

    @Override
    @GetMapping(value = "/{fileId}")
    public ResponseEntity<TemplateFileShowVO> show(@PathVariable Integer fileId) {
        TemplateFileShowVO templateFileShowVO = templateFileService.show(fileId);
        return ResponseEntity.ok(templateFileShowVO);
    }

    @Override
    @DeleteMapping(value = "/{fileId}")
    public ResponseEntity<Integer> delete(@PathVariable Integer fileId) {
        int count = templateFileService.delete(fileId);
        return ResponseEntity.ok(count);
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Integer> deleteBatch(@RequestBody Integer[] id) {
        if (ArrayUtils.isEmpty(id)) {
            throw new BusinessException(ErrorCode.PARAM_IS_NULL);
        }
        int count = templateFileService.delete(id);
        return ResponseEntity.ok(count);
    }


}


