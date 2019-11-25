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

    /**
     * 项目id
     */
    private Integer projectId;
    /**
     * 包名
     */
    private String packageName;
    /**
     * 项目名
     */
    private String projectName;
    /**
     * 项目描述
     */
    private String projectDesc;
    /**
     * maven的groupId
     */
    private String groupId;
    /**
     * 代码注释中的作者
     */
    private String author;
    /**
     * 第1个模板id
     */
    @JsonIgnore
    private Integer templateId;
    /**
     * 第2个模板id
     */
    @JsonIgnore
    private Integer templateId2;
    /**
     * 第3个模板id
     */
    @JsonIgnore
    private Integer templateId3;
    /**
     * 是否开启Git远程仓库功能
     */
    @JsonIgnore
    private Boolean remote;
    /**
     * 远程仓库地址1
     * 对应第1个模板
     */
    @JsonIgnore
    private String remoteUrl;
    /**
     * 远程仓库地址2
     * 对应第2个模板
     */
    @JsonIgnore
    private String remoteUrl2;
    /**
     * 远程仓库地址3
     * 对应第3个模板
     */
    @JsonIgnore
    private String remoteUrl3;
    /**
     * 远程仓库用户名
     */
    @JsonIgnore
    private String username;
    /**
     * 远程仓库密码
     * 支持accessToken
     */
    @JsonIgnore
    private String password;
    /**
     * 远程仓库地址1的最后提交历史id
     */
    @JsonIgnore
    private Integer lastHistoryId;
    /**
     * 远程仓库地址2的最后提交历史id
     */
    @JsonIgnore
    private Integer lastHistoryId2;
    /**
     * 远程仓库地址3的最后提交历史id
     */
    @JsonIgnore
    private Integer lastHistoryId3;
    /**
     * 项目内部版本号
     */
    @JsonIgnore
    private Integer projectVersion;
    /**
     * 项目特性
     */
    private String feature;
    /**
     * 项目下的所有实体
     */
    @JsonIgnore
    private List<MetaEntityPO> entities;
    /**
     * 项目下的所有常量
     */
    @JsonIgnore
    private List<MetaConstPO> consts;
    /**
     * 项目下的所有多对多
     */
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
        Integer id = null;
        if (index == 1) {
            id = templateId;
        } else if (index == 2) {
            id = templateId2;
        } else if (index == 3) {
            id = templateId3;
        }
        if (id == null) {
            throw new BusinessException(ErrorCode.BAD_PARAMETER, "模板未设置");
        }
        return id;
    }

    /**
     * 根据序号获取最后提交历史
     *
     * @param index
     * @return
     */
    public Integer getLastHistoryIdByIndex(int index) {
        Integer id = null;
        if (index == 1) {
            id = lastHistoryId;
        } else if (index == 2) {
            id = lastHistoryId2;
        } else if (index == 3) {
            id = lastHistoryId3;
        }
        return id;
    }

    /**
     * 根据序号设置最后提交历史
     *
     * @param index
     * @param lastHistoryId
     */
    public void setLastHistoryIdByIndex(int index, Integer lastHistoryId) {
        if (index == 1) {
            this.lastHistoryId = lastHistoryId;
        } else if (index == 2) {
            this.lastHistoryId2 = lastHistoryId;
        } else if (index == 3) {
            this.lastHistoryId3 = lastHistoryId;
        } else {
            throw new BusinessException("模板序号有误：" + index);
        }
    }

    /**
     * 根据序号获取远程git仓库地址
     *
     * @param index
     * @return
     */
    public String getRemoteUrlByIndex(int index) {
        String url = null;
        if (index == 1) {
            url = remoteUrl;
        } else if (index == 2) {
            url = remoteUrl2;
        } else if (index == 3) {
            url = remoteUrl3;
        }
        return url;
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

    public Boolean getRemote() {
        return remote;
    }

    public void setRemote(Boolean remote) {
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
