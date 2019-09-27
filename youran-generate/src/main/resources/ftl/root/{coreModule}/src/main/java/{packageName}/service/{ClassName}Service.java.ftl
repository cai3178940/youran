<#include "/common.ftl">
<#include "/mtmCascadeExtsForList.ftl">
<#include "/mtmCascadeExtsForShow.ftl">
<#--定义主体代码-->
<#assign code>
<@call this.addImport("${this.commonPackage}.constant.ErrorCode")/>
<@call this.addImport("${this.commonPackage}.exception.BusinessException")/>
<@call this.addImport("${this.packageName}.pojo.dto.${this.classNameUpper}AddDTO")/>
<@call this.addImport("${this.packageName}.pojo.qo.${this.classNameUpper}QO")/>
<@call this.addImport("${this.packageName}.pojo.vo.${this.classNameUpper}ListVO")/>
<@call this.addImport("${this.packageName}.pojo.dto.${this.classNameUpper}UpdateDTO")/>
<@call this.addImport("${this.packageName}.pojo.mapper.${this.classNameUpper}Mapper")/>
<@call this.addImport("${this.packageName}.pojo.po.${this.classNameUpper}PO")/>
<@call this.addImport("${this.packageName}.pojo.vo.${this.classNameUpper}ShowVO")/>
<@call this.addImport("org.springframework.beans.factory.annotation.Autowired")/>
<@call this.addImport("org.springframework.stereotype.Service")/>
<@call this.addImport("org.springframework.transaction.annotation.Transactional")/>
<@call this.printClassCom("【${this.title}】删改查服务")/>
@Service
public class ${this.classNameUpper}Service {

<#-- 引入当前实体的DAO -->
<@call this.addAutowired("${this.packageName}.dao" "${this.classNameUpper}DAO")/>
<#-- 引入多对多关联实体的DAO（当前持有） -->
<#list this.holds! as otherEntity,mtm>
    <#assign otherCName=otherEntity.className?capFirst>
    <@call this.addAutowired("${this.packageName}.dao" "${otherCName}DAO")/>
</#list>
<#-- 引入多对多关联实体的DAO（非当前持有） -->
<#list this.unHolds! as otherEntity,mtm>
    <@call this.addAutowired("${this.packageName}.dao" "${otherEntity.className?capFirst}DAO")/>
</#list>
<#-- 引入外键对应的DAO （插入字段对应的外键）-->
<#list this.insertFields as id,field>
    <#if isTrue(field.foreignKey)>
        <@call this.addAutowired("${this.packageName}.dao" "${field.foreignEntity.className?capFirst}DAO")/>
    </#if>
</#list>
<#-- 引入外键对应的DAO （更新字段对应的外键）-->
<#list this.updateFields as id,field>
    <#if isTrue(field.foreignKey)>
        <@call this.addAutowired("${this.packageName}.dao" "${field.foreignEntity.className?capFirst}DAO")/>
    </#if>
</#list>
<#-- 被其他实体外键关联时，引入其他实体DAO -->
<#list this.foreignEntities! as foreignEntity>
    <@call this.addAutowired("${this.packageName}.dao" "${foreignEntity.className?capFirst}DAO")/>
</#list>
<#-- 当前实体的外键字段存在级联扩展时，引入对应实体的DAO -->
<#list this.fkFields as id,field>
    <#if field.cascadeListExts?? && field.cascadeListExts?size &gt; 0>
        <@call this.addAutowired("${this.packageName}.dao" "${field.foreignEntity.className?capFirst}DAO")/>
    </#if>
</#list>
<@call this.printAutowired()/>

<#if this.metaEntity.checkUniqueIndexes?? && this.metaEntity.checkUniqueIndexes?size &gt; 0>
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
            throw new BusinessException(ErrorCode.DUPLICATE_KEY);
        }
    </#list>
    }

</#if>
<#-- 抽象出公共方法【校验外键字段对应实体是否存在】 -->
<#macro checkForeignKeys fields>
    <#list fields as id,field>
        <#if isTrue(field.foreignKey)>
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
    @Transactional(rollbackFor = RuntimeException.class)
    public ${this.classNameUpper}PO save(${this.classNameUpper}AddDTO ${this.className}DTO) {
        ${this.classNameUpper}PO ${this.className} = ${this.classNameUpper}Mapper.INSTANCE.fromAddDTO(${this.className}DTO);
        <@checkForeignKeys this.insertFields/>
<#if this.metaEntity.checkUniqueIndexes?? && this.metaEntity.checkUniqueIndexes?size &gt; 0>
        // 唯一性校验
        this.checkUnique(${this.className},false);
</#if>
        ${this.className}DAO.save(${this.className});
<#list this.holds! as otherEntity,mtm>
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
        return ${this.className};
    }

    /**
     * 修改【${this.title}】
     * @param ${this.className}UpdateDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
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
        // 唯一性校验
        this.checkUnique(${this.className},true);
</#if>
        ${this.className}DAO.update(${this.className});
<#list this.holds! as otherEntity,mtm>
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
        return ${this.className};
    }
<#if isTrue(this.pageSign)>
    <@call this.addImport("${this.commonPackage}.pojo.vo.PageVO")/>
    /**
     * 查询分页列表
     * @param ${this.className}QO
     * @return
     */
    public PageVO<${this.classNameUpper}ListVO> list(${this.classNameUpper}QO ${this.className}QO) {
        PageVO<${this.classNameUpper}ListVO> page = ${this.className}DAO.findByPage(${this.className}QO);
        <#if mtmCascadeEntitiesForList?hasContent>
        for(${this.classNameUpper}ListVO listVO : page.getList()){
            <#list mtmCascadeEntitiesForList as otherEntity>
                <#assign otherCName=otherEntity.className?capFirst>
                <#assign othercName=otherEntity.className?uncapFirst>
            listVO.set${otherCName}List(${othercName}DAO.findVOBy${this.classNameUpper}(listVO.get${this.idUpper}()));
            </#list>
        }
        </#if>
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
    <#if mtmCascadeEntitiesForList?hasContent>
        for(${this.classNameUpper}ListVO listVO : list){
        <#list mtmCascadeEntitiesForList as otherEntity>
            <#assign otherCName=otherEntity.className?capFirst>
            <#assign othercName=otherEntity.className?uncapFirst>
            listVO.set${otherCName}List(${othercName}DAO.findVOFor${this.classNameUpper}List(listVO.get${this.idUpper}()));
        </#list>
        }
    </#if>
        return list;
    }
</#if>

<#if this.holds!?hasContent>
    /**
     * 根据主键获取【${this.title}】
     * 不获取多对多级联对象
     * @param ${this.id} 主键
     * @param force 是否强制获取
     * @return
     */
    public ${this.classNameUpper}PO get${this.classNameUpper}(${this.type} ${this.id}, boolean force){
    <#assign withFalseCode="">
    <#list this.holds! as otherEntity,mtm>
        <#assign withFalseCode=withFalseCode+"false, ">
    </#list>
        return this.get${this.classNameUpper}(${this.id}, ${withFalseCode}force);
    }

</#if>
    /**
     * 根据主键获取【${this.title}】
     * @param ${this.id} 主键
<#assign withHoldParam="">
<#list this.holds! as otherEntity,mtm>
    <#assign otherCName=otherEntity.className?capFirst>
    <#assign withParamName="with"+otherCName>
    <#assign withHoldParam=withHoldParam+"boolean with"+otherCName+", ">
     * @param ${withParamName} 是否级联获取【${otherEntity.title}】
</#list>
     * @param force 是否强制获取
     * @return
     */
    public ${this.classNameUpper}PO get${this.classNameUpper}(${this.type} ${this.id}, ${withHoldParam}boolean force){
        ${this.classNameUpper}PO ${this.className} = ${this.className}DAO.findById(${this.id});
        if (force && ${this.className} == null) {
            throw new BusinessException(ErrorCode.RECORD_NOT_FIND);
        }
<#list this.holds! as otherEntity,mtm>
    <#assign otherCName=otherEntity.className?capFirst>
    <#assign othercName=otherEntity.className?uncapFirst>
        if (with${otherCName}){
            ${this.className}.set${otherCName}POList(${othercName}DAO.findBy${this.classNameUpper}(${this.id}));
        }
</#list>
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
<#list this.fkFields as id,field>
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
<#list mtmCascadeEntitiesForShow as otherEntity>
    <#assign otherCName=otherEntity.className?capFirst>
    <#assign othercName=otherEntity.className?uncapFirst>
        // 设置【${otherEntity.title}】列表
        showVO.set${otherCName}List(${othercName}DAO.findVOFor${this.classNameUpper}Show(${this.id}));
</#list>
        return showVO;
    }

    /**
     * 删除【${this.title}】
     * @param ${this.id}s
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public int delete(${this.type}... ${this.id}s) {
        int count = 0;
        for (${this.type} ${this.id} : ${this.id}s) {
<#list this.foreignEntities! as foreignEntity>
    <#assign foreignCName=foreignEntity.className?capFirst>
            this.checkDeleteBy${foreignCName}(${this.id});
</#list>
<#list this.unHolds! as otherEntity,mtm>
    <#assign otherCName=otherEntity.className?capFirst>
            // 校验是否存在【${otherEntity.title}】关联
            this.checkDeleteBy${otherCName}(${this.id});
</#list>
            count += ${this.className}DAO.delete(${this.id});
        }
        return count;
    }

<#list this.foreignEntities! as foreignEntity>
    <#assign foreignCName=foreignEntity.className?capFirst>
    <#assign foreigncName=foreignEntity.className?uncapFirst>
    <#assign alreadyFind=false>
    /**
     * 校验是否存在【${foreignEntity.title}】关联
     * @param ${this.id}
     */
    private void checkDeleteBy${foreignCName}(${this.type} ${this.id}) {
    <#list this.foreignFields as foreignField>
        <#if foreignField.entityId==foreignEntity.entityId>
        <#if !alreadyFind>int </#if>count = ${foreigncName}DAO.getCountBy${foreignField.jfieldName?capFirst}(${this.id});
        if(count>0){
            throw new BusinessException(ErrorCode.CASCADE_DELETE_ERROR);
        }
            <#assign alreadyFind=true>
        </#if>
    </#list>
    }

