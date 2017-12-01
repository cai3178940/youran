package com.youran.common.optimistic;

/**
 * 乐观锁异常
 * @author cbb
 * @date 2017/2/21 15:01
 */
public class OptimisticException extends RuntimeException{

    public OptimisticException() {

    }

    public OptimisticException(String message) {
        super(message);
    }
}
