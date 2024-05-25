package com.mariamkatamashvili.trainerwork.service;

import com.mariamkatamashvili.trainerwork.dto.WorkDTO;
import com.mariamkatamashvili.trainerwork.dto.WorkloadDTO;

import java.util.List;

public interface TrainersWorkService {
    void addWorkload(WorkloadDTO workload);

    List<WorkDTO> findAll();
}