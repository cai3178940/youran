<#include "/common.ftl">
<#include "/entity_common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "${commonPackage}.pojo.po.AbstractPO"/>

<#--判断是否继承特殊接口-->
<#assign implementsVersion=false>
<#assign implementsDeleteSign=false>
<#assign implementsCreateBy=false>
<#assign implementsCreateDate=false>
<#assign implementsOperateBy=false>
<#assign implementsOperateDate=false>
<#assign implementsCreateByDate=false>
<#assign implementsOperateByDate=false>
<#assign implementsCreateOperateDelete=false>
<#assign implementsCreateOperateDeleteVersion=false>

<#--判断是否继承单一接口-->
<#if metaEntity.delField??>
    <#assign implementsDeleteSign=true>
</#if>
<#if metaEntity.createByField??>
    <#assign implementsCreateBy=true>
</#if>
<#if metaEntity.createDateField??>
    <#assign implementsCreateDate=true>
</#if>
<#if metaEntity.operateByField??>
    <#assign implementsOperateBy=true>
</#if>
<#if metaEntity.operateByField??>
    <#assign implementsOperateDate=true>
</#if>
<#if metaEntity.versionField??>
    <#assign implementsVersion=true>
</#if>
<#--判断是否继承合并接口-->
<#if implementsCreateBy && implementsCreateDate>
    <#assign implementsCreateByDate=true>
</#if>
<#if implementsOperateBy && implementsOperateDate>
    <#assign implementsOperateByDate=true>
</#if>
<#if implementsCreateByDate && implementsOperateByDate && implementsDeleteSign>
    <#assign implementsCreateOperateDelete=true>
</#if>
<#if implementsCreateByDate && implementsOperateByDate && implementsDeleteSign && implementsVersion>
    <#assign implementsCreateOperateDeleteVersion=true>
</#if>

<#assign implementsStr=""><#--构建继承串-->
<#if implementsCreateOperateDeleteVersion>
    <#assign implementsStr+=" CreateOperateDeleteVersion,">
    <@import "${commonPackage}.pojo.po.CreateOperateDeleteVersion"/>
<#elseIf implementsCreateOperateDelete>
    <#assign implementsStr+=" CreateOperateDelete,">
    <@import "${commonPackage}.pojo.po.CreateOperateDelete"/>
<#else>
    <#if implementsCreateByDate>
        <#assign implementsStr+=" CreateByDate,">
        <@import "${commonPackage}.pojo.po.CreateByDate"/>
    <#elseIf implementsCreateBy>
        <#assign implementsStr+=" CreateBy,">
        <@import "${commonPackage}.pojo.po.CreateBy"/>
    <#elseIf implementsCreateDate>
        <#assign implementsStr+=" CreateDate,">
        <@import "${commonPackage}.pojo.po.CreateDate"/>
    </#if>
    <#if implementsOperateByDate>
        <#assign implementsStr+=" OperateByDate,">
        <@import "${commonPackage}.pojo.po.OperateByDate"/>
    <#elseIf implementsOperateBy>
        <#assign implementsStr+=" OperateBy,">
        <@import "${commonPackage}.pojo.po.OperateBy"/>
    <#elseIf implementsOperateDate>
        <#assign implementsStr+=" OperateDate,">
        <@import "${commonPackage}.pojo.po.OperateDate"/>
    </#if>
    <#if implementsDeleteSign>
        <#assign implementsStr+=" DelSign,">
        <@import "${commonPackage}.pojo.po.DelSign"/>
    </#if>
    <#if implementsVersion>
        <#assign implementsStr+=" Version,">
        <@import "${commonPackage}.pojo.po.Version"/>
    </#if>
</#if>
<#if implementsStr!="">
    <#assign implementsStr=" implements"+implementsStr?removeEnding(",")>
</#if>

<@classCom "${title}" "${desc}"/>
public class ${CName}PO extends AbstractPO${implementsStr} {

<#list fields as field>
    <#if field.jfieldType==JFieldType.DATE.getJavaType()>
        <@import "java.util.Date"/>
    <#elseIf field.jfieldType==JFieldType.BIGDECIMAL.getJavaType()>
        <@import "java.math.BigDecimal"/>
    </#if>

    private ${field.jfieldType} ${field.jfieldName};

</#list>
<#if metaEntity.mtmHoldRefers??>
    <#list metaEntity.mtmHoldRefers as otherEntity>
        <@import "java.util.List"/>
    private List<${otherEntity.className}PO> ${otherEntity.className?uncapFirst}POList;

    </#list>
</#if>

<#list fields as field>
    <@getterSetter field/>
</#list>


<#if implementsDeleteSign && metaEntity.delField.jfieldName!="delSign">
    @Override
    public Integer getDelSign() {
        return this.${metaEntity.delField.jfieldName};
    }

    @Override
    public void setDelSign(Integer delSign) {
        this.${metaEntity.delField.jfieldName} = delSign;
    }

</#if>
<#if implementsCreateBy && metaEntity.createByField.jfieldName!="createBy">
    @Override
    public String getCreateBy() {
        return this.${metaEntity.createByField.jfieldName};
    }

    @Override
    public void setCreateBy(String createBy) {
        this.${metaEntity.createByField.jfieldName} = createBy;
    }

</#if>
<#if implementsCreateDate && metaEntity.createDateField.jfieldName!="createDate">
    @Override
    public Date getCreateDate() {
        return this.${metaEntity.createDateField.jfieldName};
    }

    @Override
    public void setCreateDate(Date createDate) {
        this.${metaEntity.createDateField.jfieldName} = createDate;
    }

</#if>
<#if implementsOperateBy && metaEntity.operateByField.jfieldName!="operateBy">
    @Override
    public String getOperateBy() {
        return this.${metaEntity.operateByField.jfieldName};
    }

    @Override
    public void setOperateBy(String operateBy) {
        this.${metaEntity.operateByField.jfieldName} = operateBy;
    }

</#if>
<#if implementsOperateDate && metaEntity.operateDateField.jfieldName!="operateDate">
    @Override
    public Date getOperateDate() {
        return this.${metaEntity.operateDateField.jfieldName};
    }

    @Override
    public void setOperateDate(Date createDate) {
        this.${metaEntity.operateDateField.jfieldName} = createDate;
    }

</#if>
<#if implementsVersion && metaEntity.versionField.jfieldName!="version">
    @Override
    public Integer getVersion() {
        return this.${metaEntity.versionField.jfieldName};
    }

    @Override
    public void setVersion(Integer version) {
        this.${metaEntity.versionField.jfieldName} = version;
    }

</#if>
<#if metaEntity.mtmHoldRefers??>
    <#list metaEntity.mtmHoldRefers as otherEntity>
    public List<${otherEntity.className}PO> get${otherEntity.className}POList() {
        return ${otherEntity.className?uncapFirst}POList;
    }

    public void set${otherEntity.className}POList(List<${otherEntity.className}PO> ${otherEntity.className?uncapFirst}POList) {
        this.${otherEntity.className?uncapFirst}POList = ${otherEntity.className?uncapFirst}POList;
    }

    </#list>
</#if>

}
</#assign>
<#--开始渲染代码-->
package ${packageName}.pojo.po;

<@printImport/>

${code}
