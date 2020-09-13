package com.youran.generate.web.api;

import com.youran.generate.pojo.dto.MetaLabelDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * 【标签元数据】API
 *
 * @author: cbb
 * @date: 2020-09-13
 */
@Api(tags = "【标签元数据】API")
public interface MetaLabelAPI {

    @ApiOperation(value = "获取标签元数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "projectId", dataType = "int", value = "项目id", paramType = "query"),
        @ApiImplicitParam(name = "templateId", allowMultiple = true, dataType = "int", value = "项目id", paramType = "query"),
        @ApiImplicitParam(name = "labelType", dataType = "String", value = "标签类型", paramType = "query"),
    })
    ResponseEntity<List<MetaLabelDTO>> getMetaLabel(Integer projectId,
                                                    List<Integer> templateId,
                                                    String labelType);

}
