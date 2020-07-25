package com.youran.generate.web.api;

import com.youran.generate.pojo.dto.TemplateFileAddDTO;
import com.youran.generate.pojo.dto.TemplateFileContentUpdateDTO;
import com.youran.generate.pojo.dto.TemplateFileUpdateDTO;
import com.youran.generate.pojo.qo.TemplateFileQO;
import com.youran.generate.pojo.vo.TemplateFileListVO;
import com.youran.generate.pojo.vo.TemplateFileShowVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 【模板文件】API
 * <p> swagger接口文档
 *
 * @author cbb
 * @date 2019/10/24
 */
@Api(tags = "【模板文件】API")
public interface TemplateFileAPI {

    /**
     * 新增【模板文件】
     */
    @ApiOperation(value = "新增【模板文件】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "templateFileAddDTO", dataType = "TemplateFileAddDTO", value = "新增【模板文件】参数", paramType = "body"),
    })
    ResponseEntity<TemplateFileShowVO> save(TemplateFileAddDTO templateFileAddDTO) throws Exception;


    /**
     * 上传二进制【模板文件】
     */
    @ApiOperation(value = "上传二进制【模板文件】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "templateId", dataType = "int", value = "模板id", paramType = "query"),
        @ApiImplicitParam(name = "fileDir", dataType = "string", value = "文件目录", paramType = "query"),
    })
    ResponseEntity<TemplateFileShowVO> upload(Integer templateId,
                                              String fileDir,
                                              MultipartFile file) throws Exception;

    /**
     * 修改【模板文件】
     */
    @ApiOperation(value = "修改【模板文件】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "templateFileUpdateDTO", dataType = "TemplateFileUpdateDTO", value = "修改【模板文件】参数", paramType = "body"),
    })
    ResponseEntity<TemplateFileShowVO> update(TemplateFileUpdateDTO templateFileUpdateDTO);

    /**
     * 修改【模板文件内容】
     */
    @ApiOperation(value = "修改【模板文件内容】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "fileId", dataType = "int", value = "【模板文件】id", paramType = "path"),
        @ApiImplicitParam(name = "dto", dataType = "TemplateFileContentUpdateDTO", value = "修改【模板文件内容】参数", paramType = "body"),
    })
    ResponseEntity<Integer> updateContent(Integer fileId,
                                          TemplateFileContentUpdateDTO dto);

    /**
     * 列表查询【模板文件】
     */
    @ApiOperation(value = "列表查询【模板文件】")
    ResponseEntity<List<TemplateFileListVO>> list(TemplateFileQO templateFileQO);

    /**
     * 查看【模板文件】详情
     */
    @ApiOperation(value = "查看【模板文件】详情")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "fileId", dataType = "int", value = "【模板文件】id", paramType = "path"),
    })
    ResponseEntity<TemplateFileShowVO> show(Integer fileId);

    /**
     * 删除单个【模板文件】
     */
    @ApiOperation(value = "删除单个【模板文件】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "fileId", dataType = "int", value = "【模板文件】id", paramType = "path"),
    })
    ResponseEntity<Integer> delete(Integer fileId);

    /**
     * 批量删除【模板文件】
     */
    @ApiOperation(value = "批量删除【模板文件】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", dataType = "int", value = "id数组", paramType = "body"),
    })
    ResponseEntity<Integer> deleteBatch(Integer[] id);


}

