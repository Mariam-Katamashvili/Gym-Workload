package com.mariamkatamashvili.trainerwork.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkDTO {
    private String username;
    private String firstName;
    private String lastName;
    private Boolean status;
    private Set<YearsDTO> years;
}