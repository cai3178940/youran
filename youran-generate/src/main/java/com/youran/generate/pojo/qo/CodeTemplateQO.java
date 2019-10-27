package com.youran.generate.pojo.qo;

import com.youran.common.pojo.qo.PageQO;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.Length;

import static com.youran.generate.pojo.example.CodeTemplateExample.*;

/**
 * <p>Title: 查询【代码模板】的参数</p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/10/24
 */
public class CodeTemplateQO extends PageQO {

    @ApiParam(value = N_NAME,example = E_NAME)
    @Length(max = 32,message = "name最大长度不能超过{max}")
    private String name;

    @ApiParam(value = N_TEMPLATE_TYPE,example = E_TEMPLATE_TYPE)
    private Integer templateType;

    @ApiParam(value = N_SYS_DEFAULT,example = E_SYS_DEFAULT)
    private Boolean sysDefault;

    @ApiParam(value = N_FROM_TEMPLATE_ID,example = E_FROM_TEMPLATE_ID)
    private Integer fromTemplateId;

    @ApiParam(value = "创建时间排序标识【1升序,-1降序,0不排序】",example = "1")
    private Integer createdTimeSortSign;

    @ApiParam(value = "修改时间排序标识【1升序,-1降序,0不排序】",example = "1")
    private Integer operatedTimeSortSign;


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTemplateType() {
        return this.templateType;
    }

    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }

    public Boolean getSysDefault() {
        return this.sysDefault;
    }

    public void setSysDefault(Boolean sysDefault) {
        this.sysDefault = sysDefault;
    }

    public Integer getFromTemplateId() {
        return this.fromTemplateId;
    }

    public void setFromTemplateId(Integer fromTemplateId) {
        this.fromTemplateId = fromTemplateId;
    }


    public Integer getCreatedTimeSortSign() {
        return this.createdTimeSortSign;
    }

    public void setCreatedTimeSortSign(Integer createdTimeSortSign) {
        this.createdTimeSortSign = createdTimeSortSign;
    }

    public Integer getOperatedTimeSortSign() {
        return this.operatedTimeSortSign;
    }

    public void setOperatedTimeSortSign(Integer operatedTimeSortSign) {
        this.operatedTimeSortSign = operatedTimeSortSign;
    }

}

