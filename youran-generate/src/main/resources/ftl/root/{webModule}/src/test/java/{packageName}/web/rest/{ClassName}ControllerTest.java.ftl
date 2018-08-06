<#include "/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("com.google.common.collect.Lists")/>
<@call this.addImport("${this.commonPackage}.pojo.vo.ReplyVO")/>
<@call this.addImport("${this.commonPackage}.util.JsonUtil")/>
<@call this.addImport("${this.packageName}.help.${this.classNameUpper}Helper")/>
<@call this.addImport("${this.packageName}.pojo.dto.${this.classNameUpper}AddDTO")/>
<@call this.addImport("${this.packageName}.pojo.dto.${this.classNameUpper}UpdateDTO")/>
<@call this.addImport("${this.packageName}.pojo.po.${this.classNameUpper}PO")/>
<@call this.addImport("${this.packageName}.web.AbstractWebTest")/>
<@call this.addImport("org.junit.Test")/>
<@call this.addImport("org.springframework.beans.factory.annotation.Autowired")/>
<@call this.addImport("org.springframework.http.MediaType")/>
<@call this.addStaticImport("org.hamcrest.Matchers.is")/>
<@call this.addStaticImport("org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*")/>
<@call this.addStaticImport("org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath")/>
<@call this.printClassCom("【${this.title}】单元测试")/>
public class ${this.classNameUpper}ControllerTest extends AbstractWebTest {

    <@call this.addAutowired("${this.packageName}.help" "${this.classNameUpper}Helper")/>
<#if this.metaEntity.mtmHoldRefers??>
    <#list this.metaEntity.mtmHoldRefers as otherEntity>
        <#assign otherCName=otherEntity.className?capFirst>
        <@call this.addAutowired("${this.packageName}.help" "${otherCName}Helper")/>
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
        <@call this.addAutowired("${this.packageName}.help" "${foreignCName}Helper")/>
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
            <@call this.addImport("${this.packageName}.pojo.po.${foreignCName}PO")/>
            <#assign saveForeignList = saveForeignList + [ tmp ] />
        </#if>
    </#if>
</#macro>
<#assign foreignArg="">
<#list this.insertFields as field>
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

    <@call this.printAutowired()/>

    @Test
    public void save() throws Exception {
    <#list saveForeignList as saveForeign>
        ${saveForeign}
    </#list>
        ${this.classNameUpper}AddDTO addDTO = ${this.className}Helper.get${this.classNameUpper}AddDTO(${foreignArg});
        restMockMvc.perform(post("/${this.className}/save")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)));
    }

    @Test
    public void update() throws Exception {
    <#list saveForeignList as saveForeign>
        ${saveForeign}
    </#list>
        ${this.classNameUpper}PO ${this.className} = ${this.className}Helper.save${this.classNameUpper}Example(${foreignArg});
        ${this.classNameUpper}UpdateDTO updateDTO = ${this.className}Helper.get${this.classNameUpper}UpdateDTO(${this.className});
        restMockMvc.perform(put("/${this.className}/update")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(updateDTO)))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)));
    }

    @Test
    public void list() throws Exception {
    <#list saveForeignList as saveForeign>
        ${saveForeign}
    </#list>
        ${this.className}Helper.save${this.classNameUpper}Example(${foreignArg});
        restMockMvc.perform(get("/${this.className}/list"))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)))
    <#if this.pageSign == 1>
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
        ${this.classNameUpper}PO ${this.className} = ${this.className}Helper.save${this.classNameUpper}Example(${foreignArg});
        restMockMvc.perform(get("/${this.className}/{${this.id}}",${this.className}.get${this.idUpper}()))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)));
    }

    @Test
    public void del() throws Exception {
    <#list saveForeignList as saveForeign>
        ${saveForeign}
    </#list>
        ${this.classNameUpper}PO ${this.className} = ${this.className}Helper.save${this.classNameUpper}Example(${foreignArg});
        restMockMvc.perform(delete("/${this.className}/{${this.id}}",${this.className}.get${this.idUpper}()))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)))
            .andExpect(jsonPath("$.data").value(is(1)));
    }

    @Test
    public void deleteBatch() throws Exception {
    <#list saveForeignList as saveForeign>
        ${saveForeign}
    </#list>
        ${this.classNameUpper}PO ${this.className} = ${this.className}Helper.save${this.classNameUpper}Example(${foreignArg});
        restMockMvc.perform(put("/${this.className}/deleteBatch")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(Lists.newArrayList(${this.className}.get${this.idUpper}()))))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)))
            .andExpect(jsonPath("$.data").value(is(1)));
    }

<#if this.metaEntity.mtmHoldRefers??>
    <#list this.metaEntity.mtmHoldRefers as otherEntity>
        <#assign otherPk=otherEntity.pkField>
        <#assign otherCName=otherEntity.className?capFirst>
        <#assign othercName=otherEntity.className?uncapFirst>
        <@call this.addImport("${this.packageName}.pojo.po.${otherCName}PO")/>
    @Test
    public void addRemove${otherEntity.className}() throws Exception {
        <#list saveForeignList as saveForeign>
        ${saveForeign}
        </#list>
        ${this.classNameUpper}PO ${this.className} = ${this.className}Helper.save${this.classNameUpper}Example(${foreignArg});
        ${otherEntity.className}PO ${othercName} = ${othercName}Helper.save${otherEntity.className}Example();
        restMockMvc.perform(put("/${this.className}/{${this.id}}/add${otherEntity.className}/{${MetadataUtil.getPkAlias(othercName,false)}}",
            ${this.className}.get${this.idUpper}(),${othercName}.get${otherPk.jfieldName?capFirst}()))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)))
            .andExpect(jsonPath("$.data").value(is(1)));
        restMockMvc.perform(put("/${this.className}/{${this.id}}/remove${otherEntity.className}/{${MetadataUtil.getPkAlias(othercName,false)}}",
            ${this.className}.get${this.idUpper}(),${othercName}.get${otherPk.jfieldName?capFirst}()))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)))
            .andExpect(jsonPath("$.data").value(is(1)));
    }

    @Test
    public void addRemove${otherEntity.className}2() throws Exception {
        <#list saveForeignList as saveForeign>
        ${saveForeign}
        </#list>
        ${this.classNameUpper}PO ${this.className} = ${this.className}Helper.save${this.classNameUpper}Example(${foreignArg});
        ${otherEntity.className}PO ${othercName} = ${othercName}Helper.save${otherEntity.className}Example();
        restMockMvc.perform(put("/${this.className}/{${this.id}}/add${otherEntity.className}",
            ${this.className}.get${this.idUpper}())
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(Lists.newArrayList(${othercName}.get${otherPk.jfieldName?capFirst}()))))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)))
            .andExpect(jsonPath("$.data").value(is(1)));
        restMockMvc.perform(put("/${this.className}/{${this.id}}/remove${otherEntity.className}",
            ${this.className}.get${this.idUpper}())
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
        ${this.classNameUpper}PO ${this.className} = ${this.className}Helper.save${this.classNameUpper}Example(${foreignArg});
        ${otherEntity.className}PO ${othercName} = ${othercName}Helper.save${otherEntity.className}Example();
        restMockMvc.perform(put("/${this.className}/{${this.id}}/set${otherEntity.className}",
            ${this.className}.get${this.idUpper}())
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
package ${this.packageName}.web.rest;

<@call this.printImport()/>

${code}
