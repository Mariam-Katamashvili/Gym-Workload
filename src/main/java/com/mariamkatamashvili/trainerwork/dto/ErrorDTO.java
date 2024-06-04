package com.mariamkatamashvili.trainerwork.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ErrorDTO {
    @NonNull
    private String errorMessage;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> details = new HashMap<>();
    @NonNull
    private String errorCode;
}