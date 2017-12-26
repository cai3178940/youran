<#include "/common.ftl">
<#include "/entity_common.ftl">
<#--定义主体代码-->
<#assign code>
<@classCom "【${title}】API" "swagger接口文档"></@classCom>
@Api(tags = "${CName}", description = "${title}")
public interface ${CName}API {

    /**
     * 新增【${title}】
     */
    @ApiOperation(value="新增【${title}】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${cName}AddDTO", dataType = "${CName}AddDTO", value = "新增【${title}】参数", paramType = "body"),
    })
    ReplyVO<${type}> save(${CName}AddDTO ${cName}AddDTO);

    /**
     * 修改【${title}】
     */
    @ApiOperation(value="修改【${title}】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${cName}UpdateDTO", dataType = "${CName}UpdateDTO", value = "修改【${title}】参数", paramType = "body"),
    })
    ReplyVO<Void> update(${CName}UpdateDTO ${cName}UpdateDTO);

    /**
     * 分页查询【${title}】
     */
    @ApiOperation(value="分页查询【${title}】")
    ReplyVO<PageVO<${CName}ListVO>> list(${CName}QueryDTO ${cName}QueryDTO);

    /**
     * 查看【${title}】详情
     */
    @ApiOperation(value="查看【${title}】详情")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${id}", dataType = "${MetadataUtil.getSwaggerType(type)}", value = "【${title}】id", paramType = "path"),
    })
    ReplyVO<${CName}ShowVO> show(${type} ${id});

    /**
     * 删除单个【${title}】
     */
    @ApiOperation(value="删除单个【${title}】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${id}", dataType = "${MetadataUtil.getSwaggerType(type)}", value = "【${title}】id", paramType = "path"),
    })
    ReplyVO<Integer> delete(${type} ${id});

    /**
     * 批量删除【${title}】
     */
    @ApiOperation(value = "批量删除【${title}】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", dataType = "${MetadataUtil.getSwaggerType(type)}", value = "id数组", paramType = "body"),
    })
    ReplyVO<Integer> deleteBatch(${type}[] id);


<#if metaEntity.mtmHoldRefers??>
    <#list metaEntity.mtmHoldRefers as otherEntity>
        <#assign otherPk=otherEntity.pkField>
        <#assign otherCName=otherEntity.className?capFirst>
        <#assign othercName=otherEntity.className?uncapFirst>
        <#assign otherPkId=MetadataUtil.getPkAlias(othercName,false)>
    /**
     * 添加单个【${otherEntity.title}】关联
     */
    @ApiOperation(value="添加单个【${otherEntity.title}】关联")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${id}", dataType = "${MetadataUtil.getSwaggerType(type)}", value = "【${title}】id", paramType = "path"),
        @ApiImplicitParam(name = "${otherPkId}", dataType = "${MetadataUtil.getSwaggerType(otherPk.jfieldType)}", value = "【${otherEntity.title}】id", paramType = "path"),
    })
    ReplyVO<Integer> add${otherCName}(${type} ${id},${otherPk.jfieldType} ${otherPkId});

    /**
     * 添加多个【${otherEntity.title}】关联
     */
    @ApiOperation(value="添加多个【${otherEntity.title}】关联")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${id}", dataType = "${MetadataUtil.getSwaggerType(type)}", value = "【${title}】id", paramType = "path"),
        @ApiImplicitParam(name = "${otherPkId}", dataType = "${MetadataUtil.getSwaggerType(otherPk.jfieldType)}", value = "【${otherEntity.title}】id数组", paramType = "body"),
    })
    ReplyVO<Integer> add${otherCName}(${type} ${id},${otherPk.jfieldType}[] ${otherPkId});

    /**
     * 移除单个【${otherEntity.title}】关联
     */
    @ApiOperation(value="移除单个【${otherEntity.title}】关联")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${id}", dataType = "${MetadataUtil.getSwaggerType(type)}", value = "【${title}】id", paramType = "path"),
        @ApiImplicitParam(name = "${otherPkId}", dataType = "${MetadataUtil.getSwaggerType(otherPk.jfieldType)}", value = "【${otherEntity.title}】id", paramType = "path"),
    })
    ReplyVO<Integer> remove${otherCName}(${type} ${id},${otherPk.jfieldType} ${otherPkId});

    /**
     * 移除多个【${otherEntity.title}】关联
     */
    @ApiOperation(value="移除多个【${otherEntity.title}】关联")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${id}", dataType = "${MetadataUtil.getSwaggerType(type)}", value = "【${title}】id", paramType = "path"),
        @ApiImplicitParam(name = "${otherPkId}", dataType = "${MetadataUtil.getSwaggerType(otherPk.jfieldType)}", value = "【${otherEntity.title}】id数组", paramType = "body"),
    })
    ReplyVO<Integer> remove${otherCName}(${type} ${id},${otherPk.jfieldType}[] ${otherPkId});

    /**
     * 设置【${otherEntity.title}】关联
     */
    @ApiOperation(value="设置【${otherEntity.title}】关联")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${id}", dataType = "${MetadataUtil.getSwaggerType(type)}", value = "【${title}】id", paramType = "path"),
        @ApiImplicitParam(name = "${otherPkId}", dataType = "${MetadataUtil.getSwaggerType(otherPk.jfieldType)}", value = "【${otherEntity.title}】id数组", paramType = "body"),
    })
    ReplyVO<Integer> set${otherCName}(${type} ${id},${otherPk.jfieldType}[] ${otherPkId});

    </#list>
</#if>

}
</#assign>
<#--开始渲染代码-->
package ${packageName}.web.api;

import ${commonPackage}.pojo.vo.ReplyVO;
import ${commonPackage}.pojo.vo.PageVO;
import ${packageName}.pojo.vo.${CName}ShowVO;
import ${packageName}.pojo.dto.${CName}AddDTO;
import ${packageName}.pojo.dto.${CName}QueryDTO;
import ${packageName}.pojo.vo.${CName}ListVO;
import ${packageName}.pojo.dto.${CName}UpdateDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
${code}
