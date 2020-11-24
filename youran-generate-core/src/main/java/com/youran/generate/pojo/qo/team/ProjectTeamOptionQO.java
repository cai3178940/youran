package com.youran.generate.pojo.qo.team;

import com.youran.common.pojo.qo.OptionQO;
import io.swagger.annotations.ApiParam;

import java.util.List;

/**
 * 查询【项目组】选项的参数
 *
 * @author cbb
 * @date 2020/11/24
 */
public class ProjectTeamOptionQO extends OptionQO<Integer, String> {

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
