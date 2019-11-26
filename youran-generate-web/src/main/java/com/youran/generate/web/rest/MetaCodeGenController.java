package com.youran.generate.web.rest;

import com.youran.common.util.DateUtil;
import com.youran.generate.constant.WebConst;
import com.youran.generate.pojo.mapper.GenHistoryMapper;
import com.youran.generate.pojo.po.GenHistoryPO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.vo.CheckCommitVO;
import com.youran.generate.pojo.vo.GenHistoryShowVO;
import com.youran.generate.service.GenHistoryService;
import com.youran.generate.service.MetaCodeGenService;
import com.youran.generate.service.MetaProjectService;
import com.youran.generate.web.AbstractController;
import com.youran.generate.web.api.MetaCodeGenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private GenHistoryService genHistoryService;


    @Override
    @GetMapping(value = "/gen_code")
    public ResponseEntity<Void> genCode(@RequestParam Integer projectId,
                                        @RequestParam Integer templateIndex) {
        metaCodeGenService.genProjectCodeIfNotExists(projectId, templateIndex, null);
        return ResponseEntity.ok(null);
    }

    @Override
    @GetMapping(value = "/gen_code_and_download")
    public void genCodeAndDownload(@RequestParam Integer projectId,
                                   @RequestParam Integer templateIndex,
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
    @PostMapping(value = "/git_commit")
    @ResponseBody
    public ResponseEntity<String> gitCommit(@RequestParam Integer projectId,
                                            @RequestParam Integer templateIndex) {
        GenHistoryPO genHistory = metaCodeGenService.gitCommit(projectId, templateIndex, null);
        return ResponseEntity.ok("已创建自动分支【" + genHistory.getBranch() + "】，并提交到远程");
    }

    @Override
    @GetMapping(value = "/check_commit")
    @ResponseBody
    public ResponseEntity<CheckCommitVO> checkCommit(@RequestParam Integer projectId,
                                                     @RequestParam Integer templateIndex) {
        MetaProjectPO project = metaProjectService.getAndCheckProject(projectId);
        CheckCommitVO vo = new CheckCommitVO();
        vo.setRemoteUrl(project.getRemoteUrlByIndex(templateIndex));
        GenHistoryPO lastGenHistory = genHistoryService.findLastGenHistory(projectId, project.getRemoteUrlByIndex(templateIndex));
        if (lastGenHistory != null) {
            GenHistoryShowVO lastGenVO = GenHistoryMapper.INSTANCE.toShowVO(lastGenHistory);
            vo.setFirstCommit(false);
            vo.setLastGenHistory(lastGenVO);
        }
        return ResponseEntity.ok(vo);
    }

}
