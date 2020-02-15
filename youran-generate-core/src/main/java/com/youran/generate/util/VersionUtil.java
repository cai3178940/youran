package com.youran.generate.util;

import com.youran.common.util.SafeUtil;
import com.youran.generate.constant.PatternConst;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * 版本号工具类
 *
 * @author cbb
 * @date 2020/01/18
 */
public class VersionUtil {

    /**
     * 解析版本号字符串
     *
     * @param version
     * @return
     */
    public static int[] parseVersion(String version) {
        return Arrays.stream(version.split("\\."))
            .filter(s -> Pattern.matches(PatternConst.NUM, s))
            .mapToInt(SafeUtil::getInteger)
            .toArray();
    }

    /**
     * 比较两个版本号
     *
     * @param a
     * @param b
     * @return
     */
    public static int compareVersion(int[] a, int[] b) {
        for (int i = 0; i < a.length; i++) {
            int ai = a[i];
            int bi = b[i];
            if (ai > bi) {
                return 1;
            } else if (ai < bi) {
                return -1;
            }
        }
        return 0;
    }


}
