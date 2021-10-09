package com.len.core.exception;

/**
 * @author zhuxiaomeng
 * @date 2018/8/19.
 * @email lenospmiller@gmail.com
 * 未授权异常
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String msg) {
        super(msg);
    }
}
