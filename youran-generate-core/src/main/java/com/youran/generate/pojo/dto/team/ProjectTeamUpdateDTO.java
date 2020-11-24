package com.youran.generate.pojo.dto.team;

import com.youran.common.pojo.dto.AbstractDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.team.ProjectTeamExample.*;

/**
 * 修改【项目组】的参数
 *
 * @author cbb
 * @date 2020/11/23
 */
@ApiModel(description = "修改【项目组】的参数")
public class ProjectTeamUpdateDTO extends AbstractDTO {

    @ApiModelProperty(notes = N_TEAM_ID,example = E_TEAM_ID,required = true)
    @NotNull
    private Integer teamId;

    @ApiModelProperty(notes = N_NAME,example = E_NAME,required = true)
    @NotNull
    @Length(max = 32)
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

