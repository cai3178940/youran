package com.youran.generate.web.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletResponse;

/**
 * Title:代码生成api
 * Description:
 * Author: cbb
 * Create Time:2017/5/13 22:47
 */
@Api(tags = "MetaCodeGen", description = "代码生成")
public interface MetaCodeGenAPI {

    /**
     * 生成建表语句
     */
    @ApiOperation(value = "生成建表语句")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", dataType = "int", value = "项目id", paramType = "query"),
    })
    void genSql(Integer projectId, HttpServletResponse response);

    /**
     * 生成代码压缩包
     */
    @ApiOperation(value = "生成代码压缩包")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", dataType = "int", value = "项目id", paramType = "query"),
    })
    void genCode(Integer projectId, HttpServletResponse response);

}
