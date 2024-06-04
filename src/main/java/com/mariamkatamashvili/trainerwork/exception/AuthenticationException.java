package com.mariamkatamashvili.trainerwork.exception;

import lombok.Data;

@Data
public class AuthenticationException extends RuntimeException {
    private final String errorCode;

    public AuthenticationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}