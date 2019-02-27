package com.youran.generate.web.api;

import com.youran.common.pojo.vo.ReplyVO;
import com.youran.generate.pojo.dto.ReverseEngineeringDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>Title:【反向工程】api</p>
 * <p>Description:</p>
 * @author: cbb
 * Create Time:2017/5/24
 */
@Api(tags = "ReverseEngineering", description = "反向工程")
public interface ReverseEngineeringAPI {

    /**
     * 校验语法
     * @param dto
     * @return
     */
    @ApiOperation(value = "校验语法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dto", dataType = "ReverseEngineeringDTO", value = "反向工程参数", paramType = "body"),
    })
    ReplyVO<Void> check(ReverseEngineeringDTO dto);

    /**
     * 执行DDL反向工程
     * @param dto
     * @return
     */
    @ApiOperation(value = "执行DDL反向工程")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "dto", dataType = "ReverseEngineeringDTO", value = "反向工程参数", paramType = "body"),
    })
    ReplyVO<Void> execute(ReverseEngineeringDTO dto);



}
