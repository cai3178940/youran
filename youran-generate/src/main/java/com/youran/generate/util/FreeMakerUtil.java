package com.youran.generate.util;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * Title:FreeMaker工具类
 * Description:
 * Author: cbb
 * Create Time:2017/5/13 22:47
 */
public class FreeMakerUtil {

    private static final Logger logger = LoggerFactory.getLogger(FreeMakerUtil.class);

    private static Configuration cfg = new Configuration(Configuration.VERSION_2_3_25);
    private static BeansWrapperBuilder builder = new BeansWrapperBuilder(Configuration.VERSION_2_3_21);

    static {
        cfg.setClassForTemplateLoading(FreeMakerUtil.class, "/ftl");
        cfg.setNumberFormat("#");
        builder.setExposeFields(true);
    }

    /**
     * 获取模板文件
     *
     * @param name
     * @return
     */
    public static Template getTemplate(String name) {
        try {
            Template template = cfg.getTemplate(name, "utf-8");
            return template;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("获取freemarker模版异常");
        }
    }

    /**
     * 控制台输出
     *
     * @param templateName
     * @param root
     */
    public static void print(String templateName, Map<String, Object> root) {

        try {
            Template template = getTemplate(templateName);
            template.process(root, new PrintWriter(System.out));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("freemarker解析异常");
        }

    }

    /**
     * PrintWriter写入
     *
     * @param templateName
     * @param root
     */
    public static void write(String templateName, Map<String, Object> root, PrintWriter printWriter) {

        try {
            Template template = getTemplate(templateName);
            template.process(root, printWriter);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("freemarker解析异常");
        }

    }

    /**
     * 输出文本
     *
     * @param templateName
     * @param root
     */
    public static String writeToStr(String templateName, Map<String, Object> root) {
        try {
            StringWriter stringWriter = new StringWriter();
            BufferedWriter writer = new BufferedWriter(stringWriter);
            Template template = getTemplate(templateName);
            template.process(root, writer);
            return stringWriter.getBuffer().toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("freemarker解析异常,templateName="+templateName,e);
        }
    }


    /**
     * 生成文件
     *
     * @param templateName:模板名
     * @param root：数据原型
     * @param outFilePath：输出路径(全路径名)
     */
    public static void generateFile(String templateName, Map<String, Object> root, String outFilePath) {

        FileWriter out = null;
        try {
            // 通过一个文件输出流，就可以写到相应的文件中，此处用的是绝对路径
            out = new FileWriter(outFilePath);
            Template temp = getTemplate(templateName);
            temp.process(root, out);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("freemarker解析异常");
        } finally {
            IOUtils.closeQuietly(out);
        }
    }


    /**
     * 获取freemarker可使用的bean
     *
     * @param clz
     * @return
     */
    public static TemplateModel getStaticModel(Class clz) {
        BeansWrapper beansWrapper = builder.build();
        try {
            return beansWrapper.getStaticModels().get(clz.getName());
        } catch (TemplateModelException e) {
            e.printStackTrace();
        }
        return null;
    }

}
