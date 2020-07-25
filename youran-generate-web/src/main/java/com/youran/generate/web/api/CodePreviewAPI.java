package com.youran.generate.web.api;

import com.youran.generate.pojo.qo.CodeContentQO;
import com.youran.generate.pojo.vo.CodeTreeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

/**
 * 代码预览API
 * @author cbb
 * @date 2019/8/29
 */
@Api(tags = "【代码预览】API")
public interface CodePreviewAPI {

    /**
     * 查看代码文件内容
     *
     * @param qo 查询参数
     * @return
     */
    @ApiOperation(value = "查看代码文件内容")
    ResponseEntity<String> getFileContent(CodeContentQO qo);

    /**
     * 查看代码目录结构
     *
     * @param projectId 项目id
     * @return
     */
    @ApiOperation(value = "查看代码目录结构")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "projectId", dataType = "int", value = "项目id", paramType = "query"),
        @ApiImplicitParam(name = "templateId", dataType = "int", value = "模板id", paramType = "query")
    })
    ResponseEntity<CodeTreeVO> codeTree(Integer projectId, Integer templateId);


}
