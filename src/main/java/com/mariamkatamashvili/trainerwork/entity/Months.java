package com.mariamkatamashvili.trainerwork.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Month;
import java.util.Objects;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Months {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "work_month")
    private Month month;
    private int workAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "years_id")
    @JsonBackReference
    private Years years;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Months months = (Months) o;
        return Objects.equals(id, months.id) &&
                month == months.month;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, month);
    }
}