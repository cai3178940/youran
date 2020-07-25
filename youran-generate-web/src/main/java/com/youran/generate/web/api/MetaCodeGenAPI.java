package com.youran.generate.web.api;

import com.youran.generate.pojo.vo.CheckCommitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

/**
 * 【代码生成】api
 *
 * @author: cbb
 * @date: 2017/5/13
 */
@Api(tags = "【代码生成】API")
public interface MetaCodeGenAPI {

    /**
     * 仅生成代码，不下载
     */
    @ApiOperation(value = "仅生成代码，不下载")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "projectId", dataType = "int", value = "项目id", paramType = "query"),
        @ApiImplicitParam(name = "templateId", dataType = "int", value = "模板id", paramType = "query"),
    })
    ResponseEntity<Void> genCode(Integer projectId, Integer templateId);

    /**
     * 生成代码并下载压缩包
     */
    @ApiOperation(value = "生成代码并下载压缩包")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "projectId", dataType = "int", value = "项目id", paramType = "query"),
        @ApiImplicitParam(name = "templateId", dataType = "int", value = "模板id", paramType = "query"),
    })
    void genCodeAndDownload(Integer projectId, Integer templateId, HttpServletResponse response);

    /**
     * 提交到仓库
     */
    @ApiOperation(value = "提交到仓库")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "projectId", dataType = "int", value = "项目id", paramType = "query"),
        @ApiImplicitParam(name = "templateId", dataType = "int", value = "模板id", paramType = "query"),
    })
    ResponseEntity<String> gitCommit(Integer projectId, Integer templateId);

    /**
     * 显示git代码差异
     */
    @ApiOperation(value = "显示git代码差异")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "projectId", dataType = "int", value = "项目id", paramType = "query"),
        @ApiImplicitParam(name = "templateId", dataType = "int", value = "模板id", paramType = "query"),
    })
    ResponseEntity<String> gitDiff(Integer projectId, Integer templateId);

    /**
     * git提交前校验
     */
    @ApiOperation(value = "git提交前校验")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "projectId", dataType = "int", value = "项目id", paramType = "query"),
        @ApiImplicitParam(name = "templateId", dataType = "int", value = "模板id", paramType = "query"),
    })
    ResponseEntity<CheckCommitVO> checkCommit(Integer projectId,
                                              Integer templateId);
}
