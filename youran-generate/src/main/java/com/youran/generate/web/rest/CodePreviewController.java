package com.youran.generate.web.rest;

import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.WebConst;
import com.youran.generate.pojo.po.CodeTemplatePO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.qo.CodeContentQO;
import com.youran.generate.pojo.vo.CodeTreeVO;
import com.youran.generate.pojo.vo.FileNodeVO;
import com.youran.generate.service.CodeTemplateService;
import com.youran.generate.service.MetaProjectService;
import com.youran.generate.service.TmpDirService;
import com.youran.generate.util.FileNodeUtil;
import com.youran.generate.web.AbstractController;
import com.youran.generate.web.api.CodePreviewAPI;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 【代码预览】控制器
 *
 * @author cbb
 * @date 2019/8/29
 */
@RestController
@RequestMapping(WebConst.API_PATH + "/code_preview")
public class CodePreviewController extends AbstractController implements CodePreviewAPI {

    public static final String[] EXTENSIONS_FILTER = new String[]{
        "java", "xml", "md", "gitignore", "sql", "yml", "properties"};
    private static final Logger LOGGER = LoggerFactory.getLogger(CodePreviewController.class);
    @Autowired
    private MetaProjectService metaProjectService;
    @Autowired
    private CodeTemplateService codeTemplateService;
    @Autowired
    private TmpDirService tmpDirService;


    @Override
    @GetMapping(value = "/file_content", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public ResponseEntity<String> getFileContent(@Valid CodeContentQO qo) {
        MetaProjectPO project = metaProjectService.getProject(qo.getProjectId(), true);
        if (project.getProjectVersion() < qo.getProjectVersion()) {
            throw new BusinessException("projectVersion有误");
        }

        CodeTemplatePO templatePO = codeTemplateService.getCodeTemplate(qo.getTemplateId(), true);
        if (templatePO.getInnerVersion() < qo.getTemplateInnerVersion()) {
            throw new BusinessException("模板已更新，请返回重试");
        }

        String projectDir = tmpDirService.getProjectRecentDir(project, templatePO);
        File dirFile = new File(projectDir);
        if (!dirFile.exists()) {
            throw new BusinessException("代码目录不存在");
        }
        File file = new File(dirFile, qo.getFilePath());
        if (!file.exists()) {
            throw new BusinessException("文件不存在");
        }
        try {
            if (!FileUtils.directoryContains(dirFile, file)) {
                throw new BusinessException("文件路径不合法");
            }
        } catch (IOException e) {
            LOGGER.error("文件路径不合法", e);
            throw new BusinessException("文件路径不合法");
        }
        if (file.isDirectory()) {
            throw new BusinessException("文件不合法");
        }
        String extension = FilenameUtils.getExtension(qo.getFilePath());
        if (!ArrayUtils.contains(EXTENSIONS_FILTER, extension)) {
            throw new BusinessException("文件类型不合法");
        }
        try {
            String content = FileUtils.readFileToString(file, "utf-8");
            return ResponseEntity.ok(content);
        } catch (IOException e) {
            LOGGER.error("读取文件异常", e);
            throw new BusinessException("读取文件异常");
        }
    }

    @Override
    @GetMapping(value = "/code_tree")
    @ResponseBody
    public ResponseEntity<CodeTreeVO> codeTree(@RequestParam Integer projectId,
                                               @RequestParam Integer templateIndex) {
        MetaProjectPO project = metaProjectService.getProject(projectId, true);
        Integer templateId = project.forceGetTemplateIdByIndex(templateIndex);
        CodeTemplatePO templatePO = codeTemplateService.getCodeTemplate(templateId, true);
        String projectDir = tmpDirService.getProjectRecentDir(project, templatePO);
        File dirFile = new File(projectDir);
        if (!dirFile.exists()) {
            throw new BusinessException("代码目录不存在，请返回重试");
        }
        List<FileNodeVO> fileNodeList = FileNodeUtil.recurFileNodeTree(dirFile, dirFile, EXTENSIONS_FILTER);
        CodeTreeVO treeVO = new CodeTreeVO();
        treeVO.setProjectId(projectId);
        treeVO.setProjectVersion(project.getProjectVersion());
        treeVO.setTree(fileNodeList);
        return ResponseEntity.ok(treeVO);
    }


}
