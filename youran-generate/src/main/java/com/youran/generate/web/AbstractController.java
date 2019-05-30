package com.youran.generate.web;

import com.youran.common.constant.ErrorCode;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * <p>Title: 抽象controller</p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/2/15
 */
public abstract class AbstractController {

    /**
     * 响应Not found
     * @param response
     */
    protected void replyNotFound(HttpServletResponse response){
        response.setStatus(ErrorCode.NOT_FOUND.getValue());
        try {
            IOUtils.write(ErrorCode.NOT_FOUND.getDesc(), response.getOutputStream(),"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * 响应文件下载
     * @param response
     * @param text
     */
    protected void replyDownloadText(HttpServletResponse response, String text, String downloadFileName){
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFileName);
        response.setHeader(headerKey, headerValue);
        try {
            IOUtils.write(text, response.getOutputStream(),"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 响应文件下载
     * @param response
     * @param file
     */
    protected void replyDownloadFile(HttpServletResponse response, File file, String downloadFileName){
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFileName);
        response.setHeader(headerKey, headerValue);
        try {
            byte[] bytes = FileUtils.readFileToByteArray(file);
            IOUtils.write(bytes, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
