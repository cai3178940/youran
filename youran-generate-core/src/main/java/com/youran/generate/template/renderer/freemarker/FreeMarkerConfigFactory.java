package com.youran.generate.template.renderer.freemarker;

import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.*;
import com.youran.generate.pojo.po.CodeTemplatePO;
import com.youran.generate.service.DataDirService;
import com.youran.generate.service.TemplateFileOutputService;
import com.youran.generate.template.function.CommonTemplateFunction;
import com.youran.generate.template.function.JavaTemplateFunction;
import com.youran.generate.template.function.SqlTemplateFunction;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * freemarker配置工厂
 *
 * @author: cbb
 * @date: 11/4/2019 22:15
 */
@Component
public class FreeMarkerConfigFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(FreeMarkerConfigFactory.class);

    @Autowired
    private DataDirService dataDirService;
    @Autowired
    private TemplateFileOutputService templateFileOutputService;

    private Map<Integer, Triple<Configuration, Integer, String>> cache;
    private BeansWrapperBuilder beansWrapperBuilder;
    private Map<String,TemplateModel> staticModels;

    public FreeMarkerConfigFactory() {
        this.cache = new ConcurrentHashMap<>();
        this.beansWrapperBuilder = new BeansWrapperBuilder(Configuration.VERSION_2_3_21);
        this.beansWrapperBuilder.setExposeFields(true);
        Class[] staticModelClass = new Class[]{
            MetaConstType.class,
            JFieldType.class,
            QueryType.class,
            EditType.class,
            CustomFieldType.class,
            ChartType.class,
            FilterOperator.class,
            SortType.class,
            JoinType.class,
            MetaSpecialField.class,
            PrimaryKeyStrategy.class,
            CommonTemplateFunction.class,
            JavaTemplateFunction.class,
            SqlTemplateFunction.class,
        };
        this.staticModels = new HashMap<>(staticModelClass.length);
        for (Class modelClass : staticModelClass) {
            this.staticModels.put(modelClass.getSimpleName(),getStaticModel(modelClass));
        }
    }

    /**
     * 根据模板实体获取freemarker配置
     *
     * @param templatePO
     * @return
     */
    public Triple<Configuration, Integer, String> getConfigurationTriple(CodeTemplatePO templatePO) {
        Integer templateId = templatePO.getTemplateId();
        Triple<Configuration, Integer, String> triple = cache.get(templateId);
        if (triple == null || !Objects.equals(triple.getMiddle(), templatePO.getInnerVersion())) {
            triple = this.buildAndCache(templatePO);
        }
        return triple;
    }

    /**
     * 同步加锁构建freemarker配置，并放入缓存
     *
     * @param templatePO
     * @return
     */
    private synchronized Triple<Configuration, Integer, String> buildAndCache(CodeTemplatePO templatePO) {
        Integer templateId = templatePO.getTemplateId();
        Triple<Configuration, Integer, String> triple = cache.get(templateId);
        if (triple == null || !Objects.equals(triple.getMiddle(), templatePO.getInnerVersion())) {
            triple = this.buildTriple(templatePO);
            cache.put(templateId, triple);
        }
        return triple;
    }

    /**
     * 构建freemarker配置
     *
     * @param templatePO
     * @return (freemarker配置 ， 模板内部版本号 ， 模板目录地址)
     */
    private Triple<Configuration, Integer, String> buildTriple(CodeTemplatePO templatePO) {
        String templateDir = dataDirService.getTemplateRecentDir(templatePO);
        LOGGER.info("开始构建FreeMarker Configuration,templateId={},innerVersion={},模板文件输出目录：{}",
            templatePO.getTemplateId(), templatePO.getInnerVersion(), templateDir);
        // 把模板输出到目录
        templateFileOutputService.outputTemplateFiles(templatePO.getTemplateFiles(), templateDir);

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_25);
        try {
            cfg.setDirectoryForTemplateLoading(new File(templateDir));
        } catch (IOException e) {
            LOGGER.error("模板目录设置异常", e);
            throw new BusinessException("模板目录设置异常");
        }
        cfg.setNumberFormat("#");
        // 设置可访问的静态工具类
        for (Map.Entry<String, TemplateModel> entry : staticModels.entrySet()) {
            cfg.setSharedVariable(entry.getKey(), entry.getValue());
        }
        return Triple.of(cfg, templatePO.getInnerVersion(), templateDir);
    }


    /**
     * 获取freemarker可使用的bean
     *
     * @param clz
     * @return
     */
    public TemplateModel getStaticModel(Class clz) {
        BeansWrapper beansWrapper = beansWrapperBuilder.build();
        try {
            return beansWrapper.getStaticModels().get(clz.getName());
        } catch (TemplateModelException e) {
            throw new RuntimeException(e);
        }
    }

}
