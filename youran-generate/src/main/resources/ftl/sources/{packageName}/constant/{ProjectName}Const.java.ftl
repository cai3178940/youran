<#include "/common.ftl">
package ${packageName}.constant;

<@classCom "常量"></@classCom>
public class ${ProjectName}Const {

    public static final String ${projectName?upper_case}_ROOT_PATH = "${r'$'}{${packageName}.rootPath:}";

}
