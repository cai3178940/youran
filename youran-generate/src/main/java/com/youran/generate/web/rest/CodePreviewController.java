package com.youran.generate.web.rest;

import com.google.common.collect.Lists;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.GenerateConst;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.vo.CodeTreeVO;
import com.youran.generate.pojo.vo.FileNodeVO;
import com.youran.generate.service.MetaCodeGenService;
import com.youran.generate.service.MetaProjectService;
import com.youran.generate.web.AbstractController;
import com.youran.generate.web.api.CodePreviewAPI;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.comparator.CompositeFileComparator;
import org.apache.commons.io.comparator.DirectoryFileComparator;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.OrFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Title: 【代码预览】控制器</p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/8/29
 */
@RestController
@RequestMapping(GenerateConst.API_PATH +"/code_preview")
public class CodePreviewController extends AbstractController implements CodePreviewAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodePreviewController.class);

    public static final String[] EXTENSIONS_FILTER = new String[]{
        "java","xml","md","gitignore","sql","yml","properties"};

    @Autowired
    private MetaCodeGenService metaCodeGenService;
    @Autowired
    private MetaProjectService metaProjectService;


    @Override
    @GetMapping(value = "/{projectId}/file_content",produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public ResponseEntity<String> getFileContent(@PathVariable Integer projectId,
                                                 @RequestParam Integer projectVersion,
                                                 @RequestParam String filePath){
        MetaProjectPO project = metaProjectService.getProject(projectId,true);
        Integer recentVersion = project.getProjectVersion();
        if(recentVersion < projectVersion){
            throw new BusinessException("projectVersion有误");
        }
        String projectDir = metaCodeGenService.getProjectRecentDir(project);
        File dirFile = new File(projectDir);
        if(!dirFile.exists()){
            throw new BusinessException("代码目录不存在");
        }
        String fileFullPath = projectDir + filePath;
        File file = new File(fileFullPath);
        if(!file.exists()){
            throw new BusinessException("文件不存在");
        }
        try {
            if(!FileUtils.directoryContains(dirFile,file)){
                throw new BusinessException("文件路径不合法");
            }
        } catch (IOException e) {
            LOGGER.error("文件路径不合法",e);
            throw new BusinessException("文件路径不合法");
        }
        if(file.isDirectory()){
            throw new BusinessException("文件不合法");
        }
        String extension = FilenameUtils.getExtension(fileFullPath);
        if(!ArrayUtils.contains(EXTENSIONS_FILTER,extension)){
            throw new BusinessException("文件类型不合法");
        }
        try {
            String content = FileUtils.readFileToString(file,"utf-8");
            return ResponseEntity.ok(content);
        } catch (IOException e) {
            LOGGER.error("读取文件异常",e);
            throw new BusinessException("读取文件异常");
        }
    }

    @Override
    @GetMapping(value = "/{projectId}/code_tree")
    @ResponseBody
    public ResponseEntity<CodeTreeVO> codeTree(@PathVariable Integer projectId) {
        MetaProjectPO project = metaProjectService.getProject(projectId,true);
        String projectDir = metaCodeGenService.getProjectRecentDir(project);
        File dirFile = new File(projectDir);
        if(!dirFile.exists()){
            throw new BusinessException("代码目录不存在");
        }
        List<FileNodeVO> fileNodeList = this.recurCodeTree(dirFile, dirFile);
        CodeTreeVO treeVO = new CodeTreeVO();
        treeVO.setProjectId(projectId);
        treeVO.setProjectVersion(project.getProjectVersion());
        treeVO.setTree(fileNodeList);
        return ResponseEntity.ok(treeVO);
    }

    private static FileFilter getFileFilter(){
        IOFileFilter suffixFileFilter = new SuffixFileFilter(EXTENSIONS_FILTER);
        return new OrFileFilter(Lists.newArrayList(suffixFileFilter, DirectoryFileFilter.INSTANCE));
    }

    private List<FileNodeVO> recurCodeTree(File dirFile,File basePath){
        File[] files = dirFile.listFiles(getFileFilter());
        if(ArrayUtils.isEmpty(files)){
            return Collections.emptyList();
        }
        return Arrays.stream(files)
            .sorted(new CompositeFileComparator(DirectoryFileComparator.DIRECTORY_COMPARATOR,NameFileComparator.NAME_COMPARATOR))
            .map(file -> {
                FileNodeVO nodeVO = this.fileToNodeVO(file, basePath);
                if(file.isDirectory()){
                    List<FileNodeVO> children = recurCodeTree(file,basePath);
                    nodeVO.setChildren(children);
                }
                return nodeVO;
            })
            .collect(Collectors.toList());
    }

    private FileNodeVO fileToNodeVO(File file,File basePath){
        FileNodeVO nodeVO = new FileNodeVO();
        String fileName = file.getName();
        nodeVO.setName(fileName);
        nodeVO.setDir(file.isDirectory());
        String path = file.getPath().substring(basePath.getPath().length());
        nodeVO.setPath(path.replaceAll("\\\\","/"));
        nodeVO.setType(FilenameUtils.getExtension(fileName));
        return nodeVO;
    }

}
