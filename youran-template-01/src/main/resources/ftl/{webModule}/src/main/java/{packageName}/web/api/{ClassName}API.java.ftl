<#include "/abstracted/common.ftl">
<#include "/abstracted/checkFeatureForRest.ftl">
<#--判断如果不需要生成当前文件，则直接跳过-->
<#if !getGenRest(this.metaEntity)>
    <@call this.skipCurrent()/>
</#if>
<#--定义主体代码-->
<#assign code>
<@call this.addImport("${this.packageName}.pojo.vo.${this.classNameUpper}ShowVO")/>
<@call this.addImport("${this.packageName}.pojo.dto.${this.classNameUpper}AddDTO")/>
<@call this.addImport("${this.packageName}.pojo.qo.${this.classNameUpper}QO")/>
<@call this.addImport("${this.packageName}.pojo.vo.${this.classNameUpper}ListVO")/>
<@call this.addImport("${this.packageName}.pojo.dto.${this.classNameUpper}UpdateDTO")/>
<@call this.addImport("io.swagger.annotations.Api")/>
<@call this.addImport("io.swagger.annotations.ApiImplicitParam")/>
<@call this.addImport("io.swagger.annotations.ApiImplicitParams")/>
<@call this.addImport("io.swagger.annotations.ApiOperation")/>
<@call this.addImport("org.springframework.http.ResponseEntity")/>
<@call this.printClassCom("【${this.title}】API" "swagger接口文档")/>
@Api(tags = "【${this.title}】API")
public interface ${this.classNameUpper}API {

<#if this.entityFeature.save>
    /**
     * 新增【${this.title}】
     */
    @ApiOperation(value="新增【${this.title}】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${this.className}AddDTO", dataType = "${this.classNameUpper}AddDTO", value = "新增【${this.title}】参数", paramType = "body"),
    })
    ResponseEntity<${this.classNameUpper}ShowVO> save(${this.classNameUpper}AddDTO ${this.className}AddDTO) throws Exception;

</#if>
<#if this.entityFeature.update>
    /**
     * 修改【${this.title}】
     */
    @ApiOperation(value="修改【${this.title}】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${this.className}UpdateDTO", dataType = "${this.classNameUpper}UpdateDTO", value = "修改【${this.title}】参数", paramType = "body"),
    })
    ResponseEntity<${this.classNameUpper}ShowVO> update(${this.classNameUpper}UpdateDTO ${this.className}UpdateDTO);

</#if>
<#if this.entityFeature.list>
    <#if this.pageSign>
        <@call this.addImport("${this.commonPackage}.pojo.vo.PageVO")/>
    /**
     * 分页查询【${this.title}】
     */
    @ApiOperation(value="分页查询【${this.title}】")
    ResponseEntity<PageVO<${this.classNameUpper}ListVO>> list(${this.classNameUpper}QO ${this.className}QO);
    <#else>
        <@call this.addImport("java.util.List")/>
    /**
     * 列表查询【${this.title}】
     */
    @ApiOperation(value="列表查询【${this.title}】")
    ResponseEntity<List<${this.classNameUpper}ListVO>> list(${this.classNameUpper}QO ${this.className}QO);
    </#if>

</#if>
<#if this.entityFeature.show>
    /**
     * 查看【${this.title}】详情
     */
    @ApiOperation(value="查看【${this.title}】详情")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${this.id}", dataType = "${JavaTemplateFunction.getSwaggerType(this.type)}", value = "【${this.title}】id", paramType = "path"),
    })
    ResponseEntity<${this.classNameUpper}ShowVO> show(${this.type} ${this.id});

</#if>
<#if this.entityFeature.delete>
    /**
     * 删除单个【${this.title}】
     */
    @ApiOperation(value="删除单个【${this.title}】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${this.id}", dataType = "${JavaTemplateFunction.getSwaggerType(this.type)}", value = "【${this.title}】id", paramType = "path"),
    })
    ResponseEntity<Integer> delete(${this.type} ${this.id});

</#if>
<#if this.entityFeature.deleteBatch>
    /**
     * 批量删除【${this.title}】
     */
    @ApiOperation(value = "批量删除【${this.title}】")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", dataType = "${JavaTemplateFunction.getSwaggerType(this.type)}", value = "id数组", paramType = "body"),
    })
    ResponseEntity<Integer> deleteBatch(${this.type}[] id);

</#if>
<#list this.holds! as otherEntity,mtm>
    <#assign otherPk=otherEntity.pkField>
    <#assign otherCName=otherEntity.className?capFirst>
    <#assign otherFkId=mtm.getFkAlias(otherEntity.entityId,false)>
    <#assign entityFeature=mtm.getEntityFeature(this.entityId)>
    <#if entityFeature.addRemove>
    /**
     * 添加【${otherEntity.title}】关联
     */
    @ApiOperation(value="添加【${otherEntity.title}】关联")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${this.id}", dataType = "${JavaTemplateFunction.getSwaggerType(this.type)}", value = "【${this.title}】id", paramType = "path"),
        @ApiImplicitParam(name = "${otherFkId}", dataType = "${JavaTemplateFunction.getSwaggerType(otherPk.jfieldType)}", value = "【${otherEntity.title}】id数组", paramType = "body"),
    })
    ResponseEntity<Integer> add${otherCName}(${this.type} ${this.id},${otherPk.jfieldType}[] ${otherFkId});

    /**
     * 移除【${otherEntity.title}】关联
     */
    @ApiOperation(value="移除【${otherEntity.title}】关联")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${this.id}", dataType = "${JavaTemplateFunction.getSwaggerType(this.type)}", value = "【${this.title}】id", paramType = "path"),
        @ApiImplicitParam(name = "${otherFkId}", dataType = "${JavaTemplateFunction.getSwaggerType(otherPk.jfieldType)}", value = "【${otherEntity.title}】id数组", paramType = "body"),
    })
    ResponseEntity<Integer> remove${otherCName}(${this.type} ${this.id},${otherPk.jfieldType}[] ${otherFkId});

    </#if>
    <#if entityFeature.set>
    /**
     * 设置【${otherEntity.title}】关联
     */
    @ApiOperation(value="设置【${otherEntity.title}】关联")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "${this.id}", dataType = "${JavaTemplateFunction.getSwaggerType(this.type)}", value = "【${this.title}】id", paramType = "path"),
        @ApiImplicitParam(name = "${otherFkId}", dataType = "${JavaTemplateFunction.getSwaggerType(otherPk.jfieldType)}", value = "【${otherEntity.title}】id数组", paramType = "body"),
    })
    ResponseEntity<Integer> set${otherCName}(${this.type} ${this.id},${otherPk.jfieldType}[] ${otherFkId});

    </#if>
</#list>

}
</#assign>
<#--开始渲染代码-->
package ${this.packageName}.web.api;

<@call this.printImport()/>

${code}
