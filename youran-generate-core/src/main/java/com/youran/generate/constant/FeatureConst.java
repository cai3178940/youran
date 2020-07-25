package com.youran.generate.constant;

import com.youran.common.validator.Check;

/**
 * 项目特性常量
 *
 * @author cbb
 * @date 2018/11/28
 */
public class FeatureConst {

    @Deprecated
    public static class Boot {

        public static final int BOOT_1 = 1;
        public static final int BOOT_2 = 2;

        @Check
        public static final boolean check(int value) {
            return BOOT_1 == value || BOOT_2 == value;
        }

    }
}
