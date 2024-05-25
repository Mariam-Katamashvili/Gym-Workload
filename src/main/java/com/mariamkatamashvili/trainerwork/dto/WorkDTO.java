package com.mariamkatamashvili.trainerwork.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
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