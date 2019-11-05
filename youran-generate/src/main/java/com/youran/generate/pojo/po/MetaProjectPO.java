package com.youran.generate.pojo.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;

/**
 * 项目
 *
 * @author: cbb
 * @date: 2017/5/24
 */
public class MetaProjectPO extends BasePO {

    private Integer projectId;

    private String packageName;

    private String projectName;

    private String projectDesc;

    private String groupId;

    private String author;

    @JsonIgnore
    private Integer templateId;

    @JsonIgnore
    private Integer templateId2;

    @JsonIgnore
    private Integer templateId3;

    @JsonIgnore
    private Integer remote;

    @JsonIgnore
    private String remoteUrl;

    @JsonIgnore
    private String remoteUrl2;

    @JsonIgnore
    private String remoteUrl3;

    @JsonIgnore
    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private Integer lastHistoryId;

    @JsonIgnore
    private Integer lastHistoryId2;

    @JsonIgnore
    private Integer lastHistoryId3;

    @JsonIgnore
    private Integer projectVersion;

    private String feature;

    @JsonIgnore
    private List<MetaEntityPO> entities;

    @JsonIgnore
    private List<MetaConstPO> consts;

    @JsonIgnore
    private List<MetaManyToManyPO> mtms;

    /**
     * 根据序号获取模板id
     * <p>获取不到则抛出异常
     *
     * @param index
     * @return
     */
    public Integer forceGetTemplateIdByIndex(int index) {
        Integer tid = null;
        if (index == 1) {
            tid = templateId;
        } else if (index == 2) {
            tid = templateId2;
        } else if (index == 3) {
            tid = templateId3;
        }
        if (templateId == null) {
            throw new BusinessException(ErrorCode.BAD_PARAMETER, "模板未设置");
        }
        return tid;
    }

    public void addEntity(MetaEntityPO entity) {
        if (entities == null) {
            entities = new ArrayList<>();
        }
        entities.add(entity);
    }

    public void addConst(MetaConstPO metaConstPO) {
        if (consts == null) {
            consts = new ArrayList<>();
        }
        consts.add(metaConstPO);
    }

    public void addManyToMany(MetaManyToManyPO manyToMany) {
        if (mtms == null) {
            mtms = new ArrayList<>();
        }
        mtms.add(manyToMany);
    }


    /**
     * 将横线分割的字符串去横线化
     * 如：gen-meta -> genMeta
     *
     * @return
     */
    public String fetchNormalProjectName() {
        if (projectName == null) {
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
     *
     * @return
     */
    public String fetchCommonPackageName() {
        if (StringUtils.isBlank(packageName)) {
            return null;
        }
        int index = packageName.lastIndexOf(".");
        if (index > -1) {
            return packageName.substring(0, index) + ".common";
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getTemplateId2() {
        return templateId2;
    }

    public void setTemplateId2(Integer templateId2) {
        this.templateId2 = templateId2;
    }

    public Integer getTemplateId3() {
        return templateId3;
    }

    public void setTemplateId3(Integer templateId3) {
        this.templateId3 = templateId3;
    }

    public String getRemoteUrl2() {
        return remoteUrl2;
    }

    public void setRemoteUrl2(String remoteUrl2) {
        this.remoteUrl2 = remoteUrl2;
    }

    public String getRemoteUrl3() {
        return remoteUrl3;
    }

    public void setRemoteUrl3(String remoteUrl3) {
        this.remoteUrl3 = remoteUrl3;
    }

    public Integer getLastHistoryId2() {
        return lastHistoryId2;
    }

    public void setLastHistoryId2(Integer lastHistoryId2) {
        this.lastHistoryId2 = lastHistoryId2;
    }

    public Integer getLastHistoryId3() {
        return lastHistoryId3;
    }

    public void setLastHistoryId3(Integer lastHistoryId3) {
        this.lastHistoryId3 = lastHistoryId3;
    }
}
