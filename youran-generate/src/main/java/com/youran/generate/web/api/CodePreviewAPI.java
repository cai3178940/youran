package com.youran.generate.web.api;

import com.youran.generate.pojo.vo.CodeTreeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/8/29
 */
@Api(tags = "CodePreview")
public interface CodePreviewAPI {

    /**
     * 查看代码文件内容
     * @param projectId 项目id
     * @param projectVersion 项目版本号
     * @param filePath 文件路径
     * @return
     */
    @ApiOperation(value = "查看代码文件内容")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "projectId", dataType = "int", value = "项目id", paramType = "path"),
        @ApiImplicitParam(name = "projectVersion", dataType = "int", value = "项目版本号", paramType = "query",required = true),
        @ApiImplicitParam(name = "filePath", dataType = "string", value = "文件路径", paramType = "query",required = true)
    })
    ResponseEntity<String> getFileContent(Integer projectId,Integer projectVersion,String filePath);

    /**
     * 查看代码目录结构
     * @param projectId 项目id
     * @return
     */
    @ApiOperation(value = "查看代码目录结构")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "projectId", dataType = "int", value = "项目id", paramType = "path")
    })
    ResponseEntity<CodeTreeVO> codeTree(Integer projectId);


}
