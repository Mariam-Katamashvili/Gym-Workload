package com.mariamkatamashvili.trainerwork.controller;
import com.mariamkatamashvili.trainerwork.dto.ErrorDTO;
import com.mariamkatamashvili.trainerwork.exception.WorkloadException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order()
public class RestExceptionHandler {
    @ExceptionHandler(WorkloadException.class)
    public ResponseEntity<ErrorDTO> handleCreationException(WorkloadException e) {
        ErrorDTO dto = new ErrorDTO();
        dto.setErrorCode(e.getErrorCode());
        dto.setErrorMessage(e.getMessage());
        HttpStatus status = codeToStatus(dto.getErrorCode());
        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).body(dto);
    }

    private HttpStatus codeToStatus(String errorCode) {
        try {
            int status = Integer.parseInt(errorCode);
            return HttpStatus.resolve(status) != null ?
                    HttpStatus.resolve(status) :
                    HttpStatus.INTERNAL_SERVER_ERROR;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}