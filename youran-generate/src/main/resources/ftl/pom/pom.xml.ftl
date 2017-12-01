<#include "/common.ftl">
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.8.RELEASE</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.myGroup</groupId>
    <artifactId>${originProjectName}</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <servlet.version>3.1.0</servlet.version>
        <fastjson.version>1.2.29</fastjson.version>
        <mybatis-spring-boot.version>1.3.1</mybatis-spring-boot.version>
        <springfox.version>2.7.0</springfox.version>
        <commons-lang3.version>3.5</commons-lang3.version>
        <commons-io.version>2.5</commons-io.version>
        <commons-collections.version>3.2.2</commons-collections.version>
        <org.mapstruct.version>1.1.0.Final</org.mapstruct.version>
    </properties>

    <dependencies>


        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>${r'$'}{fastjson.version}</version>
        </dependency>
        <dependency>
            <groupId>commons-io</groupId>
            <artifactId>commons-io</artifactId>
            <version>${r'$'}{commons-io.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>${r'$'}{commons-lang3.version}</version>
        </dependency>
        <dependency>
            <groupId>commons-collections</groupId>
            <artifactId>commons-collections</artifactId>
            <version>${r'$'}{commons-collections.version}</version>
        </dependency>
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-validator</artifactId>
        </dependency>
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
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>${r'$'}{mybatis-spring-boot.version}</version>
        </dependency>

        <!-- spring-boot 单元测试-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!-- 单元测试使用H2内存数据库 -->
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <version>1.4.193</version>
            <scope>test</scope>
        </dependency>
        <!-- 正式环境使用mysql数据库 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- mapstruct提供属性映射功能 -->
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
            <scope>provided</scope>
        </dependency>

        <!-- swagger依赖 -->
        <dependency>
            <groupId>com.didispace</groupId>
            <artifactId>spring-boot-starter-swagger</artifactId>
            <version>1.3.0.RELEASE</version>
        </dependency>

    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.5.1</version>
                <configuration>
                    <source>${r'$'}{java.version}</source>
                    <target>${r'$'}{java.version}</target>
                    <annotationProcessorPaths>
                        <!-- 提供编译期生成mapper实现类，maven命令行下有效（idea下无效） -->
                        <path>
                            <groupId>org.mapstruct</groupId>
                            <artifactId>mapstruct-processor</artifactId>
                            <version>${r'$'}{org.mapstruct.version}</version>
                        </path>
                    </annotationProcessorPaths>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
