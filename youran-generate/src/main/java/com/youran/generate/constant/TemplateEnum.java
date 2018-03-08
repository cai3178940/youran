package com.youran.generate.constant;


import static com.youran.generate.constant.TemplateType.*;


/**
 * Title:模板枚举
 * Description: 模板必须再次注册才能生效
 * Author: cbb
 * Create Time:2017/9/20 15:19
 */
public enum TemplateEnum {


    /******** 根Pom *********/
    RootPom(COMMON, "pom.xml.ftl"),
    /******** Common模块 Pom *********/
    CommonPom(COMMON, "{commonModule}/pom.xml.ftl"),
    /******** Common模块 源码 *********/
    BoolConst(COMMON,"{commonModule}/src/main/java/{commonPackage}/constant/BoolConst.java.ftl"),
    ErrorCode(COMMON,"{commonModule}/src/main/java/{commonPackage}/constant/ErrorCode.java.ftl"),
    JsonFieldConst(COMMON,"{commonModule}/src/main/java/{commonPackage}/constant/JsonFieldConst.java.ftl"),
    CommonLoginContext(COMMON,"{commonModule}/src/main/java/{commonPackage}/context/LoginContext.java.ftl"),
    MyCustomDateEditor(COMMON,"{commonModule}/src/main/java/{commonPackage}/convert/MyCustomDateEditor.java.ftl"),
    AbstractDAO(COMMON,"{commonModule}/src/main/java/{commonPackage}/dao/AbstractDAO.java.ftl"),
    EnableOptimisticLock(COMMON,"{commonModule}/src/main/java/{commonPackage}/optimistic/EnableOptimisticLock.java.ftl"),
    OptimisticException(COMMON,"{commonModule}/src/main/java/{commonPackage}/optimistic/OptimisticException.java.ftl"),
    OptimisticLock(COMMON,"{commonModule}/src/main/java/{commonPackage}/optimistic/OptimisticLock.java.ftl"),
    OptimisticLockAspect(COMMON,"{commonModule}/src/main/java/{commonPackage}/optimistic/OptimisticLockAspect.java.ftl"),
    OptimisticLockConfiguration(COMMON,"{commonModule}/src/main/java/{commonPackage}/optimistic/OptimisticLockConfiguration.java.ftl"),
    AbstractDTO(COMMON,"{commonModule}/src/main/java/{commonPackage}/pojo/dto/AbstractDTO.java.ftl"),
    AbstractQO(COMMON,"{commonModule}/src/main/java/{commonPackage}/pojo/qo/AbstractQO.java.ftl"),
    PageQO(COMMON,"{commonModule}/src/main/java/{commonPackage}/pojo/qo/PageQO.java.ftl"),
    AbstractExample(COMMON,"{commonModule}/src/main/java/{commonPackage}/pojo/example/AbstractExample.java.ftl"),
    AbstractPO(COMMON,"{commonModule}/src/main/java/{commonPackage}/pojo/po/AbstractPO.java.ftl"),
    CreateBy(COMMON,"{commonModule}/src/main/java/{commonPackage}/pojo/po/CreateBy.java.ftl"),
    CreateByDate(COMMON,"{commonModule}/src/main/java/{commonPackage}/pojo/po/CreateByDate.java.ftl"),
    CreateDate(COMMON,"{commonModule}/src/main/java/{commonPackage}/pojo/po/CreateDate.java.ftl"),
    CreateOperateDelete(COMMON,"{commonModule}/src/main/java/{commonPackage}/pojo/po/CreateOperateDelete.java.ftl"),
    CreateOperateDeleteVersion(COMMON,"{commonModule}/src/main/java/{commonPackage}/pojo/po/CreateOperateDeleteVersion.java.ftl"),
    DelSign(COMMON,"{commonModule}/src/main/java/{commonPackage}/pojo/po/DelSign.java.ftl"),
    OperateBy(COMMON,"{commonModule}/src/main/java/{commonPackage}/pojo/po/OperateBy.java.ftl"),
    OperateByDate(COMMON,"{commonModule}/src/main/java/{commonPackage}/pojo/po/OperateByDate.java.ftl"),
    OperateDate(COMMON,"{commonModule}/src/main/java/{commonPackage}/pojo/po/OperateDate.java.ftl"),
    Version(COMMON,"{commonModule}/src/main/java/{commonPackage}/pojo/po/Version.java.ftl"),
    AbstractVO(COMMON,"{commonModule}/src/main/java/{commonPackage}/pojo/vo/AbstractVO.java.ftl"),
    FieldErrorVO(COMMON,"{commonModule}/src/main/java/{commonPackage}/pojo/vo/FieldErrorVO.java.ftl"),
    PageVO(COMMON,"{commonModule}/src/main/java/{commonPackage}/pojo/vo/PageVO.java.ftl"),
    ReplyVO(COMMON,"{commonModule}/src/main/java/{commonPackage}/pojo/vo/ReplyVO.java.ftl"),
    ConvertUtil(COMMON,"{commonModule}/src/main/java/{commonPackage}/util/ConvertUtil.java.ftl"),
    DateUtil(COMMON,"{commonModule}/src/main/java/{commonPackage}/util/DateUtil.java.ftl"),
    H2Util(COMMON,"{commonModule}/src/main/java/{commonPackage}/util/H2Util.java.ftl"),
    JsonUtil(COMMON,"{commonModule}/src/main/java/{commonPackage}/util/JsonUtil.java.ftl"),
    SafeUtil(COMMON,"{commonModule}/src/main/java/{commonPackage}/util/SafeUtil.java.ftl"),
    UUIDUtil(COMMON,"{commonModule}/src/main/java/{commonPackage}/util/UUIDUtil.java.ftl"),
    Check(COMMON,"{commonModule}/src/main/java/{commonPackage}/validator/Check.java.ftl"),
    ConstAnno(COMMON,"{commonModule}/src/main/java/{commonPackage}/validator/Const.java.ftl"),
    WebXSSFilter(COMMON,"{commonModule}/src/main/java/{commonPackage}/xss/WebXSSFilter.java.ftl"),
    XSSRequestWrapper(COMMON,"{commonModule}/src/main/java/{commonPackage}/xss/XSSRequestWrapper.java.ftl"),
    XSSUtil(COMMON,"{commonModule}/src/main/java/{commonPackage}/xss/XSSUtil.java.ftl"),

