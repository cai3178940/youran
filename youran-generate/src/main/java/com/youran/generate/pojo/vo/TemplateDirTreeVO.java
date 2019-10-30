package com.youran.generate.pojo.vo;

import com.youran.common.pojo.vo.AbstractVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p>Title: 模板目录树</p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/10/30
 */
public class TemplateDirTreeVO extends AbstractVO {

    @ApiModelProperty(notes = "模板id", example = "2")
    private Integer templateId;

    @ApiModelProperty(notes = "模板文件目录树")
    private List<FileNodeVO> tree;

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public List<FileNodeVO> getTree() {
        return tree;
    }

    public void setTree(List<FileNodeVO> tree) {
        this.tree = tree;
    }
}
