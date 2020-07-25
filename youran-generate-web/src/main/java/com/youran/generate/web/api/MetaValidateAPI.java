package com.youran.generate.web.api;

import com.youran.generate.pojo.vo.MetaEntityInnerValidateVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

/**
 * 【元数据校验】API
 *
 * @author: cbb
 * @date: 2019/10/10
 */
@Api(tags = "【元数据校验】API")
public interface MetaValidateAPI {

    /**
     * 实体内部校验
     */
    @ApiOperation(value = "实体内部校验")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "projectId", dataType = "int", value = "项目id", paramType = "path"),
        @ApiImplicitParam(name = "entityId", dataType = "int", value = "实体id", paramType = "path"),
    })
    ResponseEntity<MetaEntityInnerValidateVO> validateEntityInner(Integer projectId,Integer entityId);

}
