package com.youran.generate.template.renderer.freemarker;

import com.youran.generate.pojo.po.CodeTemplatePO;
import freemarker.template.Configuration;
import org.springframework.stereotype.Component;

/**
 * <p>Title: freemarker配置工厂</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 11/4/2019 22:15
 */
@Component
public class FreeMarkerConfigFactory {

    /**
     * 根据模板实体获取freemarker配置
     * @param templatePO
     * @return
     */
    public Configuration getConfiguration(CodeTemplatePO templatePO){
        // TODO
        return null;
    }

}
