package com.youran.generate.pojo.dto.chart;

import com.youran.common.pojo.dto.AbstractDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import static com.youran.generate.pojo.example.chart.MetaDashboardExample.*;

/**
 * 新增【看板】的参数
 *
 * @author cbb
 * @date 2020/06/13
 */
@ApiModel(description = "新增【看板】的参数")
public class MetaDashboardAddDTO extends AbstractDTO {

    @ApiModelProperty(notes = N_NAME, example = E_NAME, required = true)
    @NotNull
    @Length(max = 64)
    private String name;

    @ApiModelProperty(notes = N_TITLE, example = E_TITLE, required = true)
    @NotNull
    @Length(max = 64)
    private String title;

    @ApiModelProperty(notes = N_MODULE, example = E_MODULE)
    @Length(max = 50)
    private String module;

    @ApiModelProperty(notes = N_FEATURE, example = E_FEATURE)
    @Length(max = 32)
    private String feature;

    @ApiModelProperty(notes = N_PROJECT_ID, example = E_PROJECT_ID, required = true)
    @NotNull
    private Integer projectId;


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModule() {
        return this.module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getFeature() {
        return this.feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Integer getProjectId() {
        return this.projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

}