    /******** Core模块 Pom *********/
    CorePom(COMMON, "{coreModule}/pom.xml.ftl"),
    /******** Core模块源码 *********/
    Configuration(COMMON, "{coreModule}/src/main/java/{packageName}/config/{ProjectName}Configuration.java.ftl"),
    Properties(COMMON, "{coreModule}/src/main/java/{packageName}/config/{ProjectName}Properties.java.ftl"),
    Const(COMMON, "{coreModule}/src/main/java/{packageName}/constant/{ProjectName}Const.java.ftl"),
    EnumClass(CONST, "{coreModule}/src/main/java/{packageName}/constant/{EnumName}.java.ftl"),
    DAO(ENTITY, "{coreModule}/src/main/java/{packageName}/dao/{ClassName}DAO.java.ftl"),
    Exception(COMMON, "{coreModule}/src/main/java/{packageName}/exception/{ProjectName}Exception.java.ftl"),
    AddDTO(ENTITY, "{coreModule}/src/main/java/{packageName}/pojo/dto/{ClassName}AddDTO.java.ftl"),
    UpdateDTO(ENTITY, "{coreModule}/src/main/java/{packageName}/pojo/dto/{ClassName}UpdateDTO.java.ftl"),
    QO(ENTITY, "{coreModule}/src/main/java/{packageName}/pojo/qo/{ClassName}QO.java.ftl"),
    Example(ENTITY, "{coreModule}/src/main/java/{packageName}/pojo/example/{ClassName}Example.java.ftl"),
    Mapper(ENTITY, "{coreModule}/src/main/java/{packageName}/pojo/mapper/{ClassName}Mapper.java.ftl"),
    PO(ENTITY, "{coreModule}/src/main/java/{packageName}/pojo/po/{ClassName}PO.java.ftl"),
    ListVO(ENTITY, "{coreModule}/src/main/java/{packageName}/pojo/vo/{ClassName}ListVO.java.ftl"),
    ShowVO(ENTITY, "{coreModule}/src/main/java/{packageName}/pojo/vo/{ClassName}ShowVO.java.ftl"),
    Service(ENTITY, "{coreModule}/src/main/java/{packageName}/service/{ClassName}Service.java.ftl"),
    /******** Core模块配置 *********/
    MybatisConfig(COMMON, "{coreModule}/src/main/resources/mybatis-config.xml.ftl"),
    MybatisMapper(ENTITY, "{coreModule}/src/main/resources/{packageName}/mapper/{ClassName}Mapper.xml.ftl"),



