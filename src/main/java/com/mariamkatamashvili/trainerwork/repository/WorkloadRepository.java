package com.mariamkatamashvili.trainerwork.repository;

import com.mariamkatamashvili.trainerwork.entity.TrainersWork;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkloadRepository extends MongoRepository<TrainersWork, String> {
    Optional<TrainersWork> findByUsername(String username);
}