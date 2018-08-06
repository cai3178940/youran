<#include "/common.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("${this.commonPackage}.constant.ErrorCode")/>
<@call this.addImport("${this.packageName}.pojo.dto.${this.classNameUpper}AddDTO")/>
<@call this.addImport("${this.packageName}.pojo.qo.${this.classNameUpper}QO")/>
<@call this.addImport("${this.packageName}.pojo.vo.${this.classNameUpper}ListVO")/>
<@call this.addImport("${this.packageName}.pojo.dto.${this.classNameUpper}UpdateDTO")/>
<@call this.addImport("${this.packageName}.pojo.mapper.${this.classNameUpper}Mapper")/>
<@call this.addImport("${this.packageName}.pojo.po.${this.classNameUpper}PO")/>
<@call this.addImport("${this.packageName}.pojo.vo.${this.classNameUpper}ShowVO")/>
<@call this.addImport("${this.packageName}.exception.${this.projectNameUpper}Exception")/>
<@call this.addImport("org.springframework.beans.factory.annotation.Autowired")/>
<@call this.addImport("org.springframework.stereotype.Service")/>
<@call this.addImport("org.springframework.transaction.annotation.Transactional")/>
<@call this.printClassCom("【${this.title}】删改查服务")/>
@Service
public class ${this.classNameUpper}Service {

    <@call this.addAutowired("${this.packageName}.dao" "${this.classNameUpper}DAO")/>
<#if this.metaEntity.mtmHoldRefers??>
    <#list this.metaEntity.mtmHoldRefers as otherEntity>
        <#assign otherCName=otherEntity.className?capFirst>
        <@call this.addAutowired("${this.packageName}.dao" "${otherCName}DAO")/>
    </#list>
</#if>
<#if this.metaEntity.mtmUnHoldRefers??>
    <#list this.metaEntity.mtmUnHoldRefers as otherEntity>
        <#assign otherCName=otherEntity.className?capFirst>
        <@call this.addAutowired("${this.packageName}.dao" "${otherCName}DAO")/>
    </#list>
</#if>
<#-- 引入外键对应的DAO -->
<#list this.insertFields as field>
    <#if field.foreignKey==1>
        <#assign foreignCName=field.foreignEntity.className?capFirst>
        <@call this.addAutowired("${this.packageName}.dao" "${foreignCName}DAO")/>
    </#if>
</#list>
<#list this.updateFields as field>
    <#if field.foreignKey==1>
        <#assign foreignCName=field.foreignEntity.className?capFirst>
        <@call this.addAutowired("${this.packageName}.dao" "${foreignCName}DAO")/>
    </#if>
</#list>
<#if this.metaEntity.foreignEntities??>
    <#list this.metaEntity.foreignEntities as foreignEntity>
        <#assign foreignCName=foreignEntity.className?capFirst>
        <@call this.addAutowired("${this.packageName}.dao" "${foreignCName}DAO")/>
    </#list>
</#if>
<#list this.fields as field>
    <#if field.cascadeListExts?? && field.cascadeListExts?size &gt; 0>
        <@call this.addAutowired("${this.packageName}.dao" "${field.foreignEntity.className?capFirst}DAO")/>
    </#if>
</#list>

    <@call this.printAutowired()/>


<#if this.metaEntity.checkUniqueIndexes?? && metaEntity.checkUniqueIndexes?size &gt; 0>
    /**
     * 校验数据唯一性
     * @param ${this.className}
     * @param isUpdate 是否更新校验
     */
    private void checkUnique(${this.classNameUpper}PO ${this.className}, boolean isUpdate){
    <#list this.metaEntity.checkUniqueIndexes as index>
        <#assign suffix=(index_index==0)?string('',''+index_index)>
        <#assign params=''>
        <#list index.fields as field>
            <#assign params+=this.className+'.get'+field.jfieldName?capFirst+'(), '>
        </#list>
        if(${this.className}DAO.notUnique${suffix}(${params}isUpdate?${this.className}.get${this.idUpper}():null)){
            throw new ${this.projectNameUpper}Exception(ErrorCode.DUPLICATE_KEY);
        }
    </#list>
    }

</#if>
<#macro checkForeignKeys fields>
    <#list this.fields as field>
        <#if field.foreignKey==1>
            <@call this.addImport("org.springframework.util.Assert")/>
            <#assign foreigncName=field.foreignEntity.className?uncapFirst>
        if(${this.className}.get${field.jfieldName?capFirst}() != null){
            Assert.isTrue(${foreigncName}DAO.exist(${this.className}.get${field.jfieldName?capFirst}()),"${field.fieldDesc}有误");
        }
        </#if>
    </#list>
</#macro>

