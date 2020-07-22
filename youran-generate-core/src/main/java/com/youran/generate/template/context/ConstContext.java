package com.youran.generate.template.context;

import com.youran.generate.constant.MetaConstType;
import com.youran.generate.pojo.po.MetaConstDetailPO;
import com.youran.generate.pojo.po.MetaConstPO;
import com.youran.generate.pojo.po.MetaProjectPO;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 常量信息上下文对象
 * <p> 包含某个常量的所有信息
 *
 * @author cbb
 * @date 2018/8/3
 */
public class ConstContext extends BaseContext {

    /**
     * 常量对象
     */
    private final MetaConstPO metaConst;
    /**
     * 常量名称-首字母小写
     */
    private final String constName;
    /**
     * 常量名称-首字母大写
     */
    private final String constNameUpper;
    /**
     * 常量描述
     */
    private final String remark;
    /**
     * 常量类型
     */
    private final Integer constType;
    /**
     * 常量类型-字符串
     */
    private final String constTypeStr;
    /**
     * 常量值列表
     */
    private final List<MetaConstDetailPO> detailList;

    public ConstContext(MetaProjectPO project, MetaConstPO metaConst) {
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

    public String getConstName() {
        return constName;
    }

    public String getConstNameUpper() {
        return constNameUpper;
    }

    public String getConstTypeStr() {
        return constTypeStr;
    }

    public String getRemark() {
        return remark;
    }

    public Integer getConstType() {
        return constType;
    }

    public List<MetaConstDetailPO> getDetailList() {
        return detailList;
    }

    @Override
    public String toString() {
        return "Const:" + constName;
    }
}
