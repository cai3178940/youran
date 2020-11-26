package com.youran.generate.pojo.dto;

import com.youran.common.pojo.dto.AbstractDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.MetaProjectExample.E_PROJECTID;
import static com.youran.generate.pojo.example.MetaProjectExample.N_PROJECTID;

/**
 * 项目共享DTO
 *
 * @author cbb
 * @date 2020/11/24
 */
@ApiModel(description = "项目共享入参")
public class MetaProjectShareDTO extends AbstractDTO {

    @ApiModelProperty(notes = N_PROJECTID, example = E_PROJECTID)
    @NotNull
    private Integer projectId;

    @ApiModelProperty(notes = "项目组id", example = "1")
    private Integer teamId;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }
}
