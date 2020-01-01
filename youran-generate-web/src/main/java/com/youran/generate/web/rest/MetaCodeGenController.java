package com.youran.generate.web.rest;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
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
import org.apache.commons.lang3.StringUtils;
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
                                        @RequestParam Integer templateId) {
        metaCodeGenService.genProjectCodeIfNotExists(projectId, templateId, null);
        return ResponseEntity.ok(null);
    }

    @Override
    @GetMapping(value = "/gen_code_and_download")
    public void genCodeAndDownload(@RequestParam Integer projectId,
                                   @RequestParam Integer templateId,
                                   HttpServletResponse response) {
        File zipFile = metaCodeGenService.genCodeZip(projectId, templateId, null);
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
                                            @RequestParam Integer templateId) {
        GenHistoryPO genHistory = metaCodeGenService.gitCommit(projectId, templateId, null);
        return ResponseEntity.ok("已在【" + genHistory.getBranch() + "】分支提交最新代码，并push到远程");
    }

    @Override
    @GetMapping(value = "/git_diff")
    @ResponseBody
    public ResponseEntity<String> gitDiff(@RequestParam Integer projectId,
                                          @RequestParam Integer templateId) {
        String diffText = metaCodeGenService.showGitDiff(projectId, templateId, null);
        return ResponseEntity.ok(diffText);
    }

    @Override
    @GetMapping(value = "/check_commit")
    @ResponseBody
    public ResponseEntity<CheckCommitVO> checkCommit(@RequestParam Integer projectId,
                                                     @RequestParam Integer templateId) {
        MetaProjectPO project = metaProjectService.getAndCheckProject(projectId);
        CheckCommitVO vo = new CheckCommitVO();
        String remoteUrl = project.getRemoteUrlByTemplateId(templateId);
        if (StringUtils.isBlank(remoteUrl)) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "未配置该模板对应的远程仓库");
        }
        vo.setRemoteUrl(remoteUrl);
        GenHistoryPO lastGenHistory = genHistoryService.findLastGenHistory(projectId, remoteUrl);
        if (lastGenHistory != null) {
            GenHistoryShowVO lastGenVO = GenHistoryMapper.INSTANCE.toShowVO(lastGenHistory);
            vo.setLastGenHistory(lastGenVO);
            vo.setFirstCommit(false);
        } else {
            vo.setFirstCommit(true);
        }
        return ResponseEntity.ok(vo);
    }

}
