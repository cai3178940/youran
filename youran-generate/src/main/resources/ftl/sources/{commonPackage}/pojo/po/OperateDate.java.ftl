<#include "/common.ftl">
package ${commonPackage}.pojo.po;

import java.util.Date;

<@classCom "操作日期接口"></@classCom>
public interface OperateDate {

    Date getOperateDate();

    void setOperateDate(Date operateDate);
}
