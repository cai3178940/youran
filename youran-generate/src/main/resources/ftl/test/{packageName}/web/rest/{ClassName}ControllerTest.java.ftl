<#include "/common.ftl">
<#include "/entity_common.ftl">

<#assign importOtherStr="">
<#--定义主体代码-->
<#assign code>

<@classCom "【${title}】单元测试"></@classCom>
public class ${CName}ControllerTest extends AbstractWebTest {

    @Autowired
    private ${CName}Helper ${cName}Helper;
<#if metaEntity.mtmHoldRefers??>
    <#list metaEntity.mtmHoldRefers as otherEntity>
        <#assign otherCName=otherEntity.className?capFirst>
        <#assign othercName=otherEntity.className?uncapFirst>
        <#assign importOtherStr+="import ${packageName}.help.${otherCName}Helper;\n">
    @Autowired
    private ${otherCName}Helper ${othercName}Helper;
    </#list>
</#if>

    @Test
    public void save() throws Exception {
        ${CName}AddDTO addDTO = ${cName}Helper.get${CName}AddDTO();
        restMockMvc.perform(post(getRootPath()+"/${cName}/save")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(addDTO)))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)));
    }

    @Test
    public void update() throws Exception {
        ${CName}PO ${cName} = ${cName}Helper.save${CName}Example();
        ${CName}UpdateDTO updateDTO = ${cName}Helper.get${CName}UpdateDTO(${cName});
        restMockMvc.perform(put(getRootPath()+"/${cName}/update")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(updateDTO)))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)));
    }

    @Test
    public void list() throws Exception {
        ${cName}Helper.save${CName}Example();
        restMockMvc.perform(get(getRootPath()+"/${cName}/list"))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)))
            .andExpect(jsonPath("$.data.entities.length()").value(is(1)));
    }

    @Test
    public void show() throws Exception {
        ${CName}PO ${cName} = ${cName}Helper.save${CName}Example();
        restMockMvc.perform(get(getRootPath()+"/${cName}/{${id}}",${cName}.get${Id}()))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)));
    }

    @Test
    public void del() throws Exception {
        ${CName}PO ${cName} = ${cName}Helper.save${CName}Example();
        restMockMvc.perform(delete(getRootPath()+"/${cName}/{${id}}",${cName}.get${Id}()))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)))
            .andExpect(jsonPath("$.data").value(is(1)));
    }

    @Test
    public void deleteBatch() throws Exception {
        ${CName}PO ${cName} = ${cName}Helper.save${CName}Example();
        restMockMvc.perform(put(getRootPath()+"/${cName}/deleteBatch")
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
        <#assign importOtherStr+="import ${packageName}.pojo.po.${otherCName}PO;\n">
    @Test
    public void addRemove${otherEntity.className}() throws Exception {
        ${CName}PO ${cName} = ${cName}Helper.save${CName}Example();
        ${otherEntity.className}PO ${othercName} = ${othercName}Helper.save${otherEntity.className}Example();
        restMockMvc.perform(put(getRootPath()+"/${cName}/{${id}}/add${otherEntity.className}/{${MetadataUtil.getPkAlias(othercName,false)}}",
            ${cName}.get${Id}(),${othercName}.get${otherPk.jfieldName?capFirst}()))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)))
            .andExpect(jsonPath("$.data").value(is(1)));
        restMockMvc.perform(put(getRootPath()+"/${cName}/{${id}}/remove${otherEntity.className}/{${MetadataUtil.getPkAlias(othercName,false)}}",
            ${cName}.get${Id}(),${othercName}.get${otherPk.jfieldName?capFirst}()))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)))
            .andExpect(jsonPath("$.data").value(is(1)));
    }

    @Test
    public void addRemove${otherEntity.className}2() throws Exception {
        ${CName}PO ${cName} = ${cName}Helper.save${CName}Example();
        ${otherEntity.className}PO ${othercName} = ${othercName}Helper.save${otherEntity.className}Example();
        restMockMvc.perform(put(getRootPath()+"/${cName}/{${id}}/add${otherEntity.className}",
            ${cName}.get${Id}())
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(Lists.newArrayList(${othercName}.get${otherPk.jfieldName?capFirst}()))))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)))
            .andExpect(jsonPath("$.data").value(is(1)));
        restMockMvc.perform(put(getRootPath()+"/${cName}/{${id}}/remove${otherEntity.className}",
            ${cName}.get${Id}())
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(Lists.newArrayList(${othercName}.get${otherPk.jfieldName?capFirst}()))))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)))
            .andExpect(jsonPath("$.data").value(is(1)));
    }

    @Test
    public void set${otherEntity.className}() throws Exception {
        ${CName}PO ${cName} = ${cName}Helper.save${CName}Example();
        ${otherEntity.className}PO ${othercName} = ${othercName}Helper.save${otherEntity.className}Example();
        restMockMvc.perform(put(getRootPath()+"/${cName}/{${id}}/set${otherEntity.className}",
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

import com.google.common.collect.Lists;
import ${commonPackage}.pojo.vo.ReplyVO;
import ${commonPackage}.util.JsonUtil;
import ${packageName}.help.${CName}Helper;
import ${packageName}.pojo.dto.${CName}AddDTO;
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
