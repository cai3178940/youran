# youran代码自动化平台

![version](https://img.shields.io/badge/youran-v3.6.0-orange)
![license](https://img.shields.io/github/license/cai3178940/youran)

## 介绍

这是一款包含系统设计和代码生成功能的自动化web平台，使用本平台可以极大提升程序员的开发效率

**包含如下特色功能：**
1. ★★★基于git增量生成代码
<a href="doc/IncrementalGeneration.md" target="_blank">点击查看原理</a>：
支持将代码直接生成到远程git仓库，同时支持在元数据变动以后再次生成（只生成差异部分）。
2. ★★★自定义模板：如果预置的代码模板不能满足您的需求，可基于您自己团队的技术框架开发属于您独有的代码模板。
3. ★★★自定义图表：在已有实体的基础上，可配置自定义的明细表和聚合表，可配置echarts图表，可将多个图表放在一个看板中展示。
4. ★反向工程：如果您的应用已经完成了表结构设计，请使用`反向工程`功能将建表语句反向导入项目中，自动生成实体和字段。
5. 多对多关系：支持在两个实体之间建立多对多关联关系，会在mysql中生成一张关联表，可以设置两个实体之间是否持有对方引用，持有引用的一方会自动生成维护关联关系的功能。
6. 外键关联：在添加字段的时候，可以设置该字段是否外键，外键字段需要关联某个实体的主键，从而可以实现`一对一`或`一对多`关联。
7. 级联扩展：外键字段或多对多实体上可以配置级联字段，级联字段可以作为被关联实体的查询条件，也能作为查询结果展示。
8. 实体ER图：可以在实体列表页选择多个存在关联关系的实体，点击`查看ER图`按钮，即可生成实体关系图。
9. 代码预览：不需要下载就可以在线浏览生成的代码，还能预览元数据变动后新生成的代码与之前的差异。
10. 可生成excel导入导出功能
（基于<a href="https://github.com/alibaba/easyexcel" target="_blank">easyexcel</a>），
可选择是否使用lombok插件。
11. 项目共享`可选功能`：您需要集成自己公司的统一登录，然后就能将项目共享给小组成员，一起维护，该功能默认关闭。

**【git增量生成功能】是本系统最核心的功能。**

打个比方：您已经在生成代码的基础上进行了大量业务逻辑的开发，这个时候您的需求突然有改动，
需要新增几个字段或加一张表，那么使用`git增量生成功能`可以减少大量手工重复劳动。
具体操作如下：

1. 在代码自动化平台上维护上新的元数据
2. 在项目列表页，点击按钮`提交Git`
3. 回到你的代码master分支，执行git操作：fetch
4. 将远程auto分支merge到当前分支

> 前提条件！！！您必须在手工修改代码之前，在项目配置中`启用Git仓库`。
> auto分支千万不能删除。也不能在auto分支上进行任何代码开发。


**该功能支持您在“修改元数据”和“生成代码”两个操作中反复执行，建议您在整个软件迭代周期中都使用本平台来执行增量生成。**

## 页面截图

**项目管理**
![项目管理](./doc/image/projects.png)

**增量预览**
![增量预览](./doc/image/increment-preview.png)

**字段管理**
![字段管理](./doc/image/fields.png)

**ER图**
![ER图](./doc/image/er-diagram.png)

**代码预览**
![代码预览](./doc/image/code-preview.png)

**模板列表**
![模板列表](./doc/image/templates.png)

**自定义图表**
![自定义图表](./doc/image/charts.png)


## 安装步骤

### 第一步、环境及配置

1. 自行安装依赖软件：mysql数据库、jdk8
2. 下载<a href="https://github.com/cai3178940/youran/releases" target="_blank">zip安装包</a>并解压
3. 在mysql中提前建好表，建表语句见安装包：/schema/init.sql
4. 修改配置文件中的数据库配置：/conf/application-local.yml


### 第二步、启动

启动脚本：/bin/startup.cmd

系统访问地址： 

- http://localhost:8088

首次访问系统时，需要导入系统预置代码模板：

- java后端模板： 
<a href="https://github.com/cai3178940/youran-template-01" target="_blank">源码地址</a>
<a href="https://github.com/cai3178940/youran-template-01/releases" target="_blank">zip压缩包</a>
- vue前端模板： 
<a href="https://github.com/cai3178940/youran-template-02" target="_blank">源码地址</a>
<a href="https://github.com/cai3178940/youran-template-02/releases" target="_blank">zip压缩包</a>

## 使用说明

一、最佳方案`推荐`（在本工程中进行模型设计，代码同步到git）

1. 在gitlab或github之类的代码平台上创建空的工程
2. 在本平台中创建项目并录入git地址
3. 创建实体【必须】
4. 创建实体字段【必须】
5. 创建其他元数据【可选】
6. 执行`提交Git`


二、传统方案`不推荐`（设计完表结构后执行反向工程）

1. 提前设计好表结构
2. 在本平台创建项目，使用您的建表语句执行`反向工程`
3. 调整字段的属性（因为反向工程导入的字段信息量太少，部分属性设置了默认值）
4. 生成代码

## 入门案例

这是一个简单的
<a href="https://github.com/cai3178940/youran/releases/tag/example" target="_blank">项目示例</a>，
您只需要下载项目的元数据文件，然后按照下面的流程，简单地点几下，即可预览生成的代码

1. 项目管理 -> 元数据导入
2. 项目管理 -> 操作 -> 编辑 -> 选择代码模板
3. 项目管理 -> 使用模板 -> 代码预览


## 术语解析

1. 项目：项目就是一个独立的应用系统，可以基于一个项目生成一整套JavaWeb系统。
2. 实体：一个实体对应数据库里一张业务表和一整套增删改查功能，同时实体之间也可以有‘一对多’，‘多对多’等关联关系。
3. 字段：这里的字段既是数据库业务表中的字段，也是java实体类中的字段，一个字段有非常多的属性可以配置。
4. 枚举：建议将不常变化的一类常量数据创建成枚举，会在java代码中生成对应的enum类。
5. 索引：就是mysql业务表中的索引。
6. 多对多关系：两个实体之间的关联关系，会生成一张中间表用来存储双方的id。


## 更新日志

[ChangeLog](/doc/ChangeLog.md)

## 生成效果展示

![image](./doc/image/display.png)

> <a href="https://github.com/cai3178940/youran" target="_blank">点击访问Github源码地址</a>
