package com.mariamkatamashvili.trainerwork.controller;

import com.mariamkatamashvili.trainerwork.exception.AuthenticationException;
import com.mariamkatamashvili.trainerwork.exception.WorkloadException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(WorkloadException.class)
    public ResponseEntity<String> handleWorkloadException(WorkloadException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}