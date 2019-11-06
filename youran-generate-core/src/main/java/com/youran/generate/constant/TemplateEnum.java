package com.youran.generate.constant;


import com.youran.generate.pojo.po.TemplateFilePO;

import java.util.ArrayList;
import java.util.List;

import static com.youran.generate.constant.ContextType.*;

/**
 * 模板枚举
 *
 * @author: cbb
 * @date: 2017/9/20
 */
public enum TemplateEnum {


    /******** 根Pom *********/
    RootPom(GLOBAL, "pom.xml.ftl"),
    README(GLOBAL, "README.md.ftl"),
    GITIGNORE(GLOBAL, ".gitignore.ftl"),
    /******** Common模块 Pom *********/
    CommonPom(GLOBAL, "{commonModule}/pom.xml.ftl"),
    /******** Common模块 源码 *********/
    ErrorCode(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/constant/ErrorCode.java.ftl"),
    JsonFieldConst(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/constant/JsonFieldConst.java.ftl"),
    CommonLoginContext(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/context/LoginContext.java.ftl"),
    MyCustomDateEditor(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/convert/MyCustomDateEditor.java.ftl"),
    DAO(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/dao/DAO.java.ftl"),
    BusinessException(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/exception/BusinessException.java.ftl"),
    EnableOptimisticLock(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/optimistic/EnableOptimisticLock.java.ftl"),
    OptimisticException(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/optimistic/OptimisticException.java.ftl"),
    OptimisticLock(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/optimistic/OptimisticLock.java.ftl"),
    OptimisticLockAspect(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/optimistic/OptimisticLockAspect.java.ftl"),
    OptimisticLockConfiguration(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/optimistic/OptimisticLockConfiguration.java.ftl"),
    AbstractDTO(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/pojo/dto/AbstractDTO.java.ftl"),
    AbstractQO(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/pojo/qo/AbstractQO.java.ftl"),
    PageQO(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/pojo/qo/PageQO.java.ftl"),
    AbstractPO(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/pojo/po/AbstractPO.java.ftl"),
    CreatedBy(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/pojo/po/CreatedBy.java.ftl"),
    Created(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/pojo/po/Created.java.ftl"),
    CreatedTime(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/pojo/po/CreatedTime.java.ftl"),
    CreatedOperatedDeleted(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/pojo/po/CreatedOperatedDeleted.java.ftl"),
    CreatedOperatedDeletedVersion(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/pojo/po/CreatedOperatedDeletedVersion.java.ftl"),
    Deleted(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/pojo/po/Deleted.java.ftl"),
    OperatedBy(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/pojo/po/OperatedBy.java.ftl"),
    Operated(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/pojo/po/Operated.java.ftl"),
    OperatedTime(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/pojo/po/OperatedTime.java.ftl"),
    Version(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/pojo/po/Version.java.ftl"),
    AbstractVO(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/pojo/vo/AbstractVO.java.ftl"),
    FieldErrorVO(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/pojo/vo/FieldErrorVO.java.ftl"),
    PageVO(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/pojo/vo/PageVO.java.ftl"),
    ReplyVO(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/pojo/vo/ReplyVO.java.ftl"),
    ConvertUtil(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/util/ConvertUtil.java.ftl"),
    DateUtil(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/util/DateUtil.java.ftl"),
    TempDirUtil(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/util/TempDirUtil.java.ftl"),
    JsonUtil(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/util/JsonUtil.java.ftl"),
    MessageSourceUtil(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/util/MessageSourceUtil.java.ftl"),
    SafeUtil(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/util/SafeUtil.java.ftl"),
    SpringUtil(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/util/SpringUtil.java.ftl"),
    UUIDUtil(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/util/UUIDUtil.java.ftl"),
    Check(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/validator/Check.java.ftl"),
    ConstAnno(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/validator/Const.java.ftl"),
    JacksonXssDeserializer(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/xss/JacksonXssDeserializer.java.ftl"),
    WebXSSFilter(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/xss/WebXSSFilter.java.ftl"),
    XSSRequestWrapper(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/xss/XSSRequestWrapper.java.ftl"),
    XSSUtil(GLOBAL, "{commonModule}/src/main/java/{commonPackage}/xss/XSSUtil.java.ftl"),

    /******** Core模块 Pom *********/
    CorePom(GLOBAL, "{coreModule}/pom.xml.ftl"),
    /******** Core模块源码 *********/
    Configuration(GLOBAL, "{coreModule}/src/main/java/{packageName}/config/{ProjectName}Configuration.java.ftl"),
    EnumClass(CONST, "{coreModule}/src/main/java/{packageName}/constant/{EnumName}.java.ftl"),
    CoreDAO(ENTITY, "{coreModule}/src/main/java/{packageName}/dao/{ClassName}DAO.java.ftl"),
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
    MessagesProperties(GLOBAL, "{coreModule}/src/main/resources/messages.properties.ftl"),
    MessagesPropertiesEnUs(GLOBAL, "{coreModule}/src/main/resources/messages_en_US.properties.ftl"),
    MybatisConfig(GLOBAL, "{coreModule}/src/main/resources/mybatis-config.xml.ftl"),
    DefaultProperties(GLOBAL, "{coreModule}/src/main/resources/config/{project-name}-default.properties.ftl"),
    MybatisMapper(ENTITY, "{coreModule}/src/main/resources/{packageName}/dao/{ClassName}DAO.xml.ftl"),


    /******** Web模块 Pom *********/
    WebPom(GLOBAL, "{webModule}/pom.xml.ftl"),

    /******** Web模块源码 *********/
    App(GLOBAL, "{webModule}/src/main/java/{packageName}/{ProjectName}App.java.ftl"),
    AbstractController(GLOBAL, "{webModule}/src/main/java/{packageName}/web/AbstractController.java.ftl"),
    ExceptionTranslator(GLOBAL, "{webModule}/src/main/java/{packageName}/web/advice/ExceptionTranslator.java.ftl"),
    API(ENTITY, "{webModule}/src/main/java/{packageName}/web/api/{ClassName}API.java.ftl"),
    StartLogCommandLineRunner(GLOBAL, "{webModule}/src/main/java/{packageName}/web/config/StartLogCommandLineRunner.java.ftl"),
    SwaggerConfig(GLOBAL, "{webModule}/src/main/java/{packageName}/web/config/SwaggerConfig.java.ftl"),
    WebConfig(GLOBAL, "{webModule}/src/main/java/{packageName}/web/config/WebConfig.java.ftl"),
    WebConst(GLOBAL, "{webModule}/src/main/java/{packageName}/web/constant/WebConst.java.ftl"),
    WebLoginContext(GLOBAL, "{webModule}/src/main/java/{packageName}/web/context/WebLoginContext.java.ftl"),
    Controller(ENTITY, "{webModule}/src/main/java/{packageName}/web/rest/{ClassName}Controller.java.ftl"),
    IpUtil(GLOBAL, "{webModule}/src/main/java/{packageName}/web/util/IpUtil.java.ftl"),

    /******** Web模块配置 *********/
    ApplicationYml(GLOBAL, "{webModule}/src/main/resources/application.yml.ftl"),
    ApplicationLocalYml(GLOBAL, "{webModule}/src/main/resources/application-local.yml.ftl"),


    /******** Web模块单元测试源码 *********/
    AbstractTest(GLOBAL, "{webModule}/src/test/java/{packageName}/AbstractTest.java.ftl"),
    H2Flusher(GLOBAL, "{webModule}/src/test/java/{packageName}/H2Flusher.java.ftl"),
    Main(GLOBAL, "{webModule}/src/test/java/{packageName}/Main.java.ftl"),
    TestConfiguration(GLOBAL, "{webModule}/src/test/java/{packageName}/TestConfiguration.java.ftl"),
    Helper(ENTITY, "{webModule}/src/test/java/{packageName}/help/{ClassName}Helper.java.ftl"),
    H2Util(GLOBAL, "{webModule}/src/test/java/{packageName}/util/H2Util.java.ftl"),
    AbstractWebTest(GLOBAL, "{webModule}/src/test/java/{packageName}/web/AbstractWebTest.java.ftl"),
    ControllerTest(ENTITY, "{webModule}/src/test/java/{packageName}/web/rest/{ClassName}ControllerTest.java.ftl"),


    /******** Web模块单元测试配置 *********/
    ApplicationTestYml(GLOBAL, "{webModule}/src/test/resources/application-local.yml.ftl"),
    SQL(GLOBAL, "{webModule}/src/test/resources/DB/{projectName}.sql.ftl");

    private final ContextType type;
    private final String template;

    public static void main(String[] args) {
        List<TemplateFilePO> list = new ArrayList<>();
        for (TemplateEnum templateEnum : TemplateEnum.values()) {
            String template = templateEnum.template;
            String[] split = template.split("/");
            String fileName = template;
            String fileDir = "/";
            if(split.length>1){
                fileName = split[split.length-1];
                fileDir = "/"+template.substring(0,template.lastIndexOf("/"));
            }
            TemplateFilePO file = new TemplateFilePO();
            file.setFileName(fileName);
            file.setFileDir(fileDir);
            file.setContextType(templateEnum.type.getValue());
            file.setAbstracted(false);
            list.add(file);
        }
        System.out.println(com.youran.common.util.JsonUtil.toJSONString(list,true));
    }


    TemplateEnum(ContextType type, String template) {
        this.type = type;
        this.template = template;
    }

    public ContextType getType() {
        return type;
    }

    public String getTemplate() {
        return template;
    }

}
