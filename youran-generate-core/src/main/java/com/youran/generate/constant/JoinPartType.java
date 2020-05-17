package com.youran.generate.constant;

import com.youran.common.validator.Check;

/**
 * 枚举【关联部分的类型】
 *
 * @author cbb
 * @date 2020/04/04
 */
public class JoinPartType {

    public static final String ENTITY = "entity";
    public static final String MTM = "mtm";

    /**
     * 枚举值罗列，给swagger接口文档展示用
     */
    public static final String VALUES_STR = "entity,mtm";

    /**
     * 校验有效性
     */
    @Check
    public static final boolean validate(String value){
        return ENTITY.equals(value) || MTM.equals(value);
    }


}

