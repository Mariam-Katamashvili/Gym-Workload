package com.mariamkatamashvili.trainerwork.controller;

import com.mariamkatamashvili.trainerwork.dto.WorkDTO;
import com.mariamkatamashvili.trainerwork.service.WorkloadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trainers")
@RequiredArgsConstructor
@Tag(name = "Workload API", description = "Operations related to calculating and retrieving trainer working hours")
public class WorkloadController {
    private final WorkloadService workloadService;

    @Operation(summary = "Get all workloads")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of workloads retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WorkDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<WorkDTO>> getAll() {
        return ResponseEntity.ok(workloadService.findAll());
    }
}