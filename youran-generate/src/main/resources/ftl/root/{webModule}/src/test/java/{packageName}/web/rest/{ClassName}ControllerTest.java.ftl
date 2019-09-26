<#include "/common.ftl">
<@call this.addImport("com.google.common.collect.Lists")/>
<@call this.addImport("${this.commonPackage}.util.JsonUtil")/>
<@call this.addImport("${this.packageName}.help.${this.classNameUpper}Helper")/>
<@call this.addImport("${this.packageName}.pojo.dto.${this.classNameUpper}AddDTO")/>
<@call this.addImport("${this.packageName}.pojo.dto.${this.classNameUpper}UpdateDTO")/>
<@call this.addImport("${this.packageName}.pojo.po.${this.classNameUpper}PO")/>
<@call this.addImport("${this.packageName}.web.AbstractWebTest")/>
<@call this.addImport("${this.packageName}.web.constant.WebConst")/>
<@call this.addImport("org.junit.Test")/>
<@call this.addImport("org.springframework.beans.factory.annotation.Autowired")/>
<@call this.addImport("org.springframework.http.MediaType")/>
<@call this.addStaticImport("org.hamcrest.Matchers.is")/>
<@call this.addStaticImport("org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*")/>
<@call this.addStaticImport("org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath")/>
<@call this.addStaticImport("org.springframework.test.web.servlet.result.MockMvcResultMatchers.status")/>
<#--获取保存Example的代码-->
<#assign saveExampleCode=this.getPrintingSaveExample()/>
<#--定义方法区代码-->
<#assign methodCode>

    @Test
    public void save() throws Exception {
    <#list saveExampleCode as saveExample>
        <#if saveExample_has_next>
        ${saveExample}
        </#if>
    </#list>
        ${this.classNameUpper}AddDTO addDTO = ${this.className}Helper.get${this.classNameUpper}AddDTO(<@call this.printSaveExampleArg(this.metaEntity)/>);
        restMockMvc.perform(post(WebConst.API_PATH + "/${this.className}")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(status().isCreated());
    }

    @Test
    public void update() throws Exception {
    <#list saveExampleCode as saveExample>
        ${saveExample}
    </#list>
        ${this.classNameUpper}UpdateDTO updateDTO = ${this.className}Helper.get${this.classNameUpper}UpdateDTO(${this.className});
        restMockMvc.perform(put(WebConst.API_PATH + "/${this.className}")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(updateDTO)))
            .andExpect(status().isOk());
    }

    @Test
    public void list() throws Exception {
    <#list saveExampleCode as saveExample>
        ${saveExample}
    </#list>
        restMockMvc.perform(get(WebConst.API_PATH + "/${this.className}"))
            .andExpect(status().isOk())
    <#if isTrue(this.pageSign)>
            .andExpect(jsonPath("$.list.length()").value(is(1)));
    <#else>
            .andExpect(jsonPath("$.length()").value(is(1)));
    </#if>
    }

    @Test
    public void show() throws Exception {
    <#list saveExampleCode as saveExample>
        ${saveExample}
    </#list>
        restMockMvc.perform(get(WebConst.API_PATH + "/${this.className}/{${this.id}}",${this.className}.get${this.idUpper}()))
            .andExpect(status().isOk());
    }

    @Test
    public void del() throws Exception {
    <#list saveExampleCode as saveExample>
        ${saveExample}
    </#list>
        restMockMvc.perform(delete(WebConst.API_PATH + "/${this.className}/{${this.id}}",${this.className}.get${this.idUpper}()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(is(1)));
    }

    @Test
    public void deleteBatch() throws Exception {
    <#list saveExampleCode as saveExample>
        ${saveExample}
    </#list>
        restMockMvc.perform(delete(WebConst.API_PATH + "/${this.className}")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(Lists.newArrayList(${this.className}.get${this.idUpper}()))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(is(1)));
    }

    <#list this.holds! as otherEntity,mtm>
        <#assign otherPk=otherEntity.pkField>
        <#assign otherCName=otherEntity.className?capFirst>
        <#assign othercName=otherEntity.className?uncapFirst>
        <#assign otherFkId=mtm.getFkAlias(otherEntity.entityId,false)>
        <#--获取保存Example的代码-->
        <#assign saveExampleCode=this.getPrintingSaveExampleForMtm(otherEntity)/>
    @Test
    public void addRemove${otherCName}() throws Exception {
        <#list saveExampleCode as saveExample>
        ${saveExample}
        </#list>
        restMockMvc.perform(post(WebConst.API_PATH + "/${this.className}/{${this.id}}/${othercName}/{${otherFkId}}",
            ${this.className}.get${this.idUpper}(),${othercName}.get${otherPk.jfieldName?capFirst}()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(is(1)));
        restMockMvc.perform(delete(WebConst.API_PATH + "/${this.className}/{${this.id}}/${othercName}/{${otherFkId}}",
            ${this.className}.get${this.idUpper}(),${othercName}.get${otherPk.jfieldName?capFirst}()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(is(1)));
    }

    @Test
    public void addRemove${otherCName}2() throws Exception {
        <#list saveExampleCode as saveExample>
        ${saveExample}
        </#list>
        restMockMvc.perform(post(WebConst.API_PATH + "/${this.className}/{${this.id}}/${othercName}",
            ${this.className}.get${this.idUpper}())
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(Lists.newArrayList(${othercName}.get${otherPk.jfieldName?capFirst}()))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(is(1)));
        restMockMvc.perform(delete(WebConst.API_PATH + "/${this.className}/{${this.id}}/${othercName}",
            ${this.className}.get${this.idUpper}())
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(Lists.newArrayList(${othercName}.get${otherPk.jfieldName?capFirst}()))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(is(1)));
    }

    @Test
    public void set${otherCName}() throws Exception {
        <#list saveExampleCode as saveExample>
        ${saveExample}
        </#list>
        restMockMvc.perform(put(WebConst.API_PATH + "/${this.className}/{${this.id}}/${othercName}",
            ${this.className}.get${this.idUpper}())
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(Lists.newArrayList(${othercName}.get${otherPk.jfieldName?capFirst}()))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(is(1)));
    }

    </#list>
</#assign>
<#--开始渲染代码-->
package ${this.packageName}.web.rest;

<@call this.printImport()/>

<@call this.printClassCom("【${this.title}】单元测试")/>
public class ${this.classNameUpper}ControllerTest extends AbstractWebTest {

<@call this.printAutowired()/>

${methodCode}

}
