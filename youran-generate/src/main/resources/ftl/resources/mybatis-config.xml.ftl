<#include "/common.ftl">
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <typeAliases>
        <package name="${packageName}.pojo"/>
    </typeAliases>
    <mappers>
    <#list metaEntities as metaEntity>
        <mapper resource="${packageName?replace('.','/')}/mapper/${metaEntity.className?cap_first}Mapper.xml"/>
    </#list>
    </mappers>
</configuration>
