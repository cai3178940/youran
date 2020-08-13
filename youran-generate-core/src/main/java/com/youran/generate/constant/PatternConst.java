package com.youran.generate.constant;

/**
 * DTO校验正则
 *
 * @author: cbb
 * @date: 2017/5/12
 */
public class PatternConst {

    public static final String ALPHANUM_CH = "^([\u4E00-\u9FA5a-zA-Z0-9]+)$";
    public static final String ALPHANUM_CH_MSG = "只允许输入字母数字和中文";

    public static final String ALPHANUM = "^([a-zA-Z0-9]+)$";
    public static final String ALPHANUM_MSG = "只允许输入字母和数字";

    public static final String ALPHA = "^([a-zA-Z]+)$";
    public static final String ALPHA_MSG = "只允许输入字母";

    public static final String NUM = "^([0-9]+)$";
    public static final String NUM_MSG = "只允许输入数字";

    public static final String CLASS_NAME = "^[A-Z](\\w*(_|\\$)*\\w*)*$";
    public static final String CLASS_NAME_MSG = "不符合类名规范";

    public static final String CONST_NAME = "^[A-Z]([A-Z|0-9]*_?)+[A-Z|0-9]+$";
    public static final String CONST_NAME_MSG = "不符合java常量命名规范";

    public static final String FIELD_NAME = "^(\\w*(_|\\$)*\\w*)*$";
    public static final String FIELD_NAME_MSG = "不符合数据库命名规范";

    public static final String J_FIELD_NAME = "^[a-z](\\w*(_|\\$)*\\w*)*$";
    public static final String J_FIELD_NAME_MSG = "不符合java字段命名规范";

    public static final String PACKAGE_NAME = "^[a-zA-Z]+[0-9a-zA-Z_]*(\\.[a-zA-Z]+[0-9a-zA-Z_]*)*$";
    public static final String PACKAGE_NAME_MSG = "不符合包名规范";

    public static final String FIELD_IDS = "^(([0-9]+,)*[0-9]+)$";
    public static final String FIELD_IDS_MSG = "不符合多id格式";

    public static final String VERSION = "^([1-9]\\d|[0-9])(\\.([1-9]\\d|\\d)){2}$";
    public static final String VERSION_MSG = "不符合版本号格式";

    public static final String MODULE = "^(\\w|_|-)*$";
    public static final String MODULE_MSG = "不符合模块命名格式";

}
