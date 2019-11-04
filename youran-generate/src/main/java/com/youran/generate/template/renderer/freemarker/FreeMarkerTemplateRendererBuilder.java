package com.youran.generate.template.renderer.freemarker;

import com.youran.generate.pojo.po.CodeTemplatePO;
import com.youran.generate.template.renderer.TemplateRenderer;
import com.youran.generate.template.renderer.TemplateRendererBuilder;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>Title: freeMarker模板渲染器建造者</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 11/4/2019 22:26
 */
@Component
public class FreeMarkerTemplateRendererBuilder implements TemplateRendererBuilder {


    @Autowired
    private FreeMarkerConfigFactory freeMarkerConfigFactory;

    @Override
    public TemplateRenderer buildRenderer(CodeTemplatePO templatePO) {
        Configuration configuration = freeMarkerConfigFactory.getConfiguration(templatePO);
        FreeMarkerRenderer renderer = new FreeMarkerRenderer(configuration);
        return renderer;
    }


}
