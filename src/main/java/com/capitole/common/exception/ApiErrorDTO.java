/* (C) Copyright 2024 Frubana. */
package com.capitole.price.common.exception;

public record ApiErrorDTO(String message, Integer code) {

    public static ApiErrorDTO apply(ErrorCode errorCode) {
        return new ApiErrorDTO(errorCode.name, errorCode.code);
    }
}
