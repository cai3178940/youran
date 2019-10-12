package com.youran.generate.web.rest;

import com.youran.generate.constant.WebConst;
import com.youran.generate.service.MetaImportExportService;
import com.youran.generate.service.MetaProjectService;
import com.youran.generate.web.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * <p>Title: 元数据导入导出controller</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 10/12/2019 21:15
 */
@RestController
@RequestMapping(WebConst.API_PATH)
public class MetaImportExportController extends AbstractController {

    @Autowired
    private MetaImportExportService metaImportExportService;
    @Autowired
    private MetaProjectService metaProjectService;

    @GetMapping(value = "/meta_export/{projectId}")
    public void export(@PathVariable Integer projectId, HttpServletResponse response){
        File zipFile = metaImportExportService.export(projectId);
        if (zipFile == null || !zipFile.exists()) {
            this.replyNotFound(response);
        }else {
            String normalProjectName = metaProjectService.getNormalProjectName(projectId);
            String downloadFileName = normalProjectName + "Export.zip";
            this.replyDownloadFile(response, zipFile, downloadFileName);
        }
    }


}
