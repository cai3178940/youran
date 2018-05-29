<#include "/common.ftl">
<#include "/entity_common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "${packageName}.pojo.dto.${CName}AddDTO"/>
<@import "${packageName}.pojo.qo.${CName}QO"/>
<@import "${packageName}.pojo.vo.${CName}ListVO"/>
<@import "${packageName}.pojo.dto.${CName}UpdateDTO"/>
<@import "${packageName}.pojo.mapper.${CName}Mapper"/>
<@import "${packageName}.pojo.po.${CName}PO"/>
<@import "${packageName}.pojo.vo.${CName}ShowVO"/>
<@import "${packageName}.exception.${ProjectName}Exception"/>
<@import "org.springframework.beans.factory.annotation.Autowired"/>
<@import "org.springframework.stereotype.Service"/>
<@import "org.springframework.transaction.annotation.Transactional"/>
<@classCom "【${title}】删改查服务"/>
@Service
public class ${CName}Service {

    <@autowired "${packageName}.dao" "${CName}DAO"/>
<#if metaEntity.mtmHoldRefers??>
    <#list metaEntity.mtmHoldRefers as otherEntity>
        <#assign otherCName=otherEntity.className?capFirst>
        <@autowired "${packageName}.dao" "${otherCName}DAO"/>
    </#list>
</#if>
<#if metaEntity.mtmUnHoldRefers??>
    <#list metaEntity.mtmUnHoldRefers as otherEntity>
        <#assign otherCName=otherEntity.className?capFirst>
        <@autowired "${packageName}.dao" "${otherCName}DAO"/>
    </#list>
</#if>

<#-- 引入外键对应的DAO -->
<#list insertFields as field>
    <#if field.foreignKey==1>
        <#assign foreignCName=field.foreignEntity.className?capFirst>
        <@autowired "${packageName}.dao" "${foreignCName}DAO"/>
    </#if>
</#list>
<#list updateFields as field>
    <#if field.foreignKey==1>
        <#assign foreignCName=field.foreignEntity.className?capFirst>
        <@autowired "${packageName}.dao" "${foreignCName}DAO"/>
    </#if>
</#list>
<#if metaEntity.foreignEntities??>
    <#list metaEntity.foreignEntities as foreignEntity>
        <#assign foreignCName=foreignEntity.className?capFirst>
        <@autowired "${packageName}.dao" "${foreignCName}DAO"/>
    </#list>
</#if>
<#list fields as field>
    <#if field.foreignKey==1 && field.cascadeExts?size &gt; 0>
        <#list field.cascadeExts as cascadeExt>
            <#if cascadeExt.list==1>
                <@autowired "${packageName}.dao" "${field.foreignEntity.className?capFirst}DAO"/>
                <#break>
            </#if>
        </#list>
    </#if>
</#list>

<#macro checkForeignKeys fields>
    <#list fields as field>
        <#if field.foreignKey==1>
            <@import "org.springframework.util.Assert"/>
            <#assign foreigncName=field.foreignEntity.className?uncapFirst>
        if(${cName}.get${field.jfieldName?capFirst}() != null){
            Assert.isTrue(${foreigncName}DAO.exist(${cName}.get${field.jfieldName?capFirst}()),"${field.fieldDesc}有误");
        }
        </#if>
    </#list>
</#macro>

    /**
     * 新增【${title}】
     * @param ${cName}DTO
     * @return
     */
    @Transactional
    public ${CName}PO save(${CName}AddDTO ${cName}DTO) {
        ${CName}PO ${cName} = ${CName}Mapper.INSTANCE.fromAddDTO(${cName}DTO);
        <@checkForeignKeys insertFields/>
        ${cName}DAO.save(${cName});
<#if metaEntity.mtmHoldRefers??>
    <#list metaEntity.mtmHoldRefers as otherEntity>
        <@import "java.util.List"/>
        <@import "org.apache.commons.collections.CollectionUtils"/>
        <#assign otherPk=otherEntity.pkField>
        <#assign otherCName=otherEntity.className?capFirst>
        <#assign othercName=otherEntity.className?uncapFirst>
        List<${otherPk.jfieldType}> ${othercName}List = ${cName}DTO.get${otherCName}List();
        if(CollectionUtils.isNotEmpty(${othercName}List)) {
            this.doAdd${otherCName}(${cName}.get${Id}(), ${othercName}List.toArray(new ${otherPk.jfieldType}[]{}));
        }
    </#list>
</#if>
        return ${cName};
    }

