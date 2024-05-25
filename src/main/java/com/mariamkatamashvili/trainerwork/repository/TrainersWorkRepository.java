package com.mariamkatamashvili.trainerwork.repository;

import com.mariamkatamashvili.trainerwork.entity.TrainersWork;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainersWorkRepository extends JpaRepository<TrainersWork, Long> {
    @EntityGraph(attributePaths = {"years", "years.months"})
    Optional<TrainersWork> findByUsername(String username);
}