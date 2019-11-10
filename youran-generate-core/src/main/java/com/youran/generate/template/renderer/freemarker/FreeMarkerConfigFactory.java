package com.youran.generate.template.renderer.freemarker;

import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.JFieldType;
import com.youran.generate.constant.MetaConstType;
import com.youran.generate.constant.MetaSpecialField;
import com.youran.generate.constant.QueryType;
import com.youran.generate.pojo.po.CodeTemplatePO;
import com.youran.generate.service.TemplateFileOutputService;
import com.youran.generate.service.DataDirService;
import com.youran.generate.util.MetadataUtil;
import com.youran.generate.util.TemplateUtil;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
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

    private Map<Integer, Pair<Configuration, Integer>> cache;
    private BeansWrapperBuilder beansWrapperBuilder;
    private TemplateModel metaConstTypeModel;
    private TemplateModel metadataUtilModel;
    private TemplateModel templateUtilModel;
    private TemplateModel jFieldTypeModel;
    private TemplateModel queryTypeModel;
    private TemplateModel metaSpecialFieldModel;

    public FreeMarkerConfigFactory() {
        this.cache = new ConcurrentHashMap<>();
        this.beansWrapperBuilder = new BeansWrapperBuilder(Configuration.VERSION_2_3_21);
        this.beansWrapperBuilder.setExposeFields(true);
        this.metaConstTypeModel = getStaticModel(MetaConstType.class);
        this.metadataUtilModel = getStaticModel(MetadataUtil.class);
        this.templateUtilModel = getStaticModel(TemplateUtil.class);
        this.jFieldTypeModel = getStaticModel(JFieldType.class);
        this.queryTypeModel = getStaticModel(QueryType.class);
        this.metaSpecialFieldModel = getStaticModel(MetaSpecialField.class);
    }

    /**
     * 根据模板实体获取freemarker配置
     *
     * @param templatePO
     * @return
     */
    public Configuration getConfiguration(CodeTemplatePO templatePO) {
        Integer templateId = templatePO.getTemplateId();
        Pair<Configuration, Integer> pair = cache.get(templateId);
        if (pair == null || !Objects.equals(pair.getRight(), templatePO.getInnerVersion())) {
            pair = this.buildAndCache(templatePO);
        }
        return pair.getLeft();
    }

    /**
     * 同步加锁构建freemarker配置，并放入缓存
     *
     * @param templatePO
     * @return
     */
    private synchronized Pair<Configuration, Integer> buildAndCache(CodeTemplatePO templatePO) {
        Integer templateId = templatePO.getTemplateId();
        Pair<Configuration, Integer> pair = cache.get(templateId);
        if (pair == null || !Objects.equals(pair.getRight(), templatePO.getInnerVersion())) {
            pair = this.buildPair(templatePO);
            cache.put(templateId, pair);
        }
        return pair;
    }

    /**
     * 构建freemarker配置
     *
     * @param templatePO
     * @return
     */
    private Pair<Configuration, Integer> buildPair(CodeTemplatePO templatePO) {
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
        cfg.setSharedVariable("MetaConstType", metaConstTypeModel);
        cfg.setSharedVariable("MetadataUtil", metadataUtilModel);
        cfg.setSharedVariable("TemplateUtil", templateUtilModel);
        cfg.setSharedVariable("JFieldType", jFieldTypeModel);
        cfg.setSharedVariable("QueryType", queryTypeModel);
        cfg.setSharedVariable("MetaSpecialField", metaSpecialFieldModel);
        return Pair.of(cfg, templatePO.getInnerVersion());
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
