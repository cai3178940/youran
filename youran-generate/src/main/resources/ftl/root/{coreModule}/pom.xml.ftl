<#include "/common.ftl">
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>${this.originProjectName}</artifactId>
        <groupId>${this.groupId}</groupId>
        <version>1.0.0-SNAPSHOT</version>
        <relativePath>../pom.xml</relativePath>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>${this.originProjectName}-core</artifactId>
    <packaging>jar</packaging>


    <dependencies>

        <dependency>
            <groupId>${r'$'}{project.groupId}</groupId>
            <artifactId>${this.originProjectName}-common</artifactId>
        </dependency>


        <!-- mapstruct提供属性映射功能 -->
        <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct-jdk8</artifactId>
        </dependency>

        <!-- idea下使用mapstruct必须添加本依赖，用于编译期生成mapper实现类 -->
        <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct-processor</artifactId>
            <scope>provided</scope>
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
