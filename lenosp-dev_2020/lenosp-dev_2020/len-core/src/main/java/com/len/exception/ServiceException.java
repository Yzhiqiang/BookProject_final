package com.len.exception;

/**
 * @author zxm
 * @date 2021/3/21 13:56
 */


public class ServiceException extends RuntimeException {

    private String message;

    public ServiceException(String message) {
        super(message);
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