    /**
     * 修改【${title}】
     * @param ${cName}UpdateDTO
     * @return
     */
    @Transactional
    <#if metaEntity.versionField??>
        <@import "${commonPackage}.optimistic.OptimisticLock"/>
    @OptimisticLock
    </#if>
    public void update(${CName}UpdateDTO ${cName}UpdateDTO) {
        ${type} ${id} = ${cName}UpdateDTO.get${Id}();
        ${CName}PO ${cName} = this.get${CName}(${id}, true);
        ${CName}Mapper.INSTANCE.setUpdateDTO(${cName},${cName}UpdateDTO);
        <@checkForeignKeys updateFields/>
        ${cName}DAO.update(${cName});
<#if metaEntity.mtmHoldRefers??>
    <#list metaEntity.mtmHoldRefers as otherEntity>
        <@import "java.util.List"/>
        <@import "org.apache.commons.collections.CollectionUtils"/>
        <#assign otherPk=otherEntity.pkField>
        <#assign otherCName=otherEntity.className?capFirst>
        <#assign othercName=otherEntity.className?uncapFirst>
        List<${otherPk.jfieldType}> ${othercName}List = ${cName}UpdateDTO.get${otherCName}List();
        if(${othercName}List != null) {
            this.set${otherCName}(${cName}.get${Id}(), ${othercName}List.toArray(new ${otherPk.jfieldType}[]{}));
        }
    </#list>
</#if>
    }
<#if pageSign == 1>
    <@import "${commonPackage}.pojo.vo.PageVO"/>
    /**
     * 查询分页列表
     * @param ${cName}QO
     * @return
     */
    public PageVO<${CName}ListVO> list(${CName}QO ${cName}QO) {
        PageVO<${CName}ListVO> page = ${cName}DAO.findByPage(${cName}QO);
        return page;
    }
<#else>
    <@import "java.util.List"/>
    /**
     * 查询列表
     * @param ${cName}QO
     * @return
     */
    public List<${CName}ListVO> list(${CName}QO ${cName}QO) {
        List<${CName}ListVO> list = ${cName}DAO.findListByQuery(${cName}QO);
        return list;
    }
</#if>


    /**
     * 根据主键获取【${title}】
     * @param ${id}
     * @param force
     * @return
     */
    public ${CName}PO get${CName}(${type} ${id}, boolean force){
        ${CName}PO ${cName} = ${cName}DAO.findById(${id});
        if (force && ${cName} == null) {
            throw new ${ProjectName}Exception("未查询到记录");
        }
        return ${cName};
    }


    /**
     * 查询【${title}】详情
     * @param ${id}
     * @return
     */
    public ${CName}ShowVO show(${type} ${id}) {
        ${CName}PO ${cName} = this.get${CName}(${id}, true);
        ${CName}ShowVO showVO = ${CName}Mapper.INSTANCE.toShowVO(${cName});
<#list fields as field>
    <#if field.foreignKey==1 && field.cascadeExts?size &gt; 0>
        <#assign ifBreak=true>
        <#list field.cascadeExts as cascadeExt>
            <#if cascadeExt.show==1>
                <#assign ifBreak=false>
                <#break>
            </#if>
        </#list>
        <#if ifBreak>
            <#break>
        </#if>
        <#assign otherCName=field.foreignEntity.className?capFirst>
        <#assign othercName=field.foreignEntity.className?uncapFirst>
        <@import "${packageName}.pojo.po.${otherCName}PO"/>
        if(${cName}.get${field.jfieldName?capFirst}()!=null){
            ${otherCName}PO _${othercName}PO = ${othercName}DAO.findById(${cName}.get${field.jfieldName?capFirst}());
        <#list field.cascadeExts as cascadeExt>
            <#if cascadeExt.show==1>
            showVO.set${cascadeExt.alias?capFirst}(_${othercName}PO.get${cascadeExt.cascadeField.jfieldName?capFirst}());
            </#if>
        </#list>
        }
    </#if>
</#list>
<#if metaEntity.mtmHoldRefers??>
    <#list metaEntity.mtmHoldRefers as otherEntity>
        <#assign otherCName=otherEntity.className?capFirst>
        <#assign othercName=otherEntity.className?uncapFirst>
        //设置【${otherEntity.title}】列表
        showVO.set${otherCName}List(${othercName}DAO.findVOBy${CName}(${id}));
    </#list>
</#if>
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
    <#if metaEntity.foreignEntities??>
        <#list metaEntity.foreignEntities as foreignEntity>
            <#assign foreignCName=foreignEntity.className?capFirst>
            this.checkDeleteBy${foreignCName}(${id});
        </#list>
    </#if>
    <#if metaEntity.mtmUnHoldRefers??>
        <#list metaEntity.mtmUnHoldRefers as otherEntity>
            <#assign otherCName=otherEntity.className?capFirst>
            //校验是否存在【${otherEntity.title}】关联
            this.checkDeleteBy${otherCName}(${id});
        </#list>
    </#if>
            count += ${cName}DAO.delete(${id});
        }
        return count;
    }

