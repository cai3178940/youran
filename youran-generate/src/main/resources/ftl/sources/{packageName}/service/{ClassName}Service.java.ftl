<#include "/common.ftl">
<#include "/entity_common.ftl">

<#assign importOptimisticLock=false>
<#--定义主体代码-->
<#assign code>
<@classCom "【${title}】删改查服务"></@classCom>
@Service
public class ${CName}Service {

    @Autowired
    private ${CName}DAO ${cName}DAO;

    /**
     * 新增【${title}】
     * @param ${cName}DTO
     * @return
     */
    @Transactional
    public ${CName}PO save(${CName}AddDTO ${cName}DTO) {
        ${CName}PO ${cName} = ${CName}Mapper.INSTANCE.fromAddDTO(${cName}DTO);
        ${cName}DAO.save(${cName});
        return ${cName};
    }

    /**
     * 修改【${title}】
     * @param ${cName}UpdateDTO
     * @return
     */
    @Transactional
    <#if metaEntity.versionField??>
        <#assign importOptimisticLock=true>
    @OptimisticLock
    </#if>
    public void update(${CName}UpdateDTO ${cName}UpdateDTO) {
        ${type} ${id} = ${cName}UpdateDTO.get${Id}();
        ${CName}PO ${cName} = ${cName}DAO.findById(${id});
        if(${cName}==null){
            throw new ${ProjectName}Exception("${id}有误");
        }
        ${CName}Mapper.INSTANCE.setUpdateDTO(${cName},${cName}UpdateDTO);
        ${cName}DAO.update(${cName});
    }

    /**
     * 查询分页列表
     * @param ${cName}QueryDTO
     * @return
     */
    public PageVO<${CName}ListVO> list(${CName}QueryDTO ${cName}QueryDTO) {
        PageVO<${CName}ListVO> page = ${cName}DAO.findByPage(${cName}QueryDTO);
        return page;
    }

    /**
     * 查询【${title}】详情
     * @param ${id}
     * @return
     */
    public ${CName}ShowVO show(${type} ${id}) {
        ${CName}PO ${cName} = ${cName}DAO.findById(${id});
        if(${cName}==null){
            throw new ${ProjectName}Exception("未查询到记录");
        }
        ${CName}ShowVO showVO = ${CName}Mapper.INSTANCE.toShowVO(${cName});
        return showVO;
    }

    /**
     * 删除【${title}】
     * @param ${id}s
     * @return
     */
    @Transactional
    public int delete(${type}... ${id}s) {
        int count = 0;
        for (${type} ${id} : ${id}s) {
        <#if metaEntity.delField??>
            count += ${cName}DAO.delete(${id});
        <#else>
            count += ${cName}DAO.physicalDelete(${id});
        </#if>
        }
        return count;
    }

<#if metaEntity.mtmHoldRefers??>
    <#list metaEntity.mtmHoldRefers as otherEntity>
        <#assign otherPk=otherEntity.pkField>
    @Transactional
    public int add${otherEntity.className}(${type} ${id}, ${otherPk.jfieldType} ${otherPk.jfieldName}) {
        ${CName}PO ${cName} = ${cName}DAO.findById(${id});
        if(${cName}==null){
            throw new ${ProjectName}Exception("未查询到记录");
        }
        return ${cName}DAO.add${otherEntity.className}(${id},${otherPk.jfieldName});
    }
    </#list>
</#if>

}

</#assign>
<#--开始渲染代码-->
package ${packageName}.service;

import ${commonPackage}.pojo.vo.PageVO;
import ${packageName}.dao.${CName}DAO;
import ${packageName}.pojo.dto.${CName}AddDTO;
import ${packageName}.pojo.dto.${CName}QueryDTO;
import ${packageName}.pojo.vo.${CName}ListVO;
import ${packageName}.pojo.dto.${CName}UpdateDTO;
import ${packageName}.pojo.mapper.${CName}Mapper;
import ${packageName}.pojo.po.${CName}PO;
import ${packageName}.pojo.vo.${CName}ShowVO;
import ${packageName}.exception.${ProjectName}Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
<#if importOptimisticLock>
import ${commonPackage}.optimistic.OptimisticLock;
</#if>

${code}
