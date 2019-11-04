package com.youran.generate.template.renderer;

import com.youran.generate.pojo.po.CodeTemplatePO;

/**
 * <p>Title: 模板渲染器建造者</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 11/4/2019 22:23
 */
public interface TemplateRendererBuilder {

    /**
     * 构建模板渲染器
     * @param templatePO
     * @return
     */
    TemplateRenderer buildRenderer(CodeTemplatePO templatePO);

}