<#if metaEntity.foreignEntities??>
    <#list metaEntity.foreignEntities as foreignEntity>
        <#assign foreignCName=foreignEntity.className?capFirst>
        <#assign foreigncName=foreignEntity.className?uncapFirst>
        <#assign alreadyFind=false>
    /**
     * 校验是否存在【${foreignEntity.title}】关联
     * @param ${id}
     */
    private void checkDeleteBy${foreignCName}(${type} ${id}) {
        <#list metaEntity.foreignFields as foreignField>
            <#if foreignField.entityId==foreignEntity.entityId>
        <#if !alreadyFind>int </#if>count = ${foreigncName}DAO.getCountBy${foreignField.jfieldName?capFirst}(${id});
        if(count>0){
            throw new ${ProjectName}Exception("${title}和${foreignEntity.title}存在关联关系，删除失败");
        }
                <#assign alreadyFind=true>
            </#if>
        </#list>
    }

    </#list>
</#if>
<#if metaEntity.mtmUnHoldRefers??>
    <#list metaEntity.mtmUnHoldRefers as otherEntity>
        <#assign otherCName=otherEntity.className?capFirst>
        <#assign othercName=otherEntity.className?uncapFirst>
    /**
     * 校验是否存在【${otherEntity.title}】关联
     * @param ${id}
     */
    private void checkDeleteBy${otherCName}(${type} ${id}) {
        int count = ${othercName}DAO.getCountBy${CName}(${id});
        if(count>0){
            throw new ${ProjectName}Exception("${title}和${otherEntity.title}存在关联关系，删除失败");
        }
    }

    </#list>
</#if>
<#if metaEntity.mtmHoldRefers??>
    <#list metaEntity.mtmHoldRefers as otherEntity>
        <@import "org.apache.commons.lang3.ArrayUtils"/>
        <#assign otherPk=otherEntity.pkField>
        <#assign otherCName=otherEntity.className?capFirst>
        <#assign othercName=otherEntity.className?uncapFirst>
        <#assign otherPkId=MetadataUtil.getPkAlias(othercName,false)>
    /**
     * 执行【${otherEntity.title}】添加
     * @param ${id}
     * @param ${otherPkId}
     * @return
     */
    private int doAdd${otherCName}(${type} ${id}, ${otherPk.jfieldType}... ${otherPkId}) {
        int count = 0;
        for (${otherPk.jfieldType} _id : ${otherPkId}) {
            if(${othercName}DAO.exist(_id)){
                count += ${cName}DAO.add${otherCName}(${id},_id);
            }
        }
        return count;
    }

    /**
     * 添加【${otherEntity.title}】
     * @param ${id}
     * @param ${otherPkId}
     * @return
     */
    @Transactional
    public int add${otherCName}(${type} ${id}, ${otherPk.jfieldType}... ${otherPkId}) {
        ${CName}PO ${cName} = this.get${CName}(${id}, true);
        if(ArrayUtils.isEmpty(${otherPkId})){
            throw new ${ProjectName}Exception("${otherEntity.title}id参数为空");
        }
        return doAdd${otherCName}(${id}, ${otherPkId});
    }

    /**
     * 移除【${otherEntity.title}】
     * @param ${id}
     * @param ${otherPkId}
     * @return
     */
    @Transactional
    public int remove${otherCName}(${type} ${id}, ${otherPk.jfieldType}... ${otherPkId}) {
        ${CName}PO ${cName} = this.get${CName}(${id}, true);
        if(ArrayUtils.isEmpty(${otherPkId})){
            throw new ${ProjectName}Exception("${otherEntity.title}id参数为空");
        }
        return ${cName}DAO.remove${otherCName}(${id}, ${otherPkId});
    }

    /**
     * 设置【${otherEntity.title}】
     * @param ${id}
     * @param ${otherPkId}
     * @return
     */
    @Transactional
    public int set${otherCName}(${type} ${id}, ${otherPk.jfieldType}[] ${otherPkId}) {
        ${CName}PO ${cName} = this.get${CName}(${id}, true);
        ${cName}DAO.removeAll${otherCName}(${id});
        if(ArrayUtils.isEmpty(${otherPkId})){
            return 0;
        }
        return doAdd${otherCName}(${id}, ${otherPkId});
    }

    </#list>
</#if>

}

</#assign>
<#--开始渲染代码-->
package ${packageName}.service;

<@printImport/>

${code}
