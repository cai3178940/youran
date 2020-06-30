package com.youran.generate.pojo.qo;

import com.youran.common.pojo.qo.AbstractQO;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.AssertTrue;

import static com.youran.generate.pojo.example.MetaConstExample.*;


/**
 * 查询参数
 *
 * @author: cbb
 * @date: 2017/6/14
 */
public class MetaConstDetailQO extends AbstractQO {

    @ApiParam(value = N_PROJECTID, example = E_PROJECTID)
    private Integer projectId;

    @ApiParam(value = N_CONSTID, example = E_CONSTID)
    private Integer constId;

    @ApiParam(value = N_CONSTNAME, example = E_CONSTNAME)
    private String constName;

    @AssertTrue(message = "缺少入参")
    public boolean validate() {
        if (constId == null &&
            (StringUtils.isBlank(constName) || projectId == null)) {
            return false;
        }
        return true;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName = constName;
    }

    public Integer getConstId() {
        return constId;
    }

    public void setConstId(Integer constId) {
        this.constId = constId;
    }
}
