package com.youran.generate.pojo.po.team;

import com.youran.common.pojo.po.AbstractPO;
import com.youran.common.pojo.po.Created;

import java.util.Date;

/**
 * 项目组成员
 * <p>项目组成员
 *
 * @author cbb
 * @date 2020/11/23
 */
public class ProjectTeamMemberPO extends AbstractPO implements Created {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 项目组id
     */
    private Integer teamId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 创建时间【yyyy-MM-dd HH:mm:ss】
     */
    private Date createdTime;

    /**
     * 创建人【最大长度20】
     */
    private String createdBy;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTeamId() {
        return this.teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
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


}

