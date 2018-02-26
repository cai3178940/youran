<#include "/common.ftl">
<#include "/entity_common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "com.google.common.collect.Lists"/>
<@import "${commonPackage}.pojo.vo.ReplyVO"/>
<@import "${commonPackage}.util.JsonUtil"/>
<@import "${packageName}.help.${CName}Helper"/>
<@import "${packageName}.pojo.dto.${CName}AddDTO"/>
<@import "${packageName}.pojo.dto.${CName}UpdateDTO"/>
<@import "${packageName}.pojo.po.${CName}PO"/>
<@import "${packageName}.web.AbstractWebTest"/>
<@import "org.junit.Test"/>
<@import "org.springframework.beans.factory.annotation.Autowired"/>
<@import "org.springframework.http.MediaType"/>
<@importStatic "org.hamcrest.Matchers.is"/>
<@importStatic "org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*"/>
<@importStatic "org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath"/>
<@classCom "【${title}】单元测试"/>
public class ${CName}ControllerTest extends AbstractWebTest {

    @Autowired
    private ${CName}Helper ${cName}Helper;
<#if metaEntity.mtmHoldRefers??>
    <#list metaEntity.mtmHoldRefers as otherEntity>
        <#assign otherCName=otherEntity.className?capFirst>
        <@autowired "${packageName}.help" "${otherCName}Helper"/>
    </#list>
</#if>

<#--定义包含所有"保存外键实体"的代码列表-->
<#assign saveForeignList=[]>
<#macro saveForeign field>
    <#--只添加非空外键-->
    <#if field.foreignKey==1 && field.notNull==1>
        <#local foreignEntity=field.foreignEntity>
        <#local foreignCName="${foreignEntity.className?capFirst}">
        <#local foreigncName="${foreignEntity.className?uncapFirst}">
        <#--引入help依赖-->
        <@autowired "${packageName}.help" "${foreignCName}Helper"/>
        <#--定义参数串-->
        <#local foreignArg="">
        <#list foreignEntity.insertFields as _field>
            <#if _field.foreignKey==1>
                <#assign _foreigncName="${_field.foreignEntity.className?uncapFirst}">
                <#if _field.notNull==1>
                    <#local foreignArg=foreignArg+"${_foreigncName}.get${_field.jfieldName?capFirst}(), ">
                <#else>
                    <#local foreignArg=foreignArg+"null, ">
                </#if>
            </#if>
        </#list>
        <#if foreignArg?length gt 0>
            <#local foreignArg=foreignArg?substring(0,foreignArg?length-2)>
        </#if>
        <#--定义保存外键实体的代码串-->
        <#local tmp="${foreignCName}PO ${foreigncName} = ${foreigncName}Helper.save${foreignCName}Example(${foreignArg});">
        <#if !saveForeignList?seqContains(tmp)>
            <#list foreignEntity.insertFields as _field>
                <#--递归调用宏-->
                <@saveForeign _field/>
            </#list>
            <@import "${packageName}.pojo.po.${foreignCName}PO"/>
            <#assign saveForeignList = saveForeignList + [ tmp ] />
        </#if>
    </#if>
</#macro>
<#assign foreignArg="">
<#list insertFields as field>
    <#if field.foreignKey==1>
        <#assign foreigncName="${field.foreignEntity.className?uncapFirst}">
        <#if field.notNull==1>
            <#assign foreignArg=foreignArg+"${foreigncName}.get${field.jfieldName?capFirst}(), ">
        <#else>
            <#assign foreignArg=foreignArg+"null, ">
        </#if>
        <@saveForeign field/>
    </#if>
</#list>
<#if foreignArg?length gt 0>
    <#assign foreignArg=foreignArg?substring(0,foreignArg?length-2)>
</#if>


