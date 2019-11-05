package com.youran.generate.pojo.qo;

import com.youran.common.pojo.qo.PageQO;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.Length;

import static com.youran.generate.pojo.example.CodeTemplateExample.*;

/**
 * 查询【代码模板】的参数
 *
 * @author cbb
 * @date 2019/10/24
 */
public class CodeTemplateQO extends PageQO {

    @ApiParam(value = N_CODE, example = E_CODE)
    @Length(max = 32, message = "code最大长度不能超过{max}")
    private String code;

    @ApiParam(value = N_NAME, example = E_NAME)
    @Length(max = 32, message = "name最大长度不能超过{max}")
    private String name;

    @ApiParam(value = N_TEMPLATE_TYPE, example = E_TEMPLATE_TYPE)
    private Integer templateType;

    @ApiParam(value = N_SYS_DEFAULT, example = E_SYS_DEFAULT)
    private Boolean sysDefault;

    @ApiParam(value = "创建时间排序标识【1升序,-1降序,0不排序】", example = "1")
    private Integer createdTimeSortSign;

    @ApiParam(value = "修改时间排序标识【1升序,-1降序,0不排序】", example = "1")
    private Integer operatedTimeSortSign;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

