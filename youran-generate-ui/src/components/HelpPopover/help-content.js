export default {

  project: {
    groupId: `
**groupId**：maven项目的groupId，主要影响pom文件
> 注意：请遵循开发规范，尽量只输入英文小写字母 “a-z”及分隔符 “.”
    `,
    projectName: `
**项目名称**：整个项目的唯一标识
> 注意：只允许输入英文小写字母 “a-z”或短横杠 “-”
    `,
    packageName: `
**包名**：生成的核心代码所属包路径
> 注意：请遵循开发规范，尽量只输入英文小写字母 “a-z”及路径分隔符 “.”
    `,
    author: `
**作者**：生成的代码中会注明作者，此外并无其他用途
    `,
    remote: `
**启用Git仓库**：启用以后，可以将代码同步到git仓库，可以实现代码增量生成
    `,
    remoteUrl: `
**Git仓库地址**：远程Git仓库地址，可以是github、码云、gitlab私有仓库等
> 注意：请使用新创建的空仓库，不要有任何内容。
    `,
    username: `
**Git用户名/oauth2**：请填写一个有代码提交权限的用户名，如果不想提供用户名密码，而是选择access_token则填入oauth2
    `,
    password: `
**Git密码/token**：密码/token不会回显，如果不想修改请置空

**access_token在以下三个站点的获取方式：**

- **github**：Settings-->Developer settings-->
<a href="https://github.com/settings/tokens" target="_blank">Personal access tokens</a>
- **gitlab**：Settings-->User Settings-->Access Tokens
- **码云**：设置-->安全设置-->
<a href="https://gitee.com/profile/personal_access_tokens" target="_blank">私人令牌</a>
    `,
    feature: {
      bootVersion: `
**spring-boot版本**：生成的基本框架所依赖的spring-boot版本
      `,
      other: `
**启用Lombok**：Lombok是一个Java库，可以通过简单的注解的形式来帮助我们简化和消除一些必须有但显得很臃肿的Java代码，
比如常见的Getter&Setter、toString()、构造函数等等。
<a href="https://projectlombok.org" target="_blank">点击访问Lombok官网</a>
      `
    }
  },
  entity: {

    title: `
**实体名**：本实体的中文名称，会出现在多处代码及表结构的注释中
> 注意：请尽量使用言简意赅的名称
    `,
    classAndTableName: `
**类名**：生成的java bean名称，比如输入类名User,会生成的bean有:UserPO、UserAddDTO、UserDAO等
> 注意：第一个字母请大写

**表名**：生成的业务表名称
> 注意：请按mysql表名规范来命名，尽量使用下划线来分隔单词
    `,
    pageSign: `
**分页**：生成的查询服务返回结果是否需要分页展示
    `,
    desc: `
**描述**：会出现在建表语句的备注中
    `,
    feature: `
**rest服务**： 根据勾选的内容生成对应的rest服务以及相关单元测试

- 列表： 查询当前业务对象列表
- 详情： 查看当前业务对象详细信息
- 修改： 修改当前业务对象（支持\`修改\`则也必须支持\`详情\`）
- 添加： 创建当前业务对象
- 单个删除： 删除单个当前业务对象
- 批量删除： 批量删除当前业务对象

    `
  },
  field: {
    feature: `
**性质**： 不同性质的字段拥有不同的功能特性，主要包含如下几种

- 普通字段：没有特殊功能的常规字段
- 主键：标识当前字段是否业务表中的主键字段
- 外键：当前字段是否跟其他实体的主键字段关联，可以用来实现“一对一”、“一对多”关联，外键支持\`级联扩展\`功能
- 逻辑删除：如果当前实体存在逻辑删除字段，那么调用删除方法的时候，只会将该字段改为1（默认值是0），而不是物理删除。
- 创建时间：创建实体的时候，会自动对本字段设值为当前时间
- 创建人员：记录下谁创建了本实体
- 更新时间：修改实体的时候，会自动对本字段设值为当前时间
- 更新人员：记录下是谁最后一次修改了本实体
- 乐观锁版本号：防止并发更新记录时出现脏数据，使用版本号字段进行控制

    `,
    jfieldName: `
**java字段名**：Java类中的字段名称，请遵循Java字段命名规范，尽量使用驼峰格式

**mysql字段名**：业务表中的字段名称，请遵循mysql字段命名规范，尽量使用下划线来分隔单词
    `,
    fieldName: `
**mysql字段名**：业务表中的字段名称
> 注意：请遵循mysql字段命名规范，尽量使用下划线来分隔单词
    `,
    fieldDesc: `
**字段标题**：本字段的中文标题，简短可识别即可
> 该属性不会对生成的代码产生影响
    `,
    fieldType: `
**java字段类型**：实体类中的java字段类型

**mysql字段类型**：数据库中的字段类型

**字段长度**：字段长度限制。

1. 首先会影响表结构里面的字段长度
2. 新增和修改服务也会对DTO中的参数进行长度校验

**字段精度**：只会影响表结构中的字段精度
    `,
    autoIncrement: `
**主键策略**：“自增”是最常用的主键策略，若不选“自增”，请在代码中手动给主键赋值，比如随机UUID。
    `,
    notNull: `
**不能为空**：是否非空字段
    `,
    foreignKey: `
**是否外键**：本字段是否跟其他实体的主键字段关联，可以用来实现“一对一”、“一对多”关联，外键支持\`级联扩展\`

**注意：这里的外键只是个逻辑上的概念，并不会在表里生成物理外键**

**使用外键后的效果1**：新增或修改实体时会校验关联实体是否存在
\`\`\`
@Transactional(rollbackFor = RuntimeException.class)
public DeveloperPO save(DeveloperAddDTO developerDTO) {
    DeveloperPO developer = DeveloperMapper.INSTANCE.fromAddDTO(developerDTO);
    if(developer.getUserId() != null){
        Assert.isTrue(userDAO.exist(developer.getUserId()),"用户id有误");
    }
    developerDAO.save(developer);
    return developer;
}

\`\`\`


**使用外键后的效果2**：删除实体时会校验当前实体是否有被其他实体的外键引用
\`\`\`
@Transactional(rollbackFor = RuntimeException.class)
public int delete(Long... userIds) {
    int count = 0;
    for (Long userId : userIds) {
        this.checkDeleteByDeveloper(userId);
        count += userDAO.delete(userId);
    }
    return count;
}
\`\`\`

    `,
    fieldExample: `
**字段示例**：有两个作用
1. 生成的swagger文档中，出入参示例来源于此
2. 生成的单元测试中，会用字段示例作为测试参数
> 所以请认真写一下示例！
    `,
    fieldComment: `
**字段注释**：对该字段的详细说明，会在以下位置使用
1. 生成到实体类字段注释中
2. 生成到swagger文档中，出入参结构中会用到字段备注
3. 生成到建表语句中，每个字段都会有注释
> 请认真填写注释，详细的注释有助于他人理解字段含义。
    `,
    dicType: `
**枚举字典**：当前字段的值是否来源于枚举
1、调用save/update服务时枚举字段会自动校验
2、字段的枚举值也会在swagger文档中显示

    `,
    query: `
**可搜索**：如果字段可搜索，则当前字段会出现在查询参数中，当然也会出现在查询sql的where语句中。

**搜索方式**：决定sql的查询运算符，如果是between类型，则会生成前后两个查询字段。
\`\`\`
//注册开始时间
@ApiModelProperty(notes = N_REGTIME,example = E_REGTIME)
@JsonFormat(pattern=JsonFieldConst.DEFAULT_DATETIME_FORMAT,timezone="GMT+8")
private Date regTimeStart;

//注册结束时间
@ApiModelProperty(notes = N_REGTIME,example = E_REGTIME)
@JsonFormat(pattern=JsonFieldConst.DEFAULT_DATETIME_FORMAT,timezone="GMT+8")
private Date regTimeEnd;

\`\`\`
    `,
    attributes: `
**列表展示**：如果字段支持列表展示，则会出现在列表查询服务的返回结果中。

**详情展示**：如果字段支持详情展示，则会出现查看详情服务的返回结果中

**可修改**：如果字段可修改，则会出现在修改服务的外部入参中，
否则代表该字段不允许用户修改。比如“用户名”字段，一旦创建就不允许修改。
可修改的字段也必须支持详情展示。

**可插入**：如果字段可插入，则会出现在新增服务的外部入参中，
否则就是需要在save方法中设置默认值。

**可排序**：如果字段可排序，则会在列表查询入参中加入排序参数，前端就可以自由指定如何排序。
\`\`\`
@ApiModelProperty(notes = "发布标识排序标识【1升序,-1降序,0不排序】",example = "1")
private Integer publishSignSortSign;
\`\`\`
    `,
    orderNo: `
**序号**：只影响当前字段在实体中的先后顺序，将功能接近的字段放在一起，会比较美观。

**特殊字段类型**：有特殊意义的字段类型
- 逻辑删除：如果当前实体存在逻辑删除字段，那么调用删除方法的时候，只会将该字段改为1（默认值是0），而不是物理删除。
- 创建时间：创建实体的时候，会自动对本字段设值为当前时间
- 创建人员：记录下谁创建了本实体
- 更新时间：修改实体的时候，会自动对本字段设值为当前时间
- 更新人员：记录下是谁最后一次修改了本实体
- 乐观锁版本号：防止并发更新记录时出现脏数据，使用版本号字段进行控制

    `,
    columnWidth: `
**列宽**：生成前端列表时，用来指定当前列的宽度(像素值)，0值代表宽度自适应
    `
  },
  'const': {
    constRemark: `
**枚举名称**：枚举的中文名称
    `,
    constName: `
**枚举类名**：Java枚举类的类名，请一定要符合类名规范。
    `,
    constType: `
**类型**：指定下属枚举值的字段类型
    `
  },
  constDetail: {
    detailName: `
**枚举字段名**：Java枚举类中的字段名称，必须是大写，单词间用下划线分隔
    `,
    detailValue: `
**枚举值**：枚举字段的值，如果枚举类型是整数，则必须是数字
    `,
    detailRemark: `
**值描述**：枚举值的中文描述，对应枚举类中desc属性的值
#### 生成的枚举类会包含两个属性，请看下面示例：
\`\`\`
/**
 * 枚举【开发者类型】
 *
 * @author cbb
 * @date 2018/03/09
 */
public enum DevType {

    PERSONAL(1,"个人"),
    COMPANY(2,"公司");

    private final Integer value;
    private final String desc;

    private static final Map<Integer, DevType> lookup = new HashMap<>();

    static {
        for (DevType e : DevType.values()) {
            lookup.put(e.value, e);
        }
    }

    ......
}
\`\`\`

    `
  },
  mtm: {
    projectId: `
**项目**：当前多对多所属项目
    `,
    tableName: `
**关联表名**：由于一个多对多关系会生成一张关联表，此处填写该关联表表名。
    `,
    desc: `
**描述**：用于生成关联表的备注信息
    `,
    entityId1: `
**实体1**：多对多的其中一方

**持有引用**：持有引用的一方可以主动维护多对多关联关系，包括添加、删除关联等操作
    `,
    entityId2: `
**实体2**：多对多的另一方

**持有引用**：持有引用的一方可以主动维护多对多关联关系，包括添加、删除关联等操作
    `,
    entityIdField1: `
**实体1外键字段**：多对多关联表中的外键字段1，对应实体1主键
    `,
    entityIdField2: `
**实体2外键字段**：多对多关联表中的外键字段2，对应实体2主键
    `,
    needId: `
**自增id**：关联表是否需要生成一个自增主键id

**id类型**：可选择长整型bigint和整型int两种，一般情况使用整型int即可
    `,
    feature: `
**级联模式**： 当前实体如何维护与另一个实体的关联关系

- 随实体一起维护\`【推荐】\`： 在创建或修改当前实体的同时，可指定关联关系，
会在同一个事务中维护多对多关联关系，适用于关联实体总数量不大的业务场景
- 单独设置： 将维护关联关系的操作单独提取出新的服务接口，适用于关联实体总数量不大的业务场景
- 添加+移除： 单独生成两个服务（添加关联、移除关联），适用于关联实体总数量比较庞大的场景

    `
  },
  index: {
    indexName: `
**索引名**：生成数据库索引的名称
    `,
    fieldIds: `
**字段**：生成索引的表字段
    `,
    unique: `
**是否唯一**：唯一索引可以从数据库层面限制字段唯一性
> 注意：如果实体中存在逻辑删除字段，则尽量不要使用唯一索引，而是用“普通索引”+“唯一性校验”来替代
    `,
    uniqueCheck: `
**唯一性校验**：插入或修改记录时，是否需要提前进行索引唯一性校验
    `
  },
  template: {
    templateVersion: `
**版本号**：当前模板的版本号
    `,
    sysLowVersion: `
**最低系统兼容**：当前模板能兼容的最低系统版本，如果该值高于当前系统版本，则会在生成代码时报错
    `,
    remark: `
**备注**：请详细描述模板所用的技术框架
    `

  }

}
