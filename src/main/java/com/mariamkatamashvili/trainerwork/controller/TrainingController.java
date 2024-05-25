package com.mariamkatamashvili.trainerwork.controller;

import com.mariamkatamashvili.trainerwork.dto.WorkDTO;
import com.mariamkatamashvili.trainerwork.dto.WorkloadDTO;
import com.mariamkatamashvili.trainerwork.service.TrainersWorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trainings")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainersWorkService trainersWorkService;

    @PostMapping("/workload")
    public ResponseEntity<Void> workload(
            @RequestBody WorkloadDTO workload
    ) {
        trainersWorkService.addWorkload(workload);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<WorkDTO>> getAll() {
        return ResponseEntity.ok(trainersWorkService.findAll());
    }
}