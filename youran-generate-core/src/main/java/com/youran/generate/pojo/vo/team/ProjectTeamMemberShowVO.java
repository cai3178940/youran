package com.youran.generate.pojo.vo.team;

import com.youran.common.pojo.vo.AbstractVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import static com.youran.generate.pojo.example.team.ProjectTeamMemberExample.*;

/**
 * 【项目组成员】详情展示对象
 *
 * @author cbb
 * @date 2020/11/23
 */
@ApiModel(description = "【项目组成员】详情展示对象")
public class ProjectTeamMemberShowVO extends AbstractVO {

    @ApiModelProperty(notes = N_ID,example = E_ID)
    private Integer id;

    @ApiModelProperty(notes = N_TEAM_ID,example = E_TEAM_ID)
    private Integer teamId;

    @ApiModelProperty(notes = N_USERNAME,example = E_USERNAME)
    private String username;


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



}

