package com.youran.generate.pojo.po;

import com.youran.common.pojo.po.AbstractPO;
import com.youran.common.pojo.po.CreateOperateDeleteVersion;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.stream;

/**
 * Title: 元数据项目
 * Description:
 * Author: cbb
 * Create Time:2017/5/24 11:45
 */
public class MetaProjectPO extends AbstractPO implements CreateOperateDeleteVersion {

    private Integer projectId;

    private String packageName;

    private String projectName;

    private String author;

    private Integer remote;

    private String remoteUrl;

    private String username;

    private String password;

    private Integer lastHistoryId;

    private Integer projectVersion;

    private Date createDate;

    private String createBy;

    private Date operateDate;

    private String operateBy;

    private Integer version;

    private Integer delSign;

    private List<MetaEntityPO> entities;

    private List<MetaConstPO> consts;

    private List<MetaManyToManyPO> mtms;



    public void addEntity(MetaEntityPO entity){
        if(entities==null){
            entities = new ArrayList<>();
        }
        entities.add(entity);
    }

    public void addConst(MetaConstPO metaConstPO){
        if(consts==null){
            consts = new ArrayList<>();
        }
        consts.add(metaConstPO);
    }

    public void addManyToMany(MetaManyToManyPO manyToMany){
        if(mtms==null){
            mtms = new ArrayList<>();
        }
        mtms.add(manyToMany);
    }


    /**
     * 将横线分割的字符串去横线化
     * 如：gen-meta -> genMeta
     * @return
     */
    public String fetchNormalProjectName(){
        if(projectName==null){
            return null;
        }
        String[] split = projectName.split("-|_");
        return stream(split)
                .reduce((s, s2) -> s.concat(StringUtils.capitalize(s2)))
                .get();
    }

    /**
     * 获取common包名
     * packageName最后的.xxx改为.common
     * @return
     */
    public String fetchCommonPackageName(){
        if(StringUtils.isBlank(packageName)){
            return null;
        }
        int index = packageName.lastIndexOf(".");
        if(index>-1){
            return packageName.substring(0,index)+".common";
        }
        return "common";
    }

    public List<MetaManyToManyPO> getMtms() {
        return mtms;
    }

    public void setMtms(List<MetaManyToManyPO> mtms) {
        this.mtms = mtms;
    }

    public List<MetaConstPO> getConsts() {
        return consts;
    }

    public void setConsts(List<MetaConstPO> consts) {
        this.consts = consts;
    }

    public List<MetaEntityPO> getEntities() {
        return entities;
    }

    public void setEntities(List<MetaEntityPO> entities) {
        this.entities = entities;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public String getOperateBy() {
        return operateBy;
    }

    public void setOperateBy(String operateBy) {
        this.operateBy = operateBy;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getDelSign() {
        return delSign;
    }

    public void setDelSign(Integer delSign) {
        this.delSign = delSign;
    }

    public Integer getRemote() {
        return remote;
    }

    public void setRemote(Integer remote) {
        this.remote = remote;
    }

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getLastHistoryId() {
        return lastHistoryId;
    }

    public void setLastHistoryId(Integer lastHistoryId) {
        this.lastHistoryId = lastHistoryId;
    }

    public Integer getProjectVersion() {
        return projectVersion;
    }

    public void setProjectVersion(Integer projectVersion) {
        this.projectVersion = projectVersion;
    }
}
