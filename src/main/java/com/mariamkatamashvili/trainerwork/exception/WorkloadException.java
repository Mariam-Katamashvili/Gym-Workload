package com.mariamkatamashvili.trainerwork.exception;

import lombok.Data;

@Data
public class WorkloadException extends RuntimeException {
    private final String errorCode;

    public WorkloadException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}