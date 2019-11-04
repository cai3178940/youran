package com.youran.generate.template.renderer;

import com.youran.generate.pojo.po.TemplateFilePO;
import com.youran.generate.template.context.BaseContext;

/**
 * <p>Title: 模板渲染器</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 11/4/2019 21:58
 */
public interface TemplateRenderer {

    /**
     * 渲染模板路径
     * @param templateFilePO
     * @param context
     * @return
     */
    String renderPath(TemplateFilePO templateFilePO, BaseContext context);
    /**
     * 渲染模板内容
     * @param templateFilePO
     * @param context
     * @return
     */
    String renderContent(TemplateFilePO templateFilePO, BaseContext context);

}
