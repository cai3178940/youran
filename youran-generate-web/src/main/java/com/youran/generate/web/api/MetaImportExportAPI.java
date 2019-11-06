package com.youran.generate.web.api;

import com.youran.generate.pojo.vo.MetaProjectShowVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 元数据导入导出API
 *
 * @author: cbb
 * @date: 10/13/2019 15:38
 */
@Api(tags = "MetaImportExport")
public interface MetaImportExportAPI {

    /**
     * 元数据导出
     *
     * @param projectId
     * @param response
     */
    @ApiOperation(value = "元数据导出")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "projectId", dataType = "int", value = "项目id", paramType = "path"),
    })
    void metaExport(Integer projectId, HttpServletResponse response);

    /**
     * 元数据导入
     *
     * @param file
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "元数据导入")
    ResponseEntity<MetaProjectShowVO> metaImport(@RequestParam(value = "file") MultipartFile file) throws Exception;

}
