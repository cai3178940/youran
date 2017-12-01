<#include "/common.ftl">
package ${commonPackage}.pojo.po;

<@classCom "是否逻辑删除接口"></@classCom>
public interface DelSign {

    Integer getDelSign();

    void setDelSign(Integer delSign);

}
