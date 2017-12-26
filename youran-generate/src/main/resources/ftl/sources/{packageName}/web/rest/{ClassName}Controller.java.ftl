<#include "/common.ftl">
<#include "/entity_common.ftl">

<#--定义主体代码-->
<#assign code>
<@classCom "【${title}】控制器"></@classCom>
@RestController
@RequestMapping(${ProjectName}Const.${projectName?upperCase}_ROOT_PATH+"/${cName}")
public class ${CName}Controller implements ${CName}API {

    @Autowired
    private ${CName}Service ${cName}Service;

    @Override
    @PostMapping(value = "/save")
    public ReplyVO<${type}> save(@Valid @RequestBody ${CName}AddDTO ${cName}AddDTO) {
        ${CName}PO ${cName} = ${cName}Service.save(${cName}AddDTO);
        return ReplyVO.success().data(${cName}.get${Id}());
    }

    @Override
    @PutMapping(value = "/update")
    public ReplyVO<Void> update(@Valid @RequestBody ${CName}UpdateDTO ${cName}UpdateDTO) {
        ${cName}Service.update(${cName}UpdateDTO);
        return ReplyVO.success();
    }

    @Override
    @GetMapping(value = "/list")
    public ReplyVO<PageVO<${CName}ListVO>> list(@Valid ${CName}QueryDTO ${cName}QueryDTO) {
        PageVO<${CName}ListVO> page = ${cName}Service.list(${cName}QueryDTO);
        return ReplyVO.success().data(page);
    }

    @Override
    @GetMapping(value = "/{${id}}")
    public ReplyVO<${CName}ShowVO> show(@PathVariable ${type} ${id}) {
        ${CName}ShowVO ${cName}ShowVO = ${cName}Service.show(${id});
        return ReplyVO.success().data(${cName}ShowVO);
    }

    @Override
    @DeleteMapping(value = "/{${id}}")
    public ReplyVO<Integer> delete(@PathVariable ${type} ${id}) {
        int count = ${cName}Service.delete(${id});
        return ReplyVO.success().data(count);
    }

    @Override
    @PutMapping(value = "deleteBatch")
    public ReplyVO<Integer> deleteBatch(@RequestBody ${type}[] id) {
        if(ArrayUtils.isEmpty(id)){
            return ReplyVO.fail("参数为空");
        }
        int count = ${cName}Service.delete(id);
        return ReplyVO.success().data(count);
    }

<#if metaEntity.mtmHoldRefers??>
    <#list metaEntity.mtmHoldRefers as otherEntity>
        <#assign otherPk=otherEntity.pkField>
        <#assign otherCName=otherEntity.className?capFirst>
        <#assign othercName=otherEntity.className?uncapFirst>
        <#assign otherPkId=MetadataUtil.getPkAlias(othercName,false)>
    @Override
    @PutMapping(value = "/{${id}}/add${otherCName}/{${otherPkId}}")
    public ReplyVO<Integer> add${otherCName}(@PathVariable ${type} ${id},
                        @PathVariable ${otherPk.jfieldType} ${otherPkId}) {
        int count = ${cName}Service.add${otherCName}(${id}, ${otherPkId});
        return ReplyVO.success().data(count);
    }

    @Override
    @PutMapping(value = "/{${id}}/add${otherCName}")
    public ReplyVO<Integer> add${otherCName}(@PathVariable ${type} ${id},
                        @RequestBody ${otherPk.jfieldType}[] ${otherPkId}) {
        int count = ${cName}Service.add${otherCName}(${id}, ${otherPkId});
        return ReplyVO.success().data(count);
    }

    @Override
    @PutMapping(value = "/{${id}}/remove${otherCName}/{${otherPkId}}")
    public ReplyVO<Integer> remove${otherCName}(@PathVariable ${type} ${id},
                        @PathVariable ${otherPk.jfieldType} ${otherPkId}) {
        int count = ${cName}Service.remove${otherCName}(${id}, ${otherPkId});
        return ReplyVO.success().data(count);
    }

    @Override
    @PutMapping(value = "/{${id}}/remove${otherCName}")
    public ReplyVO<Integer> remove${otherCName}(@PathVariable ${type} ${id},
                        @RequestBody ${otherPk.jfieldType}[] ${otherPkId}) {
        int count = ${cName}Service.remove${otherCName}(${id}, ${otherPkId});
        return ReplyVO.success().data(count);
    }

    @Override
    @PutMapping(value = "/{${id}}/set${otherCName}")
    public ReplyVO<Integer> set${otherCName}(@PathVariable ${type} ${id},
        @RequestBody ${otherPk.jfieldType}[] ${otherPkId}) {
        int count = ${cName}Service.set${otherCName}(${id}, ${otherPkId});
        return ReplyVO.success().data(count);
    }
    </#list>
</#if>

}

</#assign>
<#--开始渲染代码-->
package ${packageName}.web.rest;

import ${commonPackage}.pojo.vo.ReplyVO;
import ${commonPackage}.pojo.vo.PageVO;
import ${packageName}.constant.${ProjectName}Const;
import ${packageName}.pojo.dto.${CName}AddDTO;
import ${packageName}.pojo.po.${CName}PO;
import ${packageName}.pojo.dto.${CName}QueryDTO;
import ${packageName}.pojo.vo.${CName}ListVO;
import ${packageName}.pojo.dto.${CName}UpdateDTO;
import ${packageName}.pojo.vo.${CName}ShowVO;
import ${packageName}.service.${CName}Service;
import ${packageName}.web.api.${CName}API;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

${code}
