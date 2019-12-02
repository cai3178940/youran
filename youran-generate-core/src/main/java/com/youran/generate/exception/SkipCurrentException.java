package com.youran.generate.exception;

/**
 * 跳过当前文件异常
 *
 * @author: cbb
 * @date: 2019/10/1
 */
public class SkipCurrentException extends RuntimeException {

    public SkipCurrentException() {
        super("跳过当前文件");
    }

}
