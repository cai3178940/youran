<#include "/common.ftl">
<#include "/entity_common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "${commonPackage}.pojo.po.AbstractPO"/>

<#--判断是否继承特殊接口-->
<#assign implementsVersion=false>
<#assign implementsDeleteSign=false>
<#assign implementsCreatedBy=false>
<#assign implementsCreatedTime=false>
<#assign implementsOperatedBy=false>
<#assign implementsOperatedTime=false>
<#assign implementsCreated=false>
<#assign implementsOperated=false>
<#assign implementsCreatedOperatedDeleted=false>
<#assign implementsCreatedOperatedDeletedVersion=false>

<#--判断是否继承单一接口-->
<#if metaEntity.delField??>
    <#assign implementsDeleteSign=true>
</#if>
<#if metaEntity.createdByField??>
    <#assign implementsCreatedBy=true>
</#if>
<#if metaEntity.createdTimeField??>
    <#assign implementsCreatedTime=true>
</#if>
<#if metaEntity.operatedByField??>
    <#assign implementsOperatedBy=true>
</#if>
<#if metaEntity.operatedTimeField??>
    <#assign implementsOperatedTime=true>
</#if>
<#if metaEntity.versionField??>
    <#assign implementsVersion=true>
</#if>
<#--判断是否继承合并接口-->
<#if implementsCreatedBy && implementsCreatedTime>
    <#assign implementsCreated=true>
</#if>
<#if implementsOperatedBy && implementsOperatedTime>
    <#assign implementsOperated=true>
</#if>
<#if implementsCreated && implementsOperated && implementsDeleteSign>
    <#assign implementsCreatedOperatedDeleted=true>
</#if>
<#if implementsCreated && implementsOperated && implementsDeleteSign && implementsVersion>
    <#assign implementsCreatedOperatedDeletedVersion=true>
</#if>

<#assign implementsStr=""><#--构建继承串-->
<#if implementsCreatedOperatedDeletedVersion>
    <#assign implementsStr+=" CreatedOperatedDeletedVersion,">
    <@import "${commonPackage}.pojo.po.CreatedOperatedDeletedVersion"/>
<#elseIf implementsCreatedOperatedDeleted>
    <#assign implementsStr+=" CreatedOperatedDeleted,">
    <@import "${commonPackage}.pojo.po.CreatedOperatedDeleted"/>
<#else>
    <#if implementsCreated>
        <#assign implementsStr+=" Created,">
        <@import "${commonPackage}.pojo.po.Created"/>
    <#elseIf implementsCreatedBy>
        <#assign implementsStr+=" CreatedBy,">
        <@import "${commonPackage}.pojo.po.CreatedBy"/>
    <#elseIf implementsCreatedTime>
        <#assign implementsStr+=" CreatedTime,">
        <@import "${commonPackage}.pojo.po.CreatedTime"/>
    </#if>
    <#if implementsOperated>
        <#assign implementsStr+=" Operated,">
        <@import "${commonPackage}.pojo.po.Operated"/>
    <#elseIf implementsOperatedBy>
        <#assign implementsStr+=" OperatedBy,">
        <@import "${commonPackage}.pojo.po.OperatedBy"/>
    <#elseIf implementsOperatedTime>
        <#assign implementsStr+=" OperatedTime,">
        <@import "${commonPackage}.pojo.po.OperatedTime"/>
    </#if>
    <#if implementsDeleteSign>
        <#assign implementsStr+=" Deleted,">
        <@import "${commonPackage}.pojo.po.Deleted"/>
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


<#if implementsDeleteSign && metaEntity.delField.jfieldName!="deleted">
    @Override
    public Integer getDeleted() {
        return this.${metaEntity.delField.jfieldName};
    }

    @Override
    public void setDeleted(Integer deleted) {
        this.${metaEntity.delField.jfieldName} = deleted;
    }

</#if>
<#if implementsCreatedBy && metaEntity.createdByField.jfieldName!="createdBy">
    @Override
    public String getCreatedBy() {
        return this.${metaEntity.createdByField.jfieldName};
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.${metaEntity.createdByField.jfieldName} = createdBy;
    }

</#if>
<#if implementsCreatedTime && metaEntity.createdTimeField.jfieldName!="createdTime">
    @Override
    public Date getCreatedTime() {
        return this.${metaEntity.createdTimeField.jfieldName};
    }

    @Override
    public void setCreatedTime(Date createdTime) {
        this.${metaEntity.createdTimeField.jfieldName} = createdTime;
    }

</#if>
<#if implementsOperatedBy && metaEntity.operatedByField.jfieldName!="operatedBy">
    @Override
    public String getOperatedBy() {
        return this.${metaEntity.operatedByField.jfieldName};
    }

    @Override
    public void setOperatedBy(String operatedBy) {
        this.${metaEntity.operatedByField.jfieldName} = operatedBy;
    }

</#if>
<#if implementsOperatedTime && metaEntity.operatedTimeField.jfieldName!="operatedTime">
    @Override
    public Date getOperatedTime() {
        return this.${metaEntity.operatedTimeField.jfieldName};
    }

    @Override
    public void setOperatedTime(Date createdTime) {
        this.${metaEntity.operatedTimeField.jfieldName} = createdTime;
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
