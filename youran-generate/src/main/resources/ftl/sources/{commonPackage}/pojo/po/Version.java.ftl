<#include "/common.ftl">
package ${commonPackage}.pojo.po;

<@classCom "是否乐观锁版本接口"></@classCom>
public interface Version {

    Integer getVersion();

    void setVersion(Integer version);

}
