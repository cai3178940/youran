package com.youran.generate.constant;


import static com.youran.generate.constant.TemplateType.*;


/**
 * Title:freemarker模版文件枚举
 * Description:
 * Author: cbb
 * Create Time:2017/5/18 23:00
 */
public enum TemplateEnum {

    /**
     * application.yml配置文件
     */
    ApplicationYml(COMMON, "resources/application.yml.ftl"),
    /**
     * application-local.yml配置文件
     */
    ApplicationLocalYml(COMMON, "resources/application-local.yml.ftl"),
    /**
     * mybatis-config.xml配置文件
     */
    MybatisConfig(COMMON, "resources/mybatis-config.xml.ftl"),
    /**
     * spring boot自动配置文件
     */
    SpringFactories(COMMON,"resources/META-INF/spring.factories.ftl"),
    /**
     * mybatis的SQL脚本文件
     */
    MybatisMapper(ENTITY, "resources/{packageName}/mapper/{ClassName}Mapper.xml.ftl"),



    /**
     * 登录上下文接口
     */
    CommonLoginContext(COMMON,"sources/{commonPackage}/LoginContext.java.ftl"),
    /**
     * 通用自动配置
     */
    CommonAutoConfiguration(COMMON,"sources/{commonPackage}/config/CommonAutoConfiguration.java.ftl"),
    /**
     * 打印启动日志
     */
    StartLogCommandLineRunner(COMMON,"sources/{commonPackage}/config/StartLogCommandLineRunner.java.ftl"),
    /**
     * 布尔常量
     */
    BoolConst(COMMON,"sources/{commonPackage}/constant/BoolConst.java.ftl"),
    /**
     * 错误代码枚举
     */
    ErrorCode(COMMON,"sources/{commonPackage}/constant/ErrorCode.java.ftl"),
    /**
     * json常量
     */
    JsonFieldConst(COMMON,"sources/{commonPackage}/constant/JsonFieldConst.java.ftl"),
    /**
     * DAO超类
     */
    AbstractDAO(COMMON,"sources/{commonPackage}/dao/AbstractDAO.java.ftl"),
    /**
     * 启用乐观锁
     */
    EnableOptimisticLock(COMMON,"sources/{commonPackage}/optimistic/EnableOptimisticLock.java.ftl"),
    /**
     * 乐观锁异常
     */
    OptimisticException(COMMON,"sources/{commonPackage}/optimistic/OptimisticException.java.ftl"),
    /**
     * 乐观锁注解
     */
    OptimisticLock(COMMON,"sources/{commonPackage}/optimistic/OptimisticLock.java.ftl"),
    /**
     * 乐观锁AOP
     */
    OptimisticLockAspect(COMMON,"sources/{commonPackage}/optimistic/OptimisticLockAspect.java.ftl"),
    /**
     * 乐观锁配置
     */
    OptimisticLockConfiguration(COMMON,"sources/{commonPackage}/optimistic/OptimisticLockConfiguration.java.ftl"),
    /**
     * 数据传输对象超类
     */
    AbstractDTO(COMMON,"sources/{commonPackage}/pojo/dto/AbstractDTO.java.ftl"),
    /**
     * 分页查询dto
     */
    PageQueryDTO(COMMON,"sources/{commonPackage}/pojo/dto/PageQueryDTO.java.ftl"),
    /**
     * 字段示例
     */
    AbstractExample(COMMON,"sources/{commonPackage}/pojo/example/AbstractExample.java.ftl"),

