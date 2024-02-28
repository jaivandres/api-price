/* (C) Copyright 2024 Capitole. */
package com.capitole.price.exception;

public record ApiErrorDTO(String message, Integer code) {

    public static ApiErrorDTO apply(ErrorCode errorCode) {
        return new ApiErrorDTO(errorCode.name, errorCode.code);
    }
}
