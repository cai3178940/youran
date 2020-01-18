package com.youran.generate.template.renderer.freemarker;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.exception.SkipCurrentException;
import com.youran.generate.pojo.po.TemplateFilePO;
import com.youran.generate.template.context.BaseContext;
import com.youran.generate.template.context.ConstContext;
import com.youran.generate.template.context.EntityContext;
import com.youran.generate.template.renderer.TemplateRenderer;
import freemarker.core._TemplateModelException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

/**
 * freeMarker模板渲染器
 *
 * @author: cbb
 * @date: 2019/11/4
 */
public class FreeMarkerRenderer implements TemplateRenderer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FreeMarkerRenderer.class);

    private Configuration configuration;
    private String templateDir;

    public FreeMarkerRenderer(Configuration configuration, String templateDir) {
        this.configuration = configuration;
        this.templateDir = templateDir;
    }

    @Override
    public String renderPath(TemplateFilePO templateFilePO, BaseContext context) {
        String packageName = context.getPackageName();
        if (StringUtils.isBlank(packageName)) {
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR, "包名未设置");
        }
        String relativePath = templateFilePO.fetchFilePath();
        relativePath = relativePath
            .replace("{commonModule}", context.getProjectNameSplit() + "-common")
            .replace("{coreModule}", context.getProjectNameSplit() + "-core")
            .replace("{webModule}", context.getProjectNameSplit() + "-web")
            .replace("{packageName}", packageName.replaceAll("\\.", "/"))
            .replace("{commonPackage}", context.getCommonPackage().replaceAll("\\.", "/"))
            .replace("{ProjectName}", context.getProjectNameUpper())
            .replace("{projectName}", context.getProjectName())
            .replace("{project-name}", context.getProjectNameSplit());
        if (context instanceof EntityContext) {
            EntityContext entityContext = (EntityContext) context;
            relativePath = relativePath
                .replace("{module}", entityContext.getModule())
                .replace("{ClassName}", entityContext.getClassNameUpper())
                .replace("{className}", entityContext.getClassName());
        }
        if (context instanceof ConstContext) {
            ConstContext constContext = (ConstContext) context;
            relativePath = relativePath
                .replace("{enumName}", constContext.getConstName())
                .replace("{EnumName}", constContext.getConstNameUpper());
        }
        // 非二进制文件，去除最后的.ftl后缀
        if (!templateFilePO.getBinary()) {
            relativePath = relativePath.substring(0, relativePath.lastIndexOf("."));
        }
        return relativePath;
    }

    @Override
    public Object renderContent(TemplateFilePO templateFilePO, BaseContext context) {
        if (templateFilePO.getBinary()) {
            // 渲染二进制文件
            return this.renderBinaryFile(templateFilePO);
        } else {
            // 渲染freemarker模板内容
            return this.renderFreemarkerFile(templateFilePO, context);
        }

    }

    /**
     * 渲染二进制文件
     *
     * @param templateFilePO
     * @return
     */
    private byte[] renderBinaryFile(TemplateFilePO templateFilePO) {
        try {
            return FileUtils.readFileToByteArray(new File(templateDir +
                templateFilePO.fetchFilePath()));
        } catch (IOException e) {
            LOGGER.error("读取二进制模板文件异常", e);
            throw new BusinessException("读取二进制模板文件异常,templateName=" +
                templateFilePO.fetchFilePath(), e);
        }
    }

    /**
     * 渲染freemarker模板内容
     *
     * @param templateFilePO
     * @param context
     * @return
     */
    private String renderFreemarkerFile(TemplateFilePO templateFilePO, BaseContext context) {
        String relativePath = templateFilePO.fetchFilePath();
        try {
            StringWriter stringWriter = new StringWriter();
            BufferedWriter writer = new BufferedWriter(stringWriter);
            Template template = this.getTemplate(relativePath);
            template.process(context, writer);
            return stringWriter.getBuffer().toString();
        } catch (Exception e) {
            String extraErrorMsg = "";
            if (e instanceof _TemplateModelException) {
                Throwable cause = e.getCause();
                if (cause != null) {
                    if (cause instanceof SkipCurrentException) {
                        throw (SkipCurrentException) cause;
                    } else if (cause instanceof BusinessException) {
                        extraErrorMsg = ",原因：" + cause.getMessage();
                    }
                }
            }
            LOGGER.error(e.getMessage(), e);
            throw new BusinessException("freemarker解析异常,dataModel="
                + context + ",templateName=" + relativePath + extraErrorMsg, e);
        }
    }

    /**
     * 获取模板文件
     *
     * @param name
     * @return
     */
    public Template getTemplate(String name) {
        try {
            Template template = configuration.getTemplate(name, "utf-8");
            return template;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new BusinessException("获取freemarker模版异常");
        }
    }

}
