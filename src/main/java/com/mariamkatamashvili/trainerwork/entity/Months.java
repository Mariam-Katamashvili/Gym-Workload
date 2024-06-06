package com.mariamkatamashvili.trainerwork.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Month;

@Data
@Document(collection = "months")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Months {
    @Id
    @EqualsAndHashCode.Include
    private String id;

    @EqualsAndHashCode.Include
    private Month month;

    private int workAmount;
}