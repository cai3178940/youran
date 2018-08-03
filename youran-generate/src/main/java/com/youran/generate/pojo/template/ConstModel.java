package com.youran.generate.pojo.template;

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
     * 常量对象
     */
    private String className;
    /**
     * 常量对象
     */
    private String classNameUpper;
    /**
     * 常量对象
     */
    private String remark;
    /**
     * 常量对象
     */
    private Integer constType;
    /**
     * 常量对象
     */
    private List<MetaConstDetailPO> detailList;

    public ConstModel(MetaProjectPO project,MetaConstPO metaConst) {
        super(project);
        this.metaConst = metaConst;
        this.className = StringUtils.uncapitalize(metaConst.getConstName());
        this.classNameUpper = StringUtils.capitalize(metaConst.getConstName());
        this.remark = metaConst.getConstRemark();
        this.constType = metaConst.getConstType();
        this.detailList = metaConst.getDetailList();
    }


    public MetaConstPO getMetaConst() {
        return metaConst;
    }

    public void setMetaConst(MetaConstPO metaConst) {
        this.metaConst = metaConst;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassNameUpper() {
        return classNameUpper;
    }

    public void setClassNameUpper(String classNameUpper) {
        this.classNameUpper = classNameUpper;
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
