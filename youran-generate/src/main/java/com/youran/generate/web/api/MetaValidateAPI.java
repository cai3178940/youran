package com.youran.generate.web.api;

import com.youran.generate.pojo.vo.MetaEntityInnerValidateVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

/**
 * <p>Title:【实体】api</p>
 * <p>Description: </p>
 * @author: cbb
 * @date: 2019/10/10
 */
public interface MetaValidateAPI {

    /**
     * 实体内部校验
     */
    @ApiOperation(value = "实体内部校验")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "entityId", dataType = "int", value = "实体id", paramType = "path"),
    })
    ResponseEntity<MetaEntityInnerValidateVO> validateEntityInner(Integer entityId);

}
