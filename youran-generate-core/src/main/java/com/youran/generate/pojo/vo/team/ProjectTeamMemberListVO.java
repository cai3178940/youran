package com.youran.generate.pojo.vo.team;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.youran.common.constant.JsonFieldConst;
import com.youran.common.pojo.vo.AbstractVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

import static com.youran.generate.pojo.example.team.ProjectTeamMemberExample.*;

/**
 * 【项目组成员】列表展示对象
 *
 * @author cbb
 * @date 2020/11/23
 */
@ApiModel(description = "【项目组成员】列表展示对象")
public class ProjectTeamMemberListVO extends AbstractVO {

    @ApiModelProperty(notes = N_ID, example = E_ID)
    private Integer id;

    @ApiModelProperty(notes = N_USERNAME, example = E_USERNAME)
    private String username;

    @ApiModelProperty(notes = N_CREATED_TIME, example = E_CREATED_TIME)
    @JsonFormat(pattern = JsonFieldConst.DEFAULT_DATETIME_FORMAT, timezone = "GMT+8")
    private Date createdTime;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreatedTime() {
        return this.createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }



}

