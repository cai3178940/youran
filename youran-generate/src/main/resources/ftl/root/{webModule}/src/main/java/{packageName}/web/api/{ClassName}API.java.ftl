<#include "/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("${this.commonPackage}.pojo.vo.ReplyVO")/>
<@call this.addImport("${this.packageName}.pojo.vo.${this.classNameUpper}ShowVO")/>
<@call this.addImport("${this.packageName}.pojo.dto.${this.classNameUpper}AddDTO")/>
<@call this.addImport("${this.packageName}.pojo.qo.${this.classNameUpper}QO")/>
<@call this.addImport("${this.packageName}.pojo.vo.${this.classNameUpper}ListVO")/>
<@call this.addImport("${this.packageName}.pojo.dto.${this.classNameUpper}UpdateDTO")/>
<@call this.addImport("io.swagger.annotations.Api")/>
<@call this.addImport("io.swagger.annotations.ApiImplicitParam")/>
<@call this.addImport("io.swagger.annotations.ApiImplicitParams")/>
<@call this.addImport("io.swagger.annotations.ApiOperation")/>
<@call this.printClassCom("【${this.title}】API" "swagger接口文档")/>
@Api(tags = "${this.classNameUpper}", description = "${this.title}")
public interface ${this.classNameUpper}API {

    /**
     * 新增【${this.title}】
     */
    @ApiOperation(value="新增【${this.title}】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${this.className}AddDTO", dataType = "${this.classNameUpper}AddDTO", value = "新增【${this.title}】参数", paramType = "body"),
    })
    ReplyVO<${this.classNameUpper}ShowVO> save(${this.classNameUpper}AddDTO ${this.className}AddDTO);

    /**
     * 修改【${this.title}】
     */
    @ApiOperation(value="修改【${this.title}】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${this.className}UpdateDTO", dataType = "${this.classNameUpper}UpdateDTO", value = "修改【${this.title}】参数", paramType = "body"),
    })
    ReplyVO<${this.classNameUpper}ShowVO> update(${this.classNameUpper}UpdateDTO ${this.className}UpdateDTO);
<#if this.pageSign == 1>
    <@call this.addImport("${this.commonPackage}.pojo.vo.PageVO")/>
    /**
     * 分页查询【${this.title}】
     */
    @ApiOperation(value="分页查询【${this.title}】")
    ReplyVO<PageVO<${this.classNameUpper}ListVO>> list(${this.classNameUpper}QO ${this.className}QO);
<#else>
    <@call this.addImport("java.util.List")/>
    /**
     * 列表查询【${this.title}】
     */
    @ApiOperation(value="列表查询【${this.title}】")
    ReplyVO<List<${this.classNameUpper}ListVO>> list(${this.classNameUpper}QO ${this.className}QO);
</#if>
    /**
     * 查看【${this.title}】详情
     */
    @ApiOperation(value="查看【${this.title}】详情")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${this.id}", dataType = "${MetadataUtil.getSwaggerType(type)}", value = "【${this.title}】id", paramType = "path"),
    })
    ReplyVO<${this.classNameUpper}ShowVO> show(${this.type} ${this.id});

    /**
     * 删除单个【${this.title}】
     */
    @ApiOperation(value="删除单个【${this.title}】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${this.id}", dataType = "${MetadataUtil.getSwaggerType(type)}", value = "【${this.title}】id", paramType = "path"),
    })
    ReplyVO<Integer> delete(${this.type} ${this.id});

    /**
     * 批量删除【${this.title}】
     */
    @ApiOperation(value = "批量删除【${this.title}】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", dataType = "${MetadataUtil.getSwaggerType(type)}", value = "id数组", paramType = "body"),
    })
    ReplyVO<Integer> deleteBatch(${this.type}[] id);


<#if this.metaEntity.mtmHoldRefers??>
    <#list this.metaEntity.mtmHoldRefers as otherEntity>
        <#assign otherPk=otherEntity.pkField>
        <#assign otherCName=otherEntity.className?capFirst>
        <#assign othercName=otherEntity.className?uncapFirst>
        <#assign otherPkId=MetadataUtil.getPkAlias(othercName,false)>
    /**
     * 添加单个【${otherEntity.title}】关联
     */
    @ApiOperation(value="添加单个【${otherEntity.title}】关联")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${this.id}", dataType = "${MetadataUtil.getSwaggerType(type)}", value = "【${this.title}】id", paramType = "path"),
        @ApiImplicitParam(name = "${otherPkId}", dataType = "${MetadataUtil.getSwaggerType(otherPk.jfieldType)}", value = "【${otherEntity.title}】id", paramType = "path"),
    })
    ReplyVO<Integer> add${otherCName}(${this.type} ${this.id},${otherPk.jfieldType} ${otherPkId});

    /**
     * 添加多个【${otherEntity.title}】关联
     */
    @ApiOperation(value="添加多个【${otherEntity.title}】关联")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${this.id}", dataType = "${MetadataUtil.getSwaggerType(type)}", value = "【${this.title}】id", paramType = "path"),
        @ApiImplicitParam(name = "${otherPkId}", dataType = "${MetadataUtil.getSwaggerType(otherPk.jfieldType)}", value = "【${otherEntity.title}】id数组", paramType = "body"),
    })
    ReplyVO<Integer> add${otherCName}(${this.type} ${this.id},${otherPk.jfieldType}[] ${otherPkId});

    /**
     * 移除单个【${otherEntity.title}】关联
     */
    @ApiOperation(value="移除单个【${otherEntity.title}】关联")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${this.id}", dataType = "${MetadataUtil.getSwaggerType(type)}", value = "【${this.title}】id", paramType = "path"),
        @ApiImplicitParam(name = "${otherPkId}", dataType = "${MetadataUtil.getSwaggerType(otherPk.jfieldType)}", value = "【${otherEntity.title}】id", paramType = "path"),
    })
    ReplyVO<Integer> remove${otherCName}(${this.type} ${this.id},${otherPk.jfieldType} ${otherPkId});

    /**
     * 移除多个【${otherEntity.title}】关联
     */
    @ApiOperation(value="移除多个【${otherEntity.title}】关联")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${this.id}", dataType = "${MetadataUtil.getSwaggerType(type)}", value = "【${this.title}】id", paramType = "path"),
        @ApiImplicitParam(name = "${otherPkId}", dataType = "${MetadataUtil.getSwaggerType(otherPk.jfieldType)}", value = "【${otherEntity.title}】id数组", paramType = "body"),
    })
    ReplyVO<Integer> remove${otherCName}(${this.type} ${this.id},${otherPk.jfieldType}[] ${otherPkId});

    /**
     * 设置【${otherEntity.title}】关联
     */
    @ApiOperation(value="设置【${otherEntity.title}】关联")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${this.id}", dataType = "${MetadataUtil.getSwaggerType(type)}", value = "【${this.title}】id", paramType = "path"),
        @ApiImplicitParam(name = "${otherPkId}", dataType = "${MetadataUtil.getSwaggerType(otherPk.jfieldType)}", value = "【${otherEntity.title}】id数组", paramType = "body"),
    })
    ReplyVO<Integer> set${otherCName}(${this.type} ${this.id},${otherPk.jfieldType}[] ${otherPkId});

    </#list>
</#if>

}
</#assign>
<#--开始渲染代码-->
package ${this.packageName}.web.api;

<@call this.printImport()/>

${code}