    /**
     * 新增【${this.title}】
     * @param ${this.className}DTO
     * @return
     */
    @Transactional
    public ${this.classNameUpper}PO save(${this.classNameUpper}AddDTO ${this.className}DTO) {
        ${this.classNameUpper}PO ${this.className} = ${this.classNameUpper}Mapper.INSTANCE.fromAddDTO(${this.className}DTO);
        <@checkForeignKeys this.insertFields/>
<#if this.metaEntity.checkUniqueIndexes?? && this.metaEntity.checkUniqueIndexes?size &gt; 0>
        //唯一性校验
        this.checkUnique(${this.className},false);
</#if>
        ${this.className}DAO.save(${this.className});
<#if this.metaEntity.mtmHoldRefers??>
    <#list this.metaEntity.mtmHoldRefers as otherEntity>
        <@call this.addImport("java.util.List")/>
        <@call this.addImport("org.apache.commons.collections.CollectionUtils")/>
        <#assign otherPk=otherEntity.pkField>
        <#assign otherCName=otherEntity.className?capFirst>
        <#assign othercName=otherEntity.className?uncapFirst>
        List<${otherPk.jfieldType}> ${othercName}List = ${this.className}DTO.get${otherCName}List();
        if(CollectionUtils.isNotEmpty(${othercName}List)) {
            this.doAdd${otherCName}(${this.className}.get${this.idUpper}(), ${othercName}List.toArray(new ${otherPk.jfieldType}[]{}));
        }
    </#list>
</#if>
        return ${this.className};
    }

    /**
     * 修改【${this.title}】
     * @param ${this.className}UpdateDTO
     * @return
     */
    @Transactional
    <#if this.metaEntity.versionField??>
        <@call this.addImport("${this.commonPackage}.optimistic.OptimisticLock")/>
    @OptimisticLock
    </#if>
    public ${this.classNameUpper}PO update(${this.classNameUpper}UpdateDTO ${this.className}UpdateDTO) {
        ${this.type} ${this.id} = ${this.className}UpdateDTO.get${this.idUpper}();
        ${this.classNameUpper}PO ${this.className} = this.get${this.classNameUpper}(${this.id}, true);
        ${this.classNameUpper}Mapper.INSTANCE.setUpdateDTO(${this.className},${this.className}UpdateDTO);
        <@checkForeignKeys this.updateFields/>
<#if this.metaEntity.checkUniqueIndexes?? && this.metaEntity.checkUniqueIndexes?size &gt; 0>
        //唯一性校验
        this.checkUnique(${this.className},true);
</#if>
        ${this.className}DAO.update(${this.className});
<#if this.metaEntity.mtmHoldRefers??>
    <#list this.metaEntity.mtmHoldRefers as otherEntity>
        <@call this.addImport("java.util.List")/>
        <@call this.addImport("org.apache.commons.collections.CollectionUtils")/>
        <#assign otherPk=otherEntity.pkField>
        <#assign otherCName=otherEntity.className?capFirst>
        <#assign othercName=otherEntity.className?uncapFirst>
        List<${otherPk.jfieldType}> ${othercName}List = ${this.className}UpdateDTO.get${otherCName}List();
        if(${othercName}List != null) {
            this.set${otherCName}(${this.className}.get${this.idUpper}(), ${othercName}List.toArray(new ${otherPk.jfieldType}[]{}));
        }
    </#list>
</#if>
        return ${this.className};
    }
<#if this.pageSign == 1>
    <@call this.addImport("${this.commonPackage}.pojo.vo.PageVO")/>
    /**
     * 查询分页列表
     * @param ${this.className}QO
     * @return
     */
    public PageVO<${this.classNameUpper}ListVO> list(${this.classNameUpper}QO ${this.className}QO) {
        PageVO<${this.classNameUpper}ListVO> page = ${this.className}DAO.findByPage(${this.className}QO);
        return page;
    }
<#else>
    <@call this.addImport("java.util.List")/>
    /**
     * 查询列表
     * @param ${this.className}QO
     * @return
     */
    public List<${this.classNameUpper}ListVO> list(${this.classNameUpper}QO ${this.className}QO) {
        List<${this.classNameUpper}ListVO> list = ${this.className}DAO.findListByQuery(${this.className}QO);
        return list;
    }
</#if>