    @Test
    public void save() throws Exception {
    <#list saveForeignList as saveForeign>
        ${saveForeign}
    </#list>
        ${CName}AddDTO addDTO = ${cName}Helper.get${CName}AddDTO(${foreignArg});
        restMockMvc.perform(post("/${cName}/save")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)));
    }

    @Test
    public void update() throws Exception {
    <#list saveForeignList as saveForeign>
        ${saveForeign}
    </#list>
        ${CName}PO ${cName} = ${cName}Helper.save${CName}Example(${foreignArg});
        ${CName}UpdateDTO updateDTO = ${cName}Helper.get${CName}UpdateDTO(${cName});
        restMockMvc.perform(put("/${cName}/update")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(updateDTO)))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)));
    }

    @Test
    public void list() throws Exception {
    <#list saveForeignList as saveForeign>
        ${saveForeign}
    </#list>
        ${cName}Helper.save${CName}Example(${foreignArg});
        restMockMvc.perform(get("/${cName}/list"))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)))
    <#if pageSign == 1>
            .andExpect(jsonPath("$.data.entities.length()").value(is(1)));
    <#else>
            .andExpect(jsonPath("$.data.length()").value(is(1)));
    </#if>
    }

    @Test
    public void show() throws Exception {
    <#list saveForeignList as saveForeign>
        ${saveForeign}
    </#list>
        ${CName}PO ${cName} = ${cName}Helper.save${CName}Example(${foreignArg});
        restMockMvc.perform(get("/${cName}/{${id}}",${cName}.get${Id}()))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)));
    }

    @Test
    public void del() throws Exception {
    <#list saveForeignList as saveForeign>
        ${saveForeign}
    </#list>
        ${CName}PO ${cName} = ${cName}Helper.save${CName}Example(${foreignArg});
        restMockMvc.perform(delete("/${cName}/{${id}}",${cName}.get${Id}()))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)))
            .andExpect(jsonPath("$.data").value(is(1)));
    }

    @Test
    public void deleteBatch() throws Exception {
    <#list saveForeignList as saveForeign>
        ${saveForeign}
    </#list>
        ${CName}PO ${cName} = ${cName}Helper.save${CName}Example(${foreignArg});
        restMockMvc.perform(put("/${cName}/deleteBatch")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(Lists.newArrayList(${cName}.get${Id}()))))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)))
            .andExpect(jsonPath("$.data").value(is(1)));
    }

<#if metaEntity.mtmHoldRefers??>
    <#list metaEntity.mtmHoldRefers as otherEntity>
        <#assign otherPk=otherEntity.pkField>
        <#assign otherCName=otherEntity.className?capFirst>
        <#assign othercName=otherEntity.className?uncapFirst>
        <@import "${packageName}.pojo.po.${otherCName}PO"/>
    @Test
    public void addRemove${otherEntity.className}() throws Exception {
        <#list saveForeignList as saveForeign>
            ${saveForeign}
        </#list>
        ${CName}PO ${cName} = ${cName}Helper.save${CName}Example(${foreignArg});
        ${otherEntity.className}PO ${othercName} = ${othercName}Helper.save${otherEntity.className}Example();
        restMockMvc.perform(put("/${cName}/{${id}}/add${otherEntity.className}/{${MetadataUtil.getPkAlias(othercName,false)}}",
            ${cName}.get${Id}(),${othercName}.get${otherPk.jfieldName?capFirst}()))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)))
            .andExpect(jsonPath("$.data").value(is(1)));
        restMockMvc.perform(put("/${cName}/{${id}}/remove${otherEntity.className}/{${MetadataUtil.getPkAlias(othercName,false)}}",
            ${cName}.get${Id}(),${othercName}.get${otherPk.jfieldName?capFirst}()))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)))
            .andExpect(jsonPath("$.data").value(is(1)));
    }

    @Test
    public void addRemove${otherEntity.className}2() throws Exception {
        <#list saveForeignList as saveForeign>
            ${saveForeign}
        </#list>
        ${CName}PO ${cName} = ${cName}Helper.save${CName}Example(${foreignArg});
        ${otherEntity.className}PO ${othercName} = ${othercName}Helper.save${otherEntity.className}Example();
        restMockMvc.perform(put("/${cName}/{${id}}/add${otherEntity.className}",
            ${cName}.get${Id}())
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(Lists.newArrayList(${othercName}.get${otherPk.jfieldName?capFirst}()))))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)))
            .andExpect(jsonPath("$.data").value(is(1)));
        restMockMvc.perform(put("/${cName}/{${id}}/remove${otherEntity.className}",
            ${cName}.get${Id}())
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(Lists.newArrayList(${othercName}.get${otherPk.jfieldName?capFirst}()))))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)))
            .andExpect(jsonPath("$.data").value(is(1)));
    }

    @Test
    public void set${otherEntity.className}() throws Exception {
        <#list saveForeignList as saveForeign>
            ${saveForeign}
        </#list>
        ${CName}PO ${cName} = ${cName}Helper.save${CName}Example(${foreignArg});
        ${otherEntity.className}PO ${othercName} = ${othercName}Helper.save${otherEntity.className}Example();
        restMockMvc.perform(put("/${cName}/{${id}}/set${otherEntity.className}",
            ${cName}.get${Id}())
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(Lists.newArrayList(${othercName}.get${otherPk.jfieldName?capFirst}()))))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)))
            .andExpect(jsonPath("$.data").value(is(1)));
    }

    </#list>
</#if>
}

</#assign>
<#--开始渲染代码-->
package ${packageName}.web.rest;

<@printImport/>

${code}