    /******** Web模块 Pom *********/
    WebPom(COMMON, "{webModule}/pom.xml.ftl"),

    /******** Web模块源码 *********/
    App(COMMON, "{webModule}/src/main/java/{packageName}/{ProjectName}App.java.ftl"),
    AbstractController(COMMON, "{webModule}/src/main/java/{packageName}/web/AbstractController.java.ftl"),
    ExceptionTranslator(COMMON, "{webModule}/src/main/java/{packageName}/web/advice/ExceptionTranslator.java.ftl"),
    API(ENTITY, "{webModule}/src/main/java/{packageName}/web/api/{ClassName}API.java.ftl"),
    StartLogCommandLineRunner(COMMON,"{webModule}/src/main/java/{packageName}/web/config/StartLogCommandLineRunner.java.ftl"),
    SwaggerConfig(COMMON, "{webModule}/src/main/java/{packageName}/web/config/SwaggerConfig.java.ftl"),
    WebConfig(COMMON,"{webModule}/src/main/java/{packageName}/web/config/WebConfig.java.ftl"),
    WebLoginContext(COMMON, "{webModule}/src/main/java/{packageName}/web/context/WebLoginContext.java.ftl"),
    Controller(ENTITY, "{webModule}/src/main/java/{packageName}/web/rest/{ClassName}Controller.java.ftl"),
    IpUtil(COMMON, "{webModule}/src/main/java/{packageName}/web/util/IpUtil.java.ftl"),

    /******** Web模块配置 *********/
    ApplicationYml(COMMON, "{webModule}/src/main/resources/application.yml.ftl"),
    ApplicationLocalYml(COMMON, "{webModule}/src/main/resources/application-local.yml.ftl"),


    /******** Web模块单元测试源码 *********/
    AbstractTest(COMMON, "{webModule}/src/test/java/{packageName}/AbstractTest.java.ftl"),
    H2Flusher(COMMON, "{webModule}/src/test/java/{packageName}/H2Flusher.java.ftl"),
    Main(COMMON, "{webModule}/src/test/java/{packageName}/Main.java.ftl"),
    TestConfiguration(COMMON, "{webModule}/src/test/java/{packageName}/TestConfiguration.java.ftl"),
    Helper(ENTITY, "{webModule}/src/test/java/{packageName}/help/{ClassName}Helper.java.ftl"),
    AbstractWebTest(COMMON, "{webModule}/src/test/java/{packageName}/web/AbstractWebTest.java.ftl"),
    ControllerTest(ENTITY, "{webModule}/src/test/java/{packageName}/web/rest/{ClassName}ControllerTest.java.ftl"),


    /******** Web模块单元测试配置 *********/
    ApplicationTestYml(COMMON, "{webModule}/src/test/resources/application-local.yml.ftl"),
    SQL(COMMON, "{webModule}/src/test/resources/DB/{projectName}.sql.ftl"),
    SQLMarkdown(COMMON, "{webModule}/src/test/resources/DB/{projectName}.md.ftl");

    private final int type;
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