    /**
     * 根据主键获取【${this.title}】
     * @param ${this.id}
     * @param force
     * @return
     */
    public ${this.classNameUpper}PO get${this.classNameUpper}(${this.type} ${this.id}, boolean force){
        ${this.classNameUpper}PO ${this.className} = ${this.className}DAO.findById(${this.id});
        if (force && ${this.className} == null) {
            throw new ${this.projectNameUpper}Exception(ErrorCode.RECORD_NOT_FIND);
        }
        return ${this.className};
    }


    /**
     * 查询【${this.title}】详情
     * @param ${this.id}
     * @return
     */
    public ${this.classNameUpper}ShowVO show(${this.type} ${this.id}) {
        ${this.classNameUpper}PO ${this.className} = this.get${this.classNameUpper}(${this.id}, true);
        ${this.classNameUpper}ShowVO showVO = ${this.classNameUpper}Mapper.INSTANCE.toShowVO(${this.className});
<#list this.fields as field>
    <#if field.cascadeShowExts?? && field.cascadeShowExts?size &gt; 0>
        <#assign otherCName=field.foreignEntity.className?capFirst>
        <#assign othercName=field.foreignEntity.className?uncapFirst>
        <@call this.addImport("${this.packageName}.pojo.po.${otherCName}PO")/>
        if(${this.className}.get${field.jfieldName?capFirst}()!=null){
            ${otherCName}PO _${othercName}PO = ${othercName}DAO.findById(${this.className}.get${field.jfieldName?capFirst}());
        <#list field.cascadeShowExts as cascadeExt>
            showVO.set${cascadeExt.alias?capFirst}(_${othercName}PO.get${cascadeExt.cascadeField.jfieldName?capFirst}());
        </#list>
        }
    </#if>
</#list>
<#if this.metaEntity.mtmHoldRefers??>
    <#list this.metaEntity.mtmHoldRefers as otherEntity>
        <#assign otherCName=otherEntity.className?capFirst>
        <#assign othercName=otherEntity.className?uncapFirst>
        //设置【${otherEntity.title}】列表
        showVO.set${otherCName}List(${othercName}DAO.findVOBy${this.classNameUpper}(${this.id}));
    </#list>
</#if>
        return showVO;
    }

    /**
     * 删除【${this.title}】
     * @param ${this.id}s
     * @return
     */
    @Transactional
    public int delete(${this.type}... ${this.id}s) {
        int count = 0;
        for (${this.type} ${this.id} : ${this.id}s) {
    <#if this.metaEntity.foreignEntities??>
        <#list this.metaEntity.foreignEntities as foreignEntity>
            <#assign foreignCName=foreignEntity.className?capFirst>
            this.checkDeleteBy${foreignCName}(${this.id});
        </#list>
    </#if>
    <#if this.metaEntity.mtmUnHoldRefers??>
        <#list this.metaEntity.mtmUnHoldRefers as otherEntity>
            <#assign otherCName=otherEntity.className?capFirst>
            //校验是否存在【${otherEntity.title}】关联
            this.checkDeleteBy${otherCName}(${this.id});
        </#list>
    </#if>
            count += ${this.className}DAO.delete(${this.id});
        }
        return count;
    }

<#if this.metaEntity.foreignEntities??>
    <#list this.metaEntity.foreignEntities as foreignEntity>
        <#assign foreignCName=foreignEntity.className?capFirst>
        <#assign foreigncName=foreignEntity.className?uncapFirst>
        <#assign alreadyFind=false>
    /**
     * 校验是否存在【${foreignEntity.title}】关联
     * @param ${this.id}
     */
    private void checkDeleteBy${foreignCName}(${this.type} ${this.id}) {
        <#list this.metaEntity.foreignFields as foreignField>
            <#if foreignField.entityId==foreignEntity.entityId>
        <#if !alreadyFind>int </#if>count = ${foreigncName}DAO.getCountBy${foreignField.jfieldName?capFirst}(${this.id});
        if(count>0){
            throw new ${this.projectNameUpper}Exception(ErrorCode.CASCADE_DELETE_ERROR);
        }
                <#assign alreadyFind=true>
            </#if>
        </#list>
    }