    /**
     * 抽象PO
     */
    AbstractPO(COMMON,"sources/{commonPackage}/pojo/po/AbstractPO.java.ftl"),
    /**
     * 创建人接口
     */
    CreateBy(COMMON,"sources/{commonPackage}/pojo/po/CreateBy.java.ftl"),
    /**
     * 创建人&创建日期
     */
    CreateByDate(COMMON,"sources/{commonPackage}/pojo/po/CreateByDate.java.ftl"),
    /**
     * 创建日期接口
     */
    CreateDate(COMMON,"sources/{commonPackage}/pojo/po/CreateDate.java.ftl"),
    /**
     * 逻辑删除+创建人&创建日期+操作人&操作日期
     */
    CreateOperateDelete(COMMON,"sources/{commonPackage}/pojo/po/CreateOperateDelete.java.ftl"),
    /**
     * 逻辑删除+创建人&创建日期+操作人&操作日期+版本号
     */
    CreateOperateDeleteVersion(COMMON,"sources/{commonPackage}/pojo/po/CreateOperateDeleteVersion.java.ftl"),
    /**
     * 是否逻辑删除接口
     */
    DelSign(COMMON,"sources/{commonPackage}/pojo/po/DelSign.java.ftl"),
    /**
     * 操作人接口
     */
    OperateBy(COMMON,"sources/{commonPackage}/pojo/po/OperateBy.java.ftl"),
    /**
     * 操作人&操作日期
     */
    OperateByDate(COMMON,"sources/{commonPackage}/pojo/po/OperateByDate.java.ftl"),
    /**
     * 操作日期接口
     */
    OperateDate(COMMON,"sources/{commonPackage}/pojo/po/OperateDate.java.ftl"),
    /**
     * 是否乐观锁版本接口
     */
    Version(COMMON,"sources/{commonPackage}/pojo/po/Version.java.ftl"),

    /**
     * 抽象VO
     */
    AbstractVO(COMMON,"sources/{commonPackage}/pojo/vo/AbstractVO.java.ftl"),
    /**
     * 参数错误-错误字段信息
     */
    FieldErrorVO(COMMON,"sources/{commonPackage}/pojo/vo/FieldErrorVO.java.ftl"),
    /**
     * 分页结果对象
     */
    PageVO(COMMON,"sources/{commonPackage}/pojo/vo/PageVO.java.ftl"),
    /**
     * 通用响应对象
     */
    ReplyVO(COMMON,"sources/{commonPackage}/pojo/vo/ReplyVO.java.ftl"),
    /**
     * 各种形式的转换工具类
     */
    ConvertUtil(COMMON,"sources/{commonPackage}/util/ConvertUtil.java.ftl"),
    /**
     * 日期工具
     */
    DateUtil(COMMON,"sources/{commonPackage}/util/DateUtil.java.ftl"),
    /**
     * H2数据库工具类
     */
    H2Util(COMMON,"sources/{commonPackage}/util/H2Util.java.ftl"),
    /**
     * IP工具
     */
    IpUtil(COMMON,"sources/{commonPackage}/util/IpUtil.java.ftl"),
    /**
     * 封装json操作
     */
    JsonUtil(COMMON,"sources/{commonPackage}/util/JsonUtil.java.ftl"),
    /**
     * 我的字符串工具
     */
    MyStringUtil(COMMON,"sources/{commonPackage}/util/MyStringUtil.java.ftl"),
    /**
     * 空指针安全的类型转换工具
     */
    SafeUtil(COMMON,"sources/{commonPackage}/util/SafeUtil.java.ftl"),
    /**
     * 获取UUID
     */
    UUIDUtil(COMMON,"sources/{commonPackage}/util/UUIDUtil.java.ftl"),
    /**
     * 常量类中静态校验方法标记
     */
    Check(COMMON,"sources/{commonPackage}/validator/Check.java.ftl"),
    /**
     * 自定义校验注解
     */
    ConstAnno(COMMON,"sources/{commonPackage}/validator/Const.java.ftl"),




