package com.mariamkatamashvili.trainerwork.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "trainer_workload")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TrainersWork {
    @Id
    @EqualsAndHashCode.Include
    private String id;

    @Indexed
    @EqualsAndHashCode.Include
    private String username;

    @Indexed
    private String firstName;

    @Indexed
    private String lastName;
    private Boolean status;

    private List<Years> years;
}