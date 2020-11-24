package com.youran.generate.pojo.vo.team;

import com.youran.common.pojo.vo.AbstractVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import static com.youran.generate.pojo.example.team.ProjectTeamExample.*;

/**
 * 【项目组】详情展示对象
 *
 * @author cbb
 * @date 2020/11/23
 */
@ApiModel(description = "【项目组】详情展示对象")
public class ProjectTeamShowVO extends AbstractVO {

    @ApiModelProperty(notes = N_TEAM_ID,example = E_TEAM_ID)
    private Integer teamId;

    @ApiModelProperty(notes = N_NAME,example = E_NAME)
    private String name;


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



}

