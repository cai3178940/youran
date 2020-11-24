package com.youran.generate.pojo.po.team;

import com.youran.common.pojo.po.AbstractPO;
import com.youran.common.pojo.po.CreatedOperatedDeletedVersion;

import java.util.Date;

/**
 * 项目组
 * <p>项目组
 *
 * @author cbb
 * @date 2020/11/23
 */
public class ProjectTeamPO extends AbstractPO implements CreatedOperatedDeletedVersion {

    /**
     * 主键ID
     */
    private Integer teamId;

    /**
     * 名称
     */
    private String name;

    /**
     * 创建时间【yyyy-MM-dd HH:mm:ss】
     */
    private Date createdTime;

    /**
     * 创建人【最大长度20】
     */
    private String createdBy;

    /**
     * 修改时间【yyyy-MM-dd HH:mm:ss】
     */
    private Date operatedTime;

    /**
     * 修改人【最大长度20】
     */
    private String operatedBy;

    /**
     * 乐观锁版本号【整型】
     */
    private Integer version;

    /**
     * 逻辑删除标识【0-未删除，1-已删除】
     */
    private Boolean deleted;

    public Integer getTeamId() {
        return this.teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Date getCreatedTime() {
        return this.createdTime;
    }

    @Override
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String getCreatedBy() {
        return this.createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public Date getOperatedTime() {
        return this.operatedTime;
    }

    @Override
    public void setOperatedTime(Date operatedTime) {
        this.operatedTime = operatedTime;
    }

    @Override
    public String getOperatedBy() {
        return this.operatedBy;
    }

    @Override
    public void setOperatedBy(String operatedBy) {
        this.operatedBy = operatedBy;
    }

    @Override
    public Integer getVersion() {
        return this.version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public Boolean getDeleted() {
        return this.deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }


}

