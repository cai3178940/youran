<#include "/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("${this.commonPackage}.pojo.vo.ReplyVO")/>
<@call this.addImport("${this.packageName}.pojo.dto.${this.classNameUpper}AddDTO")/>
<@call this.addImport("${this.packageName}.pojo.po.${this.classNameUpper}PO")/>
<@call this.addImport("${this.packageName}.pojo.qo.${this.classNameUpper}QO")/>
<@call this.addImport("${this.packageName}.pojo.vo.${this.classNameUpper}ListVO")/>
<@call this.addImport("${this.packageName}.pojo.dto.${this.classNameUpper}UpdateDTO")/>
<@call this.addImport("${this.packageName}.pojo.mapper.${this.classNameUpper}Mapper")/>
<@call this.addImport("${this.packageName}.pojo.vo.${this.classNameUpper}ShowVO")/>
<@call this.addImport("${this.packageName}.web.AbstractController")/>
<@call this.addImport("${this.packageName}.web.api.${this.classNameUpper}API")/>
<@call this.addImport("org.apache.commons.lang3.ArrayUtils")/>
<@call this.addImport("org.springframework.beans.factory.annotation.Autowired")/>
<@call this.addImport("org.springframework.web.bind.annotation.*")/>
<@call this.addImport("javax.validation.Valid")/>
<@call this.printClassCom("【${this.title}】控制器")/>
@RestController
@RequestMapping("/${this.className}")
public class ${this.classNameUpper}Controller extends AbstractController implements ${this.classNameUpper}API {

    <@call this.addAutowired("${this.packageName}.service" "${this.classNameUpper}Service")/>
    <@call this.printAutowired()/>

    @Override
    @PostMapping(value = "/save")
    public ReplyVO<${this.classNameUpper}ShowVO> save(@Valid @RequestBody ${this.classNameUpper}AddDTO ${this.className}AddDTO) {
        ${this.classNameUpper}PO ${this.className} = ${this.className}Service.save(${this.className}AddDTO);
        return ReplyVO.success().data(${this.classNameUpper}Mapper.INSTANCE.toShowVO(${this.className}));
    }

    @Override
    @PutMapping(value = "/update")
    public ReplyVO<${this.classNameUpper}ShowVO> update(@Valid @RequestBody ${this.classNameUpper}UpdateDTO ${this.className}UpdateDTO) {
        ${this.classNameUpper}PO ${this.className} = ${this.className}Service.update(${this.className}UpdateDTO);
        return ReplyVO.success().data(${this.classNameUpper}Mapper.INSTANCE.toShowVO(${this.className}));
    }

<#if this.pageSign == 1>
    <@call this.addImport("${this.commonPackage}.pojo.vo.PageVO")/>
    @Override
    @GetMapping(value = "/list")
    public ReplyVO<PageVO<${this.classNameUpper}ListVO>> list(@Valid ${this.classNameUpper}QO ${this.className}QO) {
        PageVO<${this.classNameUpper}ListVO> page = ${this.className}Service.list(${this.className}QO);
        return ReplyVO.success().data(page);
    }
<#else>
    <@call this.addImport("java.util.List")/>
    @Override
    @GetMapping(value = "/list")
    public ReplyVO<List<${this.classNameUpper}ListVO>> list(@Valid ${this.classNameUpper}QO ${this.className}QO) {
        List<${this.classNameUpper}ListVO> list = ${this.className}Service.list(${this.className}QO);
        return ReplyVO.success().data(list);
    }
</#if>

    @Override
    @GetMapping(value = "/{${this.id}}")
    public ReplyVO<${this.classNameUpper}ShowVO> show(@PathVariable ${this.type} ${this.id}) {
        ${this.classNameUpper}ShowVO ${this.className}ShowVO = ${this.className}Service.show(${this.id});
        return ReplyVO.success().data(${this.className}ShowVO);
    }

    @Override
    @DeleteMapping(value = "/{${this.id}}")
    public ReplyVO<Integer> delete(@PathVariable ${this.type} ${this.id}) {
        int count = ${this.className}Service.delete(${this.id});
        return ReplyVO.success().data(count);
    }

    @Override
    @PutMapping(value = "deleteBatch")
    public ReplyVO<Integer> deleteBatch(@RequestBody ${this.type}[] id) {
        if(ArrayUtils.isEmpty(id)){
            return ReplyVO.fail("参数为空");
        }
        int count = ${this.className}Service.delete(id);
        return ReplyVO.success().data(count);
    }

<#if this.metaEntity.mtmHoldRefers??>
    <#list this.metaEntity.mtmHoldRefers as otherEntity>
        <#assign otherPk=otherEntity.pkField>
        <#assign otherCName=otherEntity.className?capFirst>
        <#assign othercName=otherEntity.className?uncapFirst>
        <#assign otherPkId=MetadataUtil.getPkAlias(othercName,false)>
    @Override
    @PutMapping(value = "/{${this.id}}/add${otherCName}/{${otherPkId}}")
    public ReplyVO<Integer> add${otherCName}(@PathVariable ${this.type} ${this.id},
                        @PathVariable ${otherPk.jfieldType} ${otherPkId}) {
        int count = ${this.className}Service.add${otherCName}(${this.id}, ${otherPkId});
        return ReplyVO.success().data(count);
    }

    @Override
    @PutMapping(value = "/{${this.id}}/add${otherCName}")
    public ReplyVO<Integer> add${otherCName}(@PathVariable ${this.type} ${this.id},
                        @RequestBody ${otherPk.jfieldType}[] ${otherPkId}) {
        int count = ${this.className}Service.add${otherCName}(${this.id}, ${otherPkId});
        return ReplyVO.success().data(count);
    }

    @Override
    @PutMapping(value = "/{${this.id}}/remove${otherCName}/{${otherPkId}}")
    public ReplyVO<Integer> remove${otherCName}(@PathVariable ${this.type} ${this.id},
                        @PathVariable ${otherPk.jfieldType} ${otherPkId}) {
        int count = ${this.className}Service.remove${otherCName}(${this.id}, ${otherPkId});
        return ReplyVO.success().data(count);
    }

    @Override
    @PutMapping(value = "/{${this.id}}/remove${otherCName}")
    public ReplyVO<Integer> remove${otherCName}(@PathVariable ${this.type} ${this.id},
                        @RequestBody ${otherPk.jfieldType}[] ${otherPkId}) {
        int count = ${this.className}Service.remove${otherCName}(${this.id}, ${otherPkId});
        return ReplyVO.success().data(count);
    }

    @Override
    @PutMapping(value = "/{${this.id}}/set${otherCName}")
    public ReplyVO<Integer> set${otherCName}(@PathVariable ${this.type} ${this.id},
        @RequestBody ${otherPk.jfieldType}[] ${otherPkId}) {
        int count = ${this.className}Service.set${otherCName}(${this.id}, ${otherPkId});
        return ReplyVO.success().data(count);
    }
    </#list>
</#if>

}

</#assign>
<#--开始渲染代码-->
package ${this.packageName}.web.rest;

<@call this.printImport()/>

${code}
