<#include "/common.ftl">
package ${commonPackage}.pojo.po;

import java.util.Date;

<@classCom "创建日期接口"></@classCom>
public interface CreateDate {

    Date getCreateDate();

    void setCreateDate(Date createDate);

}
