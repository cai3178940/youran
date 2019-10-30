package com.youran.generate.pojo.vo;

import com.youran.common.pojo.vo.AbstractVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/8/29
 */
public class CodeTreeVO extends AbstractVO {

    @ApiModelProperty(notes = "项目id", example = "2")
    private Integer projectId;

    @ApiModelProperty(notes = "项目版本号", example = "10")
    private Integer projectVersion;

    @ApiModelProperty(notes = "代码目录树")
    private List<FileNodeVO> tree;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getProjectVersion() {
        return projectVersion;
    }

    public void setProjectVersion(Integer projectVersion) {
        this.projectVersion = projectVersion;
    }

    public List<FileNodeVO> getTree() {
        return tree;
    }

    public void setTree(List<FileNodeVO> tree) {
        this.tree = tree;
    }

}
