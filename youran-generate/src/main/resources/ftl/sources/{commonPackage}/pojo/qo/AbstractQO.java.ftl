<#include "/common.ftl">
package ${commonPackage}.pojo.qo;


import ${commonPackage}.util.JsonUtil;

import java.io.Serializable;

<@classCom "数据查询参数对象超类"></@classCom>
public abstract class AbstractQO implements Serializable {

    @Override
    public String toString() {
        return JsonUtil.toJSONString(this);
    }

}
