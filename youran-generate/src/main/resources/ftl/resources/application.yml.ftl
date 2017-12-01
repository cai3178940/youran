<#include "/common.ftl">
server:
    context-path: /
    port: 8080
spring:
    application:
        name: ${originProjectName}
    profiles:
        active: local
    http:
        encoding:
            force: true

swagger:
    basePackage: ${packageName}

mybatis:
    config-location: classpath:mybatis-config.xml