</#list>
<#list this.unHolds! as otherEntity,mtm>
    <#assign otherCName=otherEntity.className?capFirst>
    <#assign othercName=otherEntity.className?uncapFirst>
    /**
     * 校验是否存在【${otherEntity.title}】关联
     * @param ${this.id}
     */
    private void checkDeleteBy${otherCName}(${this.type} ${this.id}) {
        int count = ${othercName}DAO.getCountBy${this.classNameUpper}(${this.id});
        if(count>0){
            throw new BusinessException(ErrorCode.CASCADE_DELETE_ERROR);
        }
    }

</#list>
<#list this.holds! as otherEntity,mtm>
    <@call this.addImport("org.apache.commons.lang3.ArrayUtils")/>
    <#assign otherPk=otherEntity.pkField>
    <#assign otherCName=otherEntity.className?capFirst>
    <#assign othercName=otherEntity.className?uncapFirst>
    <#assign otherFkId=mtm.getFkAlias(otherEntity.entityId,false)>
    /**
     * 执行【${otherEntity.title}】添加
     * @param ${this.id}
     * @param ${otherFkId}
     * @return
     */
    private int doAdd${otherCName}(${this.type} ${this.id}, ${otherPk.jfieldType}... ${otherFkId}) {
        int count = 0;
        for (${otherPk.jfieldType} _id : ${otherFkId}) {
            if(${othercName}DAO.exist(_id)){
                count += ${this.className}DAO.add${otherCName}(${this.id}, _id);
            }
        }
        return count;
    }

    /**
     * 添加【${otherEntity.title}】
     * @param ${this.id}
     * @param ${otherFkId}
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public int add${otherCName}(${this.type} ${this.id}, ${otherPk.jfieldType}... ${otherFkId}) {
        ${this.classNameUpper}PO ${this.className} = this.get${this.classNameUpper}(${this.id}, true);
        if(ArrayUtils.isEmpty(${otherFkId})){
            throw new BusinessException(ErrorCode.PARAM_IS_NULL);
        }
        return doAdd${otherCName}(${this.id}, ${otherFkId});
    }

    /**
     * 移除【${otherEntity.title}】
     * @param ${this.id}
     * @param ${otherFkId}
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public int remove${otherCName}(${this.type} ${this.id}, ${otherPk.jfieldType}... ${otherFkId}) {
        ${this.classNameUpper}PO ${this.className} = this.get${this.classNameUpper}(${this.id}, true);
        if(ArrayUtils.isEmpty(${otherFkId})){
            throw new BusinessException(ErrorCode.PARAM_IS_NULL);
        }
        return ${this.className}DAO.remove${otherCName}(${this.id}, ${otherFkId});
    }

    /**
     * 设置【${otherEntity.title}】
     * @param ${this.id}
     * @param ${otherFkId}
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public int set${otherCName}(${this.type} ${this.id}, ${otherPk.jfieldType}[] ${otherFkId}) {
        ${this.classNameUpper}PO ${this.className} = this.get${this.classNameUpper}(${this.id}, true);
        ${this.className}DAO.removeAll${otherCName}(${this.id});
        if(ArrayUtils.isEmpty(${otherFkId})){
            return 0;
        }
        return doAdd${otherCName}(${this.id}, ${otherFkId});
    }

</#list>

}

</#assign>
<#--开始渲染代码-->
package ${this.packageName}.service;

<@call this.printImport()/>

${code}
