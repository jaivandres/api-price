/* (C) Copyright 2024 Frubana. */
package com.capitole.price.common.exception;


public class BusinessException extends RuntimeException {

  private final ErrorCode errorCode;

  public BusinessException(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }

}
