<#include "/common.ftl">
spring:
    datasource:
        url: jdbc:mysql://localhost:3306/${this.originProjectName}?master=1&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false&zeroDateTimeBehavior=convertToNull&useSSL=false
        username: root
        password: root
swagger:
    enable: true
