<#include "/common.ftl">
server:
    context-path: /
    port: 8080
spring:
    application:
        name: ${originProjectName}
    profiles:
        active: local
    # 强制指定响应头content-type是utf-8编码
    http:
        encoding:
            force: true

swagger:
    basePackage: ${packageName}


