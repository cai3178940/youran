<#include "/common.ftl">
package ${packageName}.web;

import ${commonPackage}.convert.MyCustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.Date;

<@classCom "抽象controller"></@classCom>
public abstract class AbstractController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new MyCustomDateEditor());
    }

}
