# youran代码自动化平台

> 通过维护元数据，一键生成java后端基础架构，同时还能生成增删改查代码和单元测试。
生成的项目是基于spring-boot+mybatis架构；其他附属技术栈包括：
- hibernate-validator
- swagger(API文档自动生成)
- mapstruct(属性映射)
- 基于H2内存数据库的单元测试

## 创建本地数据库
1. 先修改数据库配置：youran-generate\src\main\resources\application-local.yml
2. 建表语句参考：youran-generate\src\test\resources\DB\generate.sql

## 安装部署

``` bash
# 安装前端项目npm依赖
cd youran-generate-ui
npm install

# 开发模式启动前端环境
# npm run dev

# 将前端资源编译到后端项目中
# 编译目标路径：youran-generate/src/main/resources/ui
npm run build

# 后端代码编译打包
cd ../
mvn clean package

# 运行spring-boot项目
java -jar youran-generate/target/youran-generate-1.0-SNAPSHOT.war

```
### 项目管理页面
![image](https://github.com/cai3178940/youran/blob/master/image1.png)
### 实体管理页面
![image](https://github.com/cai3178940/youran/blob/master/image2.png)
### 字段管理页面
![image](https://github.com/cai3178940/youran/blob/master/image3.png)
### 字段添加页面
![image](https://github.com/cai3178940/youran/blob/master/image4.png)

