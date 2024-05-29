package com.mariamkatamashvili.trainerwork.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YearsDTO {
    private int year;
    private Set<MonthsDTO> months;
}