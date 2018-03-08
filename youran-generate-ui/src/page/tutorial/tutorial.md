# 欢迎访问youran代码自动化平台

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

```
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
java -jar youran-generate/target/youran-generate.war

```

## 升级日志

#### 2018-03-07
- 移除fastjson依赖，统一换成jackson
- 修改下载代码压缩包文件名（带上项目名称）
