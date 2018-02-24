<#include "/common.ftl">
package ${commonPackage}.pojo.po;

<@classCom "逻辑删除+创建人&创建日期+操作人&操作日期+版本号"/>
public interface CreateOperateDeleteVersion extends CreateByDate,OperateByDate,DelSign,Version{
}
