<#include "/common.ftl">
spring:
    datasource:
        url: jdbc:mysql://localhost:3306/${originProjectName}?master=1&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false&zeroDateTimeBehavior=convertToNull&useSSL=false
        username: root
        password: root
