package com.youran.generate.web.ws;

import com.youran.common.pojo.vo.ReplyVO;
import com.youran.common.util.DateUtil;
import com.youran.common.util.JsonUtil;
import com.youran.generate.constant.GenerateConst;
import com.youran.generate.pojo.vo.ProgressVO;
import com.youran.generate.service.MetaCodeGenService;
import com.youran.generate.service.MetaProjectService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Title:【代码生成】控制器
 * Description:
 * Author: cbb
 * Create Time:2017/5/13 23:00
 */
@Controller
public class MetaCodeGenWsController{

    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private MetaCodeGenService metaCodeGenService;
    @Autowired
    private MetaProjectService metaProjectService;

    /**
     * 简单实现一个LRU缓存
     * key:sessionId
     * value:[projectId,zipFilePath]
     */
    private LinkedHashMap<String, Object[]> lruCache = new LinkedHashMap<String, Object[]>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, Object[]> eldest) {
            return size() > 100;
        }
    };

    @MessageMapping(value = "/genCode/{sessionId}")
    public void genCode(@DestinationVariable String sessionId, @Header Integer projectId) {
        File zipFile = metaCodeGenService.genCodeZip(projectId,
            progressVO -> genCodeReply(sessionId,ReplyVO.success().data(progressVO)));
        // 将zip文件路径存入缓存，随后浏览器就能下载了
        lruCache.put(sessionId,new Object[]{projectId,zipFile.getPath()});
    }

    /**
     * 回应前端浏览器
     * @param sessionId
     * @param vo
     */
    private void genCodeReply(String sessionId,ReplyVO<ProgressVO> vo){
        this.template.convertAndSend("/code_gen/genCode_progress/"+sessionId,vo);
    }

    /**
     * http服务-下载代码
     * @param sessionId
     * @param response
     */
    @GetMapping(value = GenerateConst.API_PATH + "/code_gen/downloadCode/{sessionId}")
    public void downloadCode(@PathVariable String sessionId, HttpServletResponse response) {
        try {
            File zipFile = null;
            Integer projectId = null;
            Object[] arr = lruCache.get(sessionId);
            if(arr!=null){
                projectId = (Integer) arr[0];
                String zipFilePath = (String) arr[1];
                zipFile = new File(zipFilePath);
            }
            if (zipFile == null || !zipFile.exists()) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                ReplyVO fail = ReplyVO.fail("not found");
                IOUtils.write(JsonUtil.toJSONString(fail), response.getOutputStream(),"UTF-8");
                return;
            }
            response.setContentType("application/octet-stream");
            String headerKey = "Content-Disposition";
            String normalProjectName = metaProjectService.getNormalProjectName(projectId);
            String headerValue = String.format("attachment; filename=\"%s\"", normalProjectName+ DateUtil.getDateStr(new Date(),"yyyyMMddHHmmss")+".zip");
            response.setHeader(headerKey, headerValue);
            byte[] bytes = FileUtils.readFileToByteArray(zipFile);
            IOUtils.write(bytes, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