    </#list>
</#if>
<#if this.metaEntity.mtmUnHoldRefers??>
    <#list this.metaEntity.mtmUnHoldRefers as otherEntity>
        <#assign otherCName=otherEntity.className?capFirst>
        <#assign othercName=otherEntity.className?uncapFirst>
    /**
     * 校验是否存在【${otherEntity.title}】关联
     * @param ${this.id}
     */
    private void checkDeleteBy${otherCName}(${this.type} ${this.id}) {
        int count = ${othercName}DAO.getCountBy${this.classNameUpper}(${this.id});
        if(count>0){
            throw new ${this.projectNameUpper}Exception(ErrorCode.CASCADE_DELETE_ERROR);
        }
    }

    </#list>
</#if>
<#if this.metaEntity.mtmHoldRefers??>
    <#list this.metaEntity.mtmHoldRefers as otherEntity>
        <@call this.addImport("org.apache.commons.lang3.ArrayUtils")/>
        <#assign otherPk=otherEntity.pkField>
        <#assign otherCName=otherEntity.className?capFirst>
        <#assign othercName=otherEntity.className?uncapFirst>
        <#assign otherPkId=MetadataUtil.getPkAlias(othercName,false)>
    /**
     * 执行【${otherEntity.title}】添加
     * @param ${this.id}
     * @param ${otherPkId}
     * @return
     */
    private int doAdd${otherCName}(${this.type} ${this.id}, ${otherPk.jfieldType}... ${otherPkId}) {
        int count = 0;
        for (${otherPk.jfieldType} _id : ${otherPkId}) {
            if(${othercName}DAO.exist(_id)){
                count += ${this.className}DAO.add${otherCName}(${this.id},_id);
            }
        }
        return count;
    }

    /**
     * 添加【${otherEntity.title}】
     * @param ${this.id}
     * @param ${otherPkId}
     * @return
     */
    @Transactional
    public int add${otherCName}(${this.type} ${this.id}, ${otherPk.jfieldType}... ${otherPkId}) {
        ${this.classNameUpper}PO ${this.className} = this.get${this.classNameUpper}(${this.id}, true);
        if(ArrayUtils.isEmpty(${otherPkId})){
            throw new ${this.projectNameUpper}Exception(ErrorCode.PARAM_IS_NULL);
        }
        return doAdd${otherCName}(${this.id}, ${otherPkId});
    }

    /**
     * 移除【${otherEntity.title}】
     * @param ${this.id}
     * @param ${otherPkId}
     * @return
     */
    @Transactional
    public int remove${otherCName}(${this.type} ${this.id}, ${otherPk.jfieldType}... ${otherPkId}) {
        ${this.classNameUpper}PO ${this.className} = this.get${this.classNameUpper}(${this.id}, true);
        if(ArrayUtils.isEmpty(${otherPkId})){
            throw new ${this.projectNameUpper}Exception(ErrorCode.PARAM_IS_NULL);
        }
        return ${this.className}DAO.remove${otherCName}(${this.id}, ${otherPkId});
    }

    /**
     * 设置【${otherEntity.title}】
     * @param ${this.id}
     * @param ${otherPkId}
     * @return
     */
    @Transactional
    public int set${otherCName}(${this.type} ${this.id}, ${otherPk.jfieldType}[] ${otherPkId}) {
        ${this.classNameUpper}PO ${this.className} = this.get${this.classNameUpper}(${this.id}, true);
        ${this.className}DAO.removeAll${otherCName}(${this.id});
        if(ArrayUtils.isEmpty(${otherPkId})){
            return 0;
        }
        return doAdd${otherCName}(${this.id}, ${otherPkId});
    }

    </#list>
</#if>

}

</#assign>
<#--开始渲染代码-->
package ${this.packageName}.service;

<@call this.printImport()/>

${code}
