package com.youran.generate.web.rest;

import com.youran.generate.constant.WebConst;
import com.youran.generate.pojo.mapper.MetaProjectMapper;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.vo.MetaProjectShowVO;
import com.youran.generate.service.MetaImportExportService;
import com.youran.generate.service.MetaProjectService;
import com.youran.generate.service.DataDirService;
import com.youran.generate.web.AbstractController;
import com.youran.generate.web.api.MetaImportExportAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URI;

/**
 * 元数据导入导出controller
 *
 * @author: cbb
 * @date: 10/12/2019 21:15
 */
@RestController
@RequestMapping(WebConst.API_PATH)
public class MetaImportExportController extends AbstractController implements MetaImportExportAPI {

    @Autowired
    private MetaImportExportService metaImportExportService;
    @Autowired
    private MetaProjectService metaProjectService;
    @Autowired
    private DataDirService dataDirService;

    @Override
    @GetMapping(value = "/meta_export/{projectId}")
    public void metaExport(@PathVariable Integer projectId, HttpServletResponse response) {
        File zipFile = metaImportExportService.metaExport(projectId);
        if (zipFile == null || !zipFile.exists()) {
            this.replyNotFound(response);
        } else {
            String normalProjectName = metaProjectService.getNormalProjectName(projectId);
            String downloadFileName = normalProjectName + "Export.zip";
            this.replyDownloadFile(response, zipFile, downloadFileName);
        }
    }

    @Override
    @PostMapping(value = "/meta_import")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MetaProjectShowVO> metaImport(@RequestParam(value = "file") MultipartFile file) throws Exception {
        String importFilePath = dataDirService.getProjectImportFilePath();
        File zipFile = new File(importFilePath);
        File parentFile = zipFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        file.transferTo(zipFile);
        MetaProjectPO metaProjectPO = metaImportExportService.metaImport(zipFile);
        return ResponseEntity.created(new URI(apiPath + "/meta_project/" + metaProjectPO.getProjectId()))
            .body(MetaProjectMapper.INSTANCE.toShowVO(metaProjectPO));
    }


}
