<#include "/common.ftl">
<#include "/entity_common.ftl">
<#include "/import.ftl">
<#--定义主体代码-->
<#assign code>
<@import "${packageName}.dao.${CName}DAO"/>
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

    @Autowired
    private ${CName}DAO ${cName}DAO;
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


<#macro checkForeignKeys fields>
    <#list fields as field>
        <#if field.foreignKey==1>
            <@import "com.jd.jim.cli.driver.util.Assert"/>
            <#assign foreignCName=field.foreignEntity.className?capFirst>
            <#if field.foreignField.primaryKey==1>
        if(${cName}.get${field.jfieldName?capFirst}() != null){
            Assert.isTrue(${foreignCName}DAO.exist(${cName}.get${field.jfieldName?capFirst}()),"${field.fieldDesc}有误");
        }
            <#else>
            <#-- // TODO 校验非主键是否存在 -->
            </#if>
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
    <#if metaEntity.mtmUnHoldRefers??>
        <#list metaEntity.mtmUnHoldRefers as otherEntity>
            <#assign otherCName=otherEntity.className?capFirst>
            //校验是否存在【${otherEntity.title}】绑定
            this.check${otherCName}(${id});
        </#list>
    </#if>
        <#if metaEntity.delField??>
            count += ${cName}DAO.delete(${id});
        <#else>
            count += ${cName}DAO.physicalDelete(${id});
        </#if>
        }
        return count;
    }

<#if metaEntity.mtmUnHoldRefers??>
    <#list metaEntity.mtmUnHoldRefers as otherEntity>
        <#assign otherCName=otherEntity.className?capFirst>
        <#assign othercName=otherEntity.className?uncapFirst>
    /**
     * 校验是否存在【${otherEntity.title}】绑定
     * @param ${id}
     */
    private void check${otherCName}(${type} ${id}) {
        int count = ${othercName}DAO.getCountBy${CName}(${id});
        if(count>0){
            throw new ${ProjectName}Exception("${title}和${otherEntity.title}存在依赖关系，请先解除依赖");
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
        for (Integer _id : ${otherPkId}) {
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
