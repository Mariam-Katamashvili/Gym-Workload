package com.mariamkatamashvili.trainerwork.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Month;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthsDTO {
    private Month month;
    private int workAmount;
}