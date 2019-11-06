<#include "/abstracted/common.ftl">
spring:
    datasource:
        url: jdbc:h2:mem:testdb;MODE=MYSQL;DB_CLOSE_DELAY=-1
logging:
    level:
        root: info
        java:
            sql: debug
        ${this.packageName}: trace
