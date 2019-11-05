package com.youran.generate.web.ws;

import com.youran.common.exception.BusinessException;
import com.youran.common.util.DateUtil;
import com.youran.generate.constant.WebConst;
import com.youran.generate.pojo.po.GenHistoryPO;
import com.youran.generate.pojo.vo.ProgressVO;
import com.youran.generate.service.MetaCodeGenService;
import com.youran.generate.service.MetaProjectService;
import com.youran.generate.web.AbstractController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 【代码生成】websocket控制器
 *
 * @author: cbb
 * @date: 2017/5/13
 */
@Controller
@Api(tags = "MetaCodeGenWs")
public class MetaCodeGenWsController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetaCodeGenWsController.class);

    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private MetaCodeGenService metaCodeGenService;
    @Autowired
    private MetaProjectService metaProjectService;

    /**
     * 简单实现一个LRU缓存
     * 保存生成的代码压缩包地址
     * key:sessionId
     * value:[projectId,zipFilePath]
     */
    private LinkedHashMap<String, Object[]> lruCache = new LinkedHashMap<String, Object[]>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, Object[]> eldest) {
            return size() > 100;
        }
    };

    /**
     * websocket服务:只生成代码
     *
     * @param sessionId     websocket连接id
     * @param projectId     项目id
     * @param templateIndex 模板序号
     */
    @MessageMapping(value = "/genCode/{sessionId}")
    public void genCode(@DestinationVariable String sessionId,
                        @Header Integer projectId, @Header Integer templateIndex) {
        // 进度响应主题
        String topic = "/code_gen/genCode_progress/" + sessionId;
        try {
            // 初始化进度条
            ProgressVO.initProgress(sessionId);
            // 生成代码压缩包
            metaCodeGenService.genProjectCodeIfNotExists(projectId, templateIndex,
                progressVO -> this.replyProgress(topic, progressVO));
            this.replyProgress(topic, ProgressVO.success("代码生成完毕"));
        } catch (BusinessException e) {
            // 如果捕获到异常，则将异常也通知给前端浏览器
            this.replyProgress(topic, ProgressVO.error(e.getMessage()));
        } catch (Throwable e) {
            // 如果捕获到异常，则将异常也通知给前端浏览器
            this.replyProgress(topic, ProgressVO.error("系统内部错误"));
            LOGGER.error("代码生成异常", e);
        }
    }

    /**
     * websocket服务:生成代码并打压缩包
     *
     * @param sessionId     websocket连接id
     * @param projectId     项目id
     * @param templateIndex 模板序号
     */
    @MessageMapping(value = "/genCodeAndZip/{sessionId}")
    public void genCodeAndZip(@DestinationVariable String sessionId,
                              @Header Integer projectId, @Header Integer templateIndex) {
        // 进度响应主题
        String topic = "/code_gen/genCodeAndZip_progress/" + sessionId;
        try {
            // 初始化进度条
            ProgressVO.initProgress(sessionId);
            // 生成代码压缩包
            File zipFile = metaCodeGenService.genCodeZip(projectId, templateIndex,
                progressVO -> this.replyProgress(topic, progressVO));
            // 将zip文件路径存入缓存，随后浏览器就能下载了
            lruCache.put(sessionId, new Object[]{projectId, zipFile.getPath()});
            this.replyProgress(topic, ProgressVO.success("代码生成完毕"));
        } catch (BusinessException e) {
            // 如果捕获到异常，则将异常也通知给前端浏览器
            this.replyProgress(topic, ProgressVO.error(e.getMessage()));
        } catch (Throwable e) {
            // 如果捕获到异常，则将异常也通知给前端浏览器
            this.replyProgress(topic, ProgressVO.error("系统内部错误"));
            LOGGER.error("代码生成异常", e);
        }
    }

    /**
     * http服务-下载代码
     * 代码生成完以后，浏览器调用该服务下载代码
     *
     * @param sessionId
     * @param response
     */
    @GetMapping(value = WebConst.API_PATH + "/code_gen/downloadCode/{sessionId}")
    public void downloadCode(@PathVariable String sessionId, HttpServletResponse response) {
        File zipFile = null;
        Integer projectId = null;
        Object[] arr = lruCache.get(sessionId);
        if (arr != null) {
            projectId = (Integer) arr[0];
            String zipFilePath = (String) arr[1];
            zipFile = new File(zipFilePath);
        }
        if (zipFile == null || !zipFile.exists()) {
            this.replyNotFound(response);
        } else {
            String normalProjectName = metaProjectService.getNormalProjectName(projectId);
            String downloadFileName = normalProjectName + DateUtil.getDateStr(new Date(), "yyyyMMddHHmmss") + ".zip";
            this.replyDownloadFile(response, zipFile, downloadFileName);
        }
    }


    /**
     * websocket服务:提交Git
     *
     * @param sessionId     websocket连接id
     * @param projectId     项目id
     * @param templateIndex 模板序号
     */
    @MessageMapping(value = "/gitCommit/{sessionId}")
    public void gitCommit(@DestinationVariable String sessionId,
                          @Header Integer projectId, @Header Integer templateIndex) {
        // 进度响应主题
        String topic = "/code_gen/gitCommit_progress/" + sessionId;
        try {
            // 初始化进度条
            ProgressVO.initProgress(sessionId);
            // 校验操作人
            metaProjectService.checkOperatorByProjectId(projectId);
            // 提交到仓库
            GenHistoryPO history = metaCodeGenService.gitCommit(projectId, templateIndex,
                progressVO -> this.replyProgress(topic, progressVO));
            this.replyProgress(topic, ProgressVO.success("已创建自动分支【" + history.getBranch() + "】，并提交到远程"));
        } catch (BusinessException e) {
            // 如果捕获到异常，则将异常也通知给前端浏览器
            this.replyProgress(topic, ProgressVO.error(e.getMessage()));
        } catch (Exception e) {
            // 如果捕获到异常，则将异常也通知给前端浏览器
            this.replyProgress(topic, ProgressVO.error("系统内部错误"));
            LOGGER.error("提交Git异常", e);
        }
    }


    /**
     * 将进度发送给某个topic
     * 由前端浏览器接收
     *
     * @param topic 主题
     * @param vo    进度
     */
    private void replyProgress(String topic, ProgressVO vo) {
        // 如果前端浏览器监听了topic主题，就能收到进度消息
        this.template.convertAndSend(topic, vo);
    }

}
