package com.youran.generate.template.renderer.freemarker;

import com.youran.generate.pojo.po.CodeTemplatePO;
import com.youran.generate.template.renderer.TemplateRenderer;
import com.youran.generate.template.renderer.TemplateRendererBuilder;
import freemarker.template.Configuration;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * freeMarker模板渲染器建造者
 *
 * @author: cbb
 * @date: 11/4/2019 22:26
 */
@Component
public class FreeMarkerTemplateRendererBuilder implements TemplateRendererBuilder {


    @Autowired
    private FreeMarkerConfigFactory freeMarkerConfigFactory;

    @Override
    public TemplateRenderer buildRenderer(CodeTemplatePO templatePO) {
        Triple<Configuration, Integer, String> triple = freeMarkerConfigFactory.getConfigurationTriple(templatePO);
        FreeMarkerRenderer renderer = new FreeMarkerRenderer(triple.getLeft(), triple.getRight());
        return renderer;
    }


}
