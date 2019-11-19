package com.youran.common.xss;

import java.lang.annotation.*;

/**
 * 无视XSS脚本
 *
 * @author: cbb
 * @date: 2019/11/19
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface IgnoreXSS {
}
