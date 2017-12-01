<#include "/common.ftl">
<#include "/entity_common.ftl">

<#assign importOtherStr="">
<#--定义主体代码-->
<#assign code>

<@classCom "【${title}】单元测试"></@classCom>
public class ${CName}ControllerTest extends AbstractWebTest {

    @Autowired
    private ${CName}Helper ${cName}Helper;

    @Test
    public void save() throws Exception {
        ${CName}AddDTO addDTO = ${cName}Helper.get${CName}AddDTO();
        restMockMvc.perform(post(getRootPath()+"/${cName}/save")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(jsonPath("$.errorCode").value(is(0)));
    }

    @Test
    public void update() throws Exception {
        ${CName}PO ${cName} = ${cName}Helper.save${CName}Example();
        ${CName}UpdateDTO updateDTO = ${cName}Helper.get${CName}UpdateDTO(${cName});
        restMockMvc.perform(put(getRootPath()+"/${cName}/update")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(updateDTO)))
            .andExpect(jsonPath("$.errorCode").value(is(0)));
    }

    @Test
    public void list() throws Exception {
        ${cName}Helper.save${CName}Example();
        ${CName}QueryDTO queryDTO = new ${CName}QueryDTO();
        restMockMvc.perform(post(getRootPath()+"/${cName}/list")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(queryDTO)))
            .andExpect(jsonPath("$.errorCode").value(is(0)))
            .andExpect(jsonPath("$.data.entities.length()").value(is(1)));
    }

    @Test
    public void show() throws Exception {
        ${CName}PO ${cName} = ${cName}Helper.save${CName}Example();
        restMockMvc.perform(get(getRootPath()+"/${cName}/{${id}}",${cName}.get${Id}()))
            .andExpect(jsonPath("$.errorCode").value(is(0)));
    }

    @Test
    public void del() throws Exception {
        ${CName}PO ${cName} = ${cName}Helper.save${CName}Example();
        restMockMvc.perform(delete(getRootPath()+"/${cName}/{${id}}",${cName}.get${Id}()))
            .andExpect(jsonPath("$.errorCode").value(is(0)))
            .andExpect(jsonPath("$.data").value(is(1)));
    }

<#if metaEntity.mtmHoldRefers??>
    <#list metaEntity.mtmHoldRefers as otherEntity>
        <#assign otherPk=otherEntity.pkField>
        <#assign importOtherStr+="import ${packageName}.pojo.${otherEntity.className};\n">
    @Test
    public void add${otherEntity.className}() throws Exception {
        ${CName} ${cName} = ${cName}Helper.save${CName}Example();
        ${otherEntity.className} ${otherEntity.className?uncapFirst} = ${cName}Helper.save${otherEntity.className}Example();
        restMockMvc.perform(put(getRootPath()+"/${cName}/{${id}}/add${otherEntity.className}/{${otherPk.jfieldName}}",
            ${cName}.get${Id}(),${otherEntity.className?uncapFirst}.get${otherPk.jfieldName?capFirst}()))
            .andExpect(jsonPath("$.errorCode").value(is(0)))
            .andExpect(jsonPath("$.data").value(is(1)));
    }

    </#list>
</#if>
}

</#assign>
<#--开始渲染代码-->
package ${packageName}.web.rest;

import ${commonPackage}.util.JsonUtil;
import ${packageName}.help.${CName}Helper;
import ${packageName}.pojo.dto.${CName}AddDTO;
import ${packageName}.pojo.dto.${CName}QueryDTO;
import ${packageName}.pojo.dto.${CName}UpdateDTO;
import ${packageName}.pojo.po.${CName}PO;
${importOtherStr}
import ${packageName}.web.AbstractWebTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

${code}
