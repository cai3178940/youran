package com.youran.generate.pojo.template;

import com.youran.generate.pojo.po.MetaConstPO;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaManyToManyPO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.util.FreeMakerUtil;
import freemarker.template.TemplateModel;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * <p>Title: 基本模型</p>
 * <p>Description: </p>
 *
 * @author cbb
 * @date 2018/8/3
 */
public class BaseModel {

    /**
     * 封装静态方法模型
     */
    public static final TemplateModel MetadataUtil;
    public static final TemplateModel JFieldType;
    public static final TemplateModel QueryType;
    public static final TemplateModel MetaSpecialField;

    static{
        MetadataUtil = FreeMakerUtil.getStaticModel(com.youran.generate.util.MetadataUtil.class);
        JFieldType = FreeMakerUtil.getStaticModel(com.youran.generate.constant.JFieldType.class);
        QueryType = FreeMakerUtil.getStaticModel(com.youran.generate.constant.QueryType.class);
        MetaSpecialField = FreeMakerUtil.getStaticModel(com.youran.generate.constant.MetaSpecialField.class);
    }


    /**
     * 实体列表
     */
    private List<MetaEntityPO> metaEntities;
    /**
     * 常量列表
     */
    private List<MetaConstPO> metaConsts;
    /**
     * 多对多列表
     */
    private List<MetaManyToManyPO> mtms;
    /**
     * 包名
     */
    private String packageName;
    /**
     * common包名
     */
    private String commonPackage;
    /**
     * 项目名称-驼峰格式-首字母小写
     */
    private String projectName;
    /**
     * 项目名称-驼峰格式-首字母大写
     */
    private String projectNameUpper;
    /**
     * 项目名称-短横杠分割
     */
    private String projectNameSplit;
    /**
     * 原始模块名
     */
    private String originProjectName;
    /**
     * maven的groupId
     */
    private String groupId;
    /**
     * 作者
     */
    private String author;
    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 导入模块
     */
    private Set<String> imports;
    /**
     * 静态导入模块
     */
    private Set<String> staticImports;
    /**
     * spring注入bean
     */
    private Set<String[]> autowired;





    public BaseModel(MetaProjectPO project){
        //所有实体
        this.metaEntities = project.getEntities();
        //所有常量
        this.metaConsts = project.getConsts();
        //包名
        this.packageName = project.getPackageName();
        //通用模块名
        this.commonPackage = project.fetchCommonPackageName();
        //项目名：驼峰格式-首字母小写
        this.projectName = project.fetchNormalProjectName();
        //项目名：驼峰格式-首字母大写
        this.projectNameUpper = StringUtils.capitalize(project.fetchNormalProjectName());
        //项目名：短横杠分割
        this.projectNameSplit = project.getProjectName();
        //原始模块名
        this.originProjectName = project.getProjectName();
        //groupId
        this.groupId = project.getGroupId();
        //作者
        this.author = project.getAuthor();
        //创建时间
        this.createdTime = project.getCreatedTime();
        //多对多关联
        this.mtms = project.getMtms();

        this.imports = new TreeSet<>();
        this.staticImports = new TreeSet<>();
        this.autowired = new TreeSet<>();
    }


    /**
     * 添加依赖
     * @param classPath 类全路径
     */
    public void addImport(String classPath){
        this.imports.add(classPath);
    }

    /**
     * 添加静态依赖
     * @param classPath 类全路径
     */
    public void addStaticImport(String classPath){
        this.staticImports.add(classPath);
    }

    /**
     * 添加spring bean注入
     * @param packageName 包名
     * @param className 类名
     */
    public void addAutowired(String packageName,String className){
        this.addImport("org.springframework.beans.factory.annotation.Autowired");
        this.addImport(packageName+"."+className);
        this.autowired.add(new String[]{packageName,className});
    }


    /**
     * 打印import依赖
     * @return
     */
    public String printImport(){
        // 打印外部依赖
        StringBuilder sb1 = new StringBuilder();
        // 打印java内建依赖
        StringBuilder sb2 = new StringBuilder();
        for (String imp : imports) {
            if(!imp.startsWith("java.") && !imp.startsWith("javax.")){
                sb1.append("import ").append(imp).append(";\n");
            }else{
                sb2.append("import ").append(imp).append(";\n");
            }
        }
        // 打印静态外部依赖
        StringBuilder sb3 = new StringBuilder();
        // 打印静态java内建依赖
        StringBuilder sb4 = new StringBuilder();
        for (String imp : staticImports) {
            if(!imp.startsWith("java.") && !imp.startsWith("javax.")){
                sb3.append("import static ").append(imp).append(";\n");
            }else{
                sb4.append("import static ").append(imp).append(";\n");
            }
        }
        StringBuilder sb = new StringBuilder();
        if(sb1.length()>0){
            sb.append("\n").append(sb1);
        }
        if(sb2.length()>0){
            sb.append("\n").append(sb2);
        }
        if(sb3.length()>0){
            sb.append("\n").append(sb3);
        }
        if(sb4.length()>0){
            sb.append("\n").append(sb4);
        }

        if(sb.length()>0){
            sb.deleteCharAt(0);
        }

        return sb.toString();
    }


    /**
     * 打印依赖注入
     * @return
     */
    public String printAutowired(){
        StringBuilder sb = new StringBuilder();
        for (String[] aw : autowired) {
            sb.append("    @Autowired\n")
                .append("    private ").append(aw[1]).append(" ").append(StringUtils.uncapitalize(aw[1])).append(";\n");
        }
        return sb.toString();
    }

    public List<MetaEntityPO> getMetaEntities() {
        return metaEntities;
    }

    public void setMetaEntities(List<MetaEntityPO> metaEntities) {
        this.metaEntities = metaEntities;
    }

    public List<MetaConstPO> getMetaConsts() {
        return metaConsts;
    }

    public void setMetaConsts(List<MetaConstPO> metaConsts) {
        this.metaConsts = metaConsts;
    }

    public List<MetaManyToManyPO> getMtms() {
        return mtms;
    }

    public void setMtms(List<MetaManyToManyPO> mtms) {
        this.mtms = mtms;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getCommonPackage() {
        return commonPackage;
    }

    public void setCommonPackage(String commonPackage) {
        this.commonPackage = commonPackage;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectNameUpper() {
        return projectNameUpper;
    }

    public void setProjectNameUpper(String projectNameUpper) {
        this.projectNameUpper = projectNameUpper;
    }

    public String getProjectNameSplit() {
        return projectNameSplit;
    }

    public void setProjectNameSplit(String projectNameSplit) {
        this.projectNameSplit = projectNameSplit;
    }

    public String getOriginProjectName() {
        return originProjectName;
    }

    public void setOriginProjectName(String originProjectName) {
        this.originProjectName = originProjectName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
