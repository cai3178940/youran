package com.youran.generate.web.rest;

import com.youran.common.util.DateUtil;
import com.youran.generate.constant.WebConst;
import com.youran.generate.pojo.po.GenHistoryPO;
import com.youran.generate.service.MetaCodeGenService;
import com.youran.generate.service.MetaProjectService;
import com.youran.generate.web.AbstractController;
import com.youran.generate.web.api.MetaCodeGenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;

/**
 * 【代码生成】控制器
 * <p> 生成代码的服务接口
 *
 * @author: cbb
 * @date: 2017/5/13
 */
@Controller
@RequestMapping(WebConst.API_PATH + "/code_gen")
public class MetaCodeGenController extends AbstractController implements MetaCodeGenAPI {

    @Autowired
    private MetaCodeGenService metaCodeGenService;
    @Autowired
    private MetaProjectService metaProjectService;


    @Override
    @GetMapping(value = "/genCode")
    public ResponseEntity<Void> genCode(@RequestParam Integer projectId,
                                        @RequestParam Integer templateIndex) {
        metaCodeGenService.genProjectCodeIfNotExists(projectId, templateIndex, null);
        return ResponseEntity.ok(null);
    }

    @Override
    @GetMapping(value = "/genCodeAndDownload")
    public void genCodeAndDownload(@RequestParam Integer projectId,
                                   @RequestParam  Integer templateIndex,
                                   HttpServletResponse response) {
        File zipFile = metaCodeGenService.genCodeZip(projectId, templateIndex, null);
        if (zipFile == null || !zipFile.exists()) {
            this.replyNotFound(response);
        } else {
            String normalProjectName = metaProjectService.getNormalProjectName(projectId);
            String downloadFileName = normalProjectName + DateUtil.getDateStr(new Date(), "yyyyMMddHHmmss") + ".zip";
            this.replyDownloadFile(response, zipFile, downloadFileName);
        }
    }

    @Override
    @GetMapping(value = "/gitCommit")
    @ResponseBody
    public ResponseEntity<String> gitCommit(@RequestParam Integer projectId,
                                            @RequestParam Integer templateIndex) {
        GenHistoryPO genHistory = metaCodeGenService.gitCommit(projectId, templateIndex, null);
        return ResponseEntity.ok("已创建自动分支【" + genHistory.getBranch() + "】，并提交到远程");
    }

}