    /**
     * 自动配置类模版
     */
    App(COMMON, "sources/{packageName}/{ProjectName}App.java.ftl"),
    /**
     * 自动配置类模版
     */
    Configuration(COMMON, "sources/{packageName}/config/{ProjectName}Configuration.java.ftl"),
    /**
     * loginContext模版
     */
    LoginContext(COMMON, "sources/{packageName}/config/{ProjectName}LoginContext.java.ftl"),
    /**
     * 属性配置类模版
     */
    Properties(COMMON, "sources/{packageName}/config/{ProjectName}Properties.java.ftl"),
    /**
     * 系统常量模版
     */
    Const(COMMON, "sources/{packageName}/constant/{ProjectName}Const.java.ftl"),
    /**
     * 枚举类模版
     */
    EnumClass(CONST, "sources/{packageName}/constant/{EnumName}.java.ftl"),
    /**
     * dao模版
     */
    DAO(ENTITY, "sources/{packageName}/dao/{ClassName}DAO.java.ftl"),
    /**
     * 自定义异常模版
     */
    Exception(COMMON, "sources/{packageName}/exception/{ProjectName}Exception.java.ftl"),
    /**
     * 添加参数模版
     */
    AddDTO(ENTITY, "sources/{packageName}/pojo/dto/{ClassName}AddDTO.java.ftl"),
    /**
     * 查询参数模版
     */
    QueryDTO(ENTITY, "sources/{packageName}/pojo/dto/{ClassName}QueryDTO.java.ftl"),
    /**
     * 修改参数模版
     */
    UpdateDTO(ENTITY, "sources/{packageName}/pojo/dto/{ClassName}UpdateDTO.java.ftl"),
    /**
     * 示例说明模版
     */
    Example(ENTITY, "sources/{packageName}/pojo/example/{ClassName}Example.java.ftl"),
    /**
     * 属性映射工具模版
     */
    Mapper(ENTITY, "sources/{packageName}/pojo/mapper/{ClassName}Mapper.java.ftl"),
    /**
     * po模版
     */
    PO(ENTITY, "sources/{packageName}/pojo/po/{ClassName}PO.java.ftl"),
    /**
     * 列表展示对象模版
     */
    ListVO(ENTITY, "sources/{packageName}/pojo/vo/{ClassName}ListVO.java.ftl"),
    /**
     * 详情展示对象模版
     */
    ShowVO(ENTITY, "sources/{packageName}/pojo/vo/{ClassName}ShowVO.java.ftl"),
    /**
     * 增删改查service模版
     */
    Service(ENTITY, "sources/{packageName}/service/{ClassName}Service.java.ftl"),
    /**
     * 异常信息展示
     */
    ExceptionTranslator(COMMON, "sources/{packageName}/web/advice/ExceptionTranslator.java.ftl"),
    /**
     * swagger接口文档模版
     */
    API(ENTITY, "sources/{packageName}/web/api/{ClassName}API.java.ftl"),
    /**
     * controller模版
     */
    Controller(ENTITY, "sources/{packageName}/web/rest/{ClassName}Controller.java.ftl"),






    /**
     * 单元测试抽象类模版
     */
    AbstractTest(COMMON, "test/{packageName}/AbstractTest.java.ftl"),
    /**
     * H2数据库刷新模版
     */
    H2Flusher(COMMON, "test/{packageName}/H2Flusher.java.ftl"),
    /**
     * 合并测试类模版
     */
    Main(COMMON, "test/{packageName}/Main.java.ftl"),
    /**
     * 测试配置类模版
     */
    TestConfiguration(COMMON, "test/{packageName}/TestConfiguration.java.ftl"),
    /**
     * 测试帮助类模版
     */
    Helper(ENTITY, "test/{packageName}/help/{ClassName}Helper.java.ftl"),
    /**
     * web单元测试抽象类模版
     */
    AbstractWebTest(COMMON, "test/{packageName}/web/AbstractWebTest.java.ftl"),
    /**
     * 单元测试类模版
     */
    ControllerTest(ENTITY, "test/{packageName}/web/rest/{ClassName}ControllerTest.java.ftl"),



    /**
     * 测试spring配置文件模版
     */
    ApplicationTestYml(COMMON, "test_resources/application-local.yml.ftl"),
    /**
     * sql脚本模版
     */
    SQL(COMMON, "test_resources/DB/{projectName}.sql.ftl"),

    /**
     * pom模版
     */
    Pom(COMMON, "pom/pom.xml.ftl");

    /**
     * 模版类型
     */
    private final int type;
    /**
     * 模版文件
     */
    private final String template;

    TemplateEnum(int type, String template) {
        this.type = type;
        this.template = template;
    }

    public int getType() {
        return type;
    }

    public String getTemplate() {
        return template;
    }

}
