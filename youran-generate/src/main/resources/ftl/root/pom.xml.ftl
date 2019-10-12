<#include "/common.ftl">
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version><#if this.projectFeature.bootVersion==2>2.1.9.RELEASE<#else>1.5.22.RELEASE</#if></version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <groupId>${this.groupId}</groupId>
    <artifactId>${this.originProjectName}</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>pom</packaging>

    <modules>
        <module>${this.originProjectName}-common</module>
        <module>${this.originProjectName}-core</module>
        <module>${this.originProjectName}-web</module>
    </modules>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <mybatis-spring-boot.version><#if this.projectFeature.bootVersion==2>2.1.0<#else>1.3.4</#if></mybatis-spring-boot.version>
        <springfox.version><#if this.projectFeature.bootVersion==2>2.9.2<#else>2.7.0</#if></springfox.version>
        <commons-lang3.version>3.8.1</commons-lang3.version>
        <commons-io.version>2.6</commons-io.version>
        <commons-collections.version>3.2.2</commons-collections.version>
        <org.mapstruct.version>1.3.0.Final</org.mapstruct.version>
        <jsoup.version>1.11.2</jsoup.version>
        <h2.version>1.4.193</h2.version>
        <mysql2h2.version>0.2.1</mysql2h2.version>
        <spring-boot-swagger.version><#if this.projectFeature.bootVersion==2>1.9.0.RELEASE<#else>1.6.0.RELEASE</#if></spring-boot-swagger.version>
    </properties>

    <dependencyManagement>
        <dependencies>

            <dependency>
                <groupId>${r'$'}{project.groupId}</groupId>
                <artifactId>${this.originProjectName}-common</artifactId>
                <version>${r'$'}{project.version}</version>
            </dependency>
            <dependency>
                <groupId>${r'$'}{project.groupId}</groupId>
                <artifactId>${this.originProjectName}-core</artifactId>
                <version>${r'$'}{project.version}</version>
            </dependency>
            <dependency>
                <groupId>${r'$'}{project.groupId}</groupId>
                <artifactId>${this.originProjectName}-web</artifactId>
                <version>${r'$'}{project.version}</version>
            </dependency>
            <!-- apache常用工具包 -->
            <dependency>
                <groupId>org.apache.commons</groupId>
                <artifactId>commons-lang3</artifactId>
                <version>${r'$'}{commons-lang3.version}</version>
            </dependency>
            <!-- apache io流工具包 -->
            <dependency>
                <groupId>commons-io</groupId>
                <artifactId>commons-io</artifactId>
                <version>${r'$'}{commons-io.version}</version>
            </dependency>
            <!-- apache集合工具包 -->
            <dependency>
                <groupId>commons-collections</groupId>
                <artifactId>commons-collections</artifactId>
                <version>${r'$'}{commons-collections.version}</version>
            </dependency>
            <!-- spring集成swagger第三方包 http://springfox.github.io/springfox/ -->
            <dependency>
                <groupId>io.springfox</groupId>
                <artifactId>springfox-swagger2</artifactId>
                <version>${r'$'}{springfox.version}</version>
                <exclusions>
                    <exclusion>
                        <groupId>org.mapstruct</groupId>
                        <artifactId>mapstruct</artifactId>
                    </exclusion>
                </exclusions>
            </dependency>
            <dependency>
                <groupId>io.springfox</groupId>
                <artifactId>springfox-bean-validators</artifactId>
                <version>${r'$'}{springfox.version}</version>
            </dependency>
            <!-- 集成mybatis http://www.mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/index.html -->
            <dependency>
                <groupId>org.mybatis.spring.boot</groupId>
                <artifactId>mybatis-spring-boot-starter</artifactId>
                <version>${r'$'}{mybatis-spring-boot.version}</version>
            </dependency>
            <!-- 单元测试使用H2内存数据库 -->
            <dependency>
                <groupId>com.h2database</groupId>
                <artifactId>h2</artifactId>
                <version>${r'$'}{h2.version}</version>
            </dependency>
            <!-- 单元测试使用mysql脚本转H2 -->
            <dependency>
                <groupId>com.parmet</groupId>
                <artifactId>mysql2h2-parser</artifactId>
                <version>${r'$'}{mysql2h2.version}</version>
            </dependency>
            <!-- mapstruct提供属性映射功能 http://mapstruct.org/ -->
            <dependency>
                <groupId>org.mapstruct</groupId>
                <artifactId>mapstruct-jdk8</artifactId>
                <version>${r'$'}{org.mapstruct.version}</version>
            </dependency>
            <!-- idea下使用mapstruct必须添加本依赖，用于编译期生成mapper实现类 -->
            <dependency>
                <groupId>org.mapstruct</groupId>
                <artifactId>mapstruct-processor</artifactId>
                <version>${r'$'}{org.mapstruct.version}</version>
            </dependency>
            <!-- swagger依赖 @ https://github.com/SpringForAll/spring-boot-starter-swagger -->
            <dependency>
                <groupId>com.spring4all</groupId>
                <artifactId>swagger-spring-boot-starter</artifactId>
                <version>${r'$'}{spring-boot-swagger.version}</version>
            </dependency>
            <!-- jsoup HTML parser library（用于过滤XSS） https://jsoup.org/ -->
            <dependency>
                <groupId>org.jsoup</groupId>
                <artifactId>jsoup</artifactId>
                <version>${r'$'}{jsoup.version}</version>
            </dependency>
        </dependencies>
    </dependencyManagement>

</project>
