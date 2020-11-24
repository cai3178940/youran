package com.youran.generate.pojo.qo;

import com.youran.common.pojo.qo.AbstractQO;
import io.swagger.annotations.ApiParam;

import java.util.List;

/**
 * 查询参数
 *
 * @author: cbb
 * @date: 2017/5/12
 */
public class MetaProjectQO extends AbstractQO {

    /**
     * 是否获取自己创建的项目
     */
    @ApiParam(value = "是否获取自己创建的项目", example = "true")
    private boolean withMyProject = true;
    /**
     * 是否获取分享给自己的项目
     */
    @ApiParam(value = "是否获取分享给自己的项目", example = "true")
    private boolean withSharedProject = true;
    /**
     * 创建人
     */
    @ApiParam(hidden = true)
    private String _creator;
    /**
     * 项目组id
     */
    @ApiParam(hidden = true)
    private List<Integer> _teamId;

    public boolean isWithMyProject() {
        return withMyProject;
    }

    public void setWithMyProject(boolean withMyProject) {
        this.withMyProject = withMyProject;
    }


    public boolean isWithSharedProject() {
        return withSharedProject;
    }

    public void setWithSharedProject(boolean withSharedProject) {
        this.withSharedProject = withSharedProject;
    }

    public String get_creator() {
        return _creator;
    }

    public void set_creator(String _creator) {
        this._creator = _creator;
    }

    public List<Integer> get_teamId() {
        return _teamId;
    }

    public void set_teamId(List<Integer> _teamId) {
        this._teamId = _teamId;
    }
}
