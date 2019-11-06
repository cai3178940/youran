<#include "/abstracted/common.ftl">
server:
<#if this.projectFeature.bootVersion==2>
    servlet:
        context-path: /
<#else>
    context-path: /
</#if>
    port: 8080
spring:
    application:
        name: ${this.originProjectName}
    profiles:
        active: local
    # 强制指定响应头content-type是utf-8编码
    http:
        encoding:
            force: true

swagger:
    base-package: ${this.packageName}


