/* (C) Copyright 2024 Capitole. */
package com.capitole.price.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    GENERIC_ERROR(500, "Generic error", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND(404, "Not found", HttpStatus.NOT_FOUND),
    PRICE_NOT_FOUND(101, "Price not found", HttpStatus.NOT_FOUND),
    PRICE_ALREADY_EXISTS(102, "Price already exists", HttpStatus.BAD_REQUEST);

    public final Integer code;
    public final String name;
    public final HttpStatus httpStatus;

    ErrorCode(Integer code, String name, HttpStatus httpStatus) {
        this.code = code;
        this.name = name;
        this.httpStatus = httpStatus;
    }
    
}
