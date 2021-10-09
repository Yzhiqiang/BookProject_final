package com.len.exception;

/**
 * @author zhuxiaomeng
 * @date 2017/12/15.
 * @email lenospmiller@gmail.com
 */
public class LenException extends RuntimeException {

  private String message;

  public LenException(String message){
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
