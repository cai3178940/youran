package com.youran.generate.web;

import com.youran.common.constant.ErrorCode;
import com.youran.common.context.LoginContext;
import com.youran.generate.constant.WebConst;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 抽象controller
 *
 * @author cbb
 * @date 2019/2/15
 */
public abstract class AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);

    @Autowired
    protected LoginContext loginContext;

    @Value(WebConst.API_PATH)
    protected String apiPath;

    /**
     * 响应Not found
     *
     * @param response
     */
    protected void replyNotFound(HttpServletResponse response) {
        response.setStatus(ErrorCode.NOT_FOUND.getValue());
        try {
            IOUtils.write(ErrorCode.NOT_FOUND.getDesc(), response.getOutputStream(), "UTF-8");
        } catch (IOException e) {
            LOGGER.error("IO异常", e);
        }
    }


    /**
     * 响应文件下载
     *
     * @param response
     * @param text
     */
    protected void replyDownloadText(HttpServletResponse response, String text, String downloadFileName) {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFileName);
        response.setHeader(headerKey, headerValue);
        try {
            IOUtils.write(text, response.getOutputStream(), "UTF-8");
        } catch (IOException e) {
            LOGGER.error("IO异常", e);
        }
    }

    /**
     * 响应文件下载
     *
     * @param response
     * @param file
     */
    protected void replyDownloadFile(HttpServletResponse response, File file, String downloadFileName) {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String encode = "file";
        try {
            encode = URLEncoder.encode(downloadFileName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("文件名编码异常，{}", downloadFileName);
        }
        String headerValue = String.format("attachment; filename=\"%s\"", encode);
        response.setHeader(headerKey, headerValue);
        try {
            byte[] bytes = FileUtils.readFileToByteArray(file);
            IOUtils.write(bytes, response.getOutputStream());
        } catch (IOException e) {
            LOGGER.error("IO异常", e);
        }
    }

}
