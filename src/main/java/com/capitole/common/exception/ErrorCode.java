/* (C) Copyright 2024 Frubana. */
package com.capitole.price.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    GENERIC_ERROR(500, "Generic error", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND(404, "Not found", HttpStatus.NOT_FOUND),
    CUSTOMER_NOT_FOUND(101, "Customer not found", HttpStatus.NOT_FOUND),
    CUSTOMER_ALREADY_EXISTS(102, "Customer already exists", HttpStatus.BAD_REQUEST);

    public final Integer code;
    public final String name;
    public final HttpStatus httpStatus;

    ErrorCode(Integer code, String name, HttpStatus httpStatus) {
        this.code = code;
        this.name = name;
        this.httpStatus = httpStatus;
    }
    
}
