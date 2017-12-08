<#include "/common.ftl">
<#include "/entity_common.ftl">

<#--定义主体代码-->
<#assign code>
<@classCom "【${title}】控制器"></@classCom>
@RestController
@RequestMapping(${ProjectName}Const.${projectName?upper_case}_ROOT_PATH+"/${cName}")
public class ${CName}Controller implements ${CName}API {

    @Autowired
    private ${CName}Service ${cName}Service;

    @Override
    @PostMapping(value = "/save")
    public ReplyVO<${type}> save(@Valid @RequestBody ${CName}AddDTO ${cName}AddDTO) {
        ${CName}PO ${cName} = ${cName}Service.save(${cName}AddDTO);
        ReplyVO<${type}> replyVO = ReplyVO.success();
        replyVO.setData(${cName}.get${Id}());
        return replyVO;
    }

    @Override
    @PutMapping(value = "/update")
    public ReplyVO<Void> update(@Valid @RequestBody ${CName}UpdateDTO ${cName}UpdateDTO) {
        ${cName}Service.update(${cName}UpdateDTO);
        ReplyVO<Void> replyVO = ReplyVO.success();
        return replyVO;
    }

    @Override
    @PostMapping(value = "/list")
    public ReplyVO<PageVO<${CName}ListVO>> list(@Valid @RequestBody ${CName}QueryDTO ${cName}QueryDTO) {
        PageVO<${CName}ListVO> page = ${cName}Service.list(${cName}QueryDTO);
        ReplyVO<PageVO<${CName}ListVO>> replyVO = ReplyVO.success();
        replyVO.setData(page);
        return replyVO;
    }

    @Override
    @GetMapping(value = "/{${id}}")
    public ReplyVO<${CName}ShowVO> show(@PathVariable ${type} ${id}) {
        ${CName}ShowVO ${cName}ShowVO = ${cName}Service.show(${id});
        ReplyVO<${CName}ShowVO> replyVO = ReplyVO.success();
        replyVO.setData(${cName}ShowVO);
        return replyVO;
    }

    @Override
    @DeleteMapping(value = "/{${id}}")
    public ReplyVO<Integer> delete(@PathVariable ${type} ${id}) {
        int count = ${cName}Service.delete(${id});
        ReplyVO<Integer> replyVO = ReplyVO.success();
        replyVO.setData(count);
        return replyVO;
    }

    @Override
    @PostMapping(value = "deleteBatch")
    public ReplyVO<Integer> deleteBatch(@RequestBody ${type}[] id) {
        if(ArrayUtils.isEmpty(id)){
            return ReplyVO.fail("参数为空");
        }
        int count = ${cName}Service.delete(id);
        ReplyVO<Integer> result = ReplyVO.success();
        result.setData(count);
        return result;
    }

<#if metaEntity.mtmHoldRefers??>
    <#list metaEntity.mtmHoldRefers as otherEntity>
        <#assign otherPk=otherEntity.pkField>
    @Override
    @PutMapping(value = "/{${id}}/add${otherEntity.className}/{${otherPk.jfieldName}}")
    public ReplyVO<Integer> add${otherEntity.className}(@PathVariable ${type} ${id},
                                                       @PathVariable ${otherPk.jfieldType} ${otherPk.jfieldName}) {
        int count = ${cName}Service.add${otherEntity.className}(${id}, ${otherPk.jfieldName});
        ReplyVO<Integer> replyVO = ReplyVO.success();
        replyVO.setData(count);
        return replyVO;
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
