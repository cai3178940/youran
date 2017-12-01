<#include "/common.ftl">
package ${commonPackage}.pojo.dto;


import ${commonPackage}.util.JsonUtil;

import java.io.Serializable;

<@classCom "数据传输对象超类"></@classCom>
public class AbstractDTO implements Serializable {

    @Override
    public String toString() {
        return JsonUtil.toJSONString(this);
    }

}
