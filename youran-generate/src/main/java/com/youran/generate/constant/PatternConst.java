package com.youran.generate.constant;

/**
 * <p>Title: DTO校验正则</p>
 * <p>Description: </p>
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

    public static final String PACKAGE_NAME = "^([a-z]+[.][a-z]+)[.]*.*";
    public static final String PACKAGE_NAME_MSG = "不符合包名规范";

    public static final String FIELD_IDS = "^(([0-9]+,)*[0-9]+)$";
    public static final String FIELD_IDS_MSG = "不符合多id格式";


}
