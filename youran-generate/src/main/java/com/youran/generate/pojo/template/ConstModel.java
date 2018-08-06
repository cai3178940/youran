package com.youran.generate.pojo.template;

import com.youran.generate.constant.MetaConstType;
import com.youran.generate.pojo.po.MetaConstDetailPO;
import com.youran.generate.pojo.po.MetaConstPO;
import com.youran.generate.pojo.po.MetaProjectPO;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * <p>Title: 常量模型</p>
 * <p>Description: </p>
 *
 * @author cbb
 * @date 2018/8/3
 */
public class ConstModel extends BaseModel{

    /**
     * 常量对象
     */
    private MetaConstPO metaConst;
    /**
     * 常量名称-首字母小写
     */
    private String constName;
    /**
     * 常量名称-首字母大写
     */
    private String constNameUpper;
    /**
     * 常量描述
     */
    private String remark;
    /**
     * 常量类型
     */
    private Integer constType;
    /**
     * 常量类型-字符串
     */
    private String constTypeStr;
    /**
     * 常量值列表
     */
    private List<MetaConstDetailPO> detailList;

    public ConstModel(MetaProjectPO project,MetaConstPO metaConst) {
        super(project);
        this.metaConst = metaConst;
        this.constName = StringUtils.uncapitalize(metaConst.getConstName());
        this.constNameUpper = StringUtils.capitalize(metaConst.getConstName());
        this.remark = metaConst.getConstRemark();
        this.constType = metaConst.getConstType();
        this.constTypeStr = MetaConstType.convertString(metaConst.getConstType());
        this.detailList = metaConst.getDetailList();
    }


    public MetaConstPO getMetaConst() {
        return metaConst;
    }

    public void setMetaConst(MetaConstPO metaConst) {
        this.metaConst = metaConst;
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName = constName;
    }

    public String getConstNameUpper() {
        return constNameUpper;
    }

    public void setConstNameUpper(String constNameUpper) {
        this.constNameUpper = constNameUpper;
    }

    public String getConstTypeStr() {
        return constTypeStr;
    }

    public void setConstTypeStr(String constTypeStr) {
        this.constTypeStr = constTypeStr;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getConstType() {
        return constType;
    }

    public void setConstType(Integer constType) {
        this.constType = constType;
    }

    public List<MetaConstDetailPO> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<MetaConstDetailPO> detailList) {
        this.detailList = detailList;
    }
}
