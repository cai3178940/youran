<#include "/common.ftl">
package ${commonPackage}.pojo.vo;

import ${commonPackage}.util.JsonUtil;

import java.io.Serializable;

<@classCom "抽象VO"></@classCom>
public abstract class AbstractVO implements Serializable {

    private static final long serialVersionUID = -1417748095004687576L;

    @Override
    public String toString() {
        return JsonUtil.toJSONString(this);
    }

}
