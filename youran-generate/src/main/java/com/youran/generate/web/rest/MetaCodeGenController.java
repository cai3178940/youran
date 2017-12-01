package com.youran.generate.web.rest;

import com.youran.common.pojo.vo.ReplyVO;
import com.youran.common.util.JsonUtil;
import com.youran.generate.constant.GenerateConst;
import com.youran.generate.service.MetaCodeGenService;
import com.youran.generate.web.api.MetaCodeGenAPI;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Title:代码生成controller
 * Description:
 * Author: cbb
 * Create Time:2017/5/13 23:00
 */
@Controller
@RequestMapping(GenerateConst.GENERATE_ROOT_PATH + "/code_gen")
public class MetaCodeGenController implements MetaCodeGenAPI {

    @Autowired
    private MetaCodeGenService metaCodeGenService;

    @Override
    @GetMapping(value = "/genSql")
    public void genSql(Integer projectId, HttpServletResponse response) {
        try {
            String text = metaCodeGenService.genSql(projectId);
            response.setContentType("application/octet-stream");
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", "db.sql");
            response.setHeader(headerKey, headerValue);
            IOUtils.write(text, response.getOutputStream(),"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @GetMapping(value = "/genCode")
    public void genCode(Integer projectId, HttpServletResponse response) {
        try {
            File zipFile = metaCodeGenService.genCodeZip(projectId);
            if (zipFile == null) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                ReplyVO fail = ReplyVO.fail("not found");
                IOUtils.write(JsonUtil.toJSONString(fail), response.getOutputStream(),"UTF-8");
                return;
            }
            response.setContentType("application/octet-stream");
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", "code.zip");
            response.setHeader(headerKey, headerValue);
            byte[] bytes = FileUtils.readFileToByteArray(zipFile);
            IOUtils.write(bytes, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
