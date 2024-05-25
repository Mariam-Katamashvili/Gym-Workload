package com.mariamkatamashvili.trainerwork.service.implementation;

import com.mariamkatamashvili.trainerwork.dto.ActionType;
import com.mariamkatamashvili.trainerwork.dto.WorkDTO;
import com.mariamkatamashvili.trainerwork.dto.WorkloadDTO;
import com.mariamkatamashvili.trainerwork.entity.Months;
import com.mariamkatamashvili.trainerwork.entity.TrainersWork;
import com.mariamkatamashvili.trainerwork.entity.Years;
import com.mariamkatamashvili.trainerwork.mapper.Mapper;
import com.mariamkatamashvili.trainerwork.repository.TrainersWorkRepository;
import com.mariamkatamashvili.trainerwork.service.TrainersWorkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.YearMonth;
import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrainersWorkServiceImpl implements TrainersWorkService {
    private final TrainersWorkRepository trainersWorkRepository;
    private final Mapper mapper;

    @Override
    public void addWorkload(WorkloadDTO workload) {
        TrainersWork trainer = trainersWorkRepository.findByUsername(workload.getUsername())
                .orElseGet(() -> createNewTrainer(workload));

        YearMonth yearMonth = YearMonth.from(workload.getDate());
        Years yearlySummary = trainer.getYears().stream()
                .filter(ys -> ys.getYear() == yearMonth.getYear())
                .findFirst()
                .orElseGet(() -> createNewYear(trainer, yearMonth.getYear()));

        Months monthlySummary = yearlySummary.getMonths().stream()
                .filter(ms -> ms.getMonth().equals(yearMonth.getMonth()))
                .findFirst()
                .orElseGet(() -> createNewMonth(yearlySummary, yearMonth.getMonth()));

        updateMonthlyWorkAmount(monthlySummary, workload);

        trainersWorkRepository.save(trainer);
    }

    @Override
    public List<WorkDTO> findAll() {
        return trainersWorkRepository.findAll().stream()
                .map(mapper::workEntityToDto)
                .toList();
    }

    private TrainersWork createNewTrainer(WorkloadDTO workload) {
        TrainersWork trainer = new TrainersWork();
        trainer.setUsername(workload.getUsername());
        trainer.setFirstName(workload.getFirstName());
        trainer.setLastName(workload.getLastName());
        trainer.setStatus(workload.getIsActive());
        trainer.setYears(new HashSet<>());
        return trainer;
    }

    private Years createNewYear(TrainersWork trainer, int year) {
        Years newYear = new Years();
        newYear.setYear(year);
        newYear.setTrainersWork(trainer);
        newYear.setMonths(new HashSet<>());
        trainer.getYears().add(newYear);
        return newYear;
    }

    private Months createNewMonth(Years yearlySummary, Month month) {
        Months newMonth = new Months();
        newMonth.setMonth(month);
        newMonth.setYears(yearlySummary);
        yearlySummary.getMonths().add(newMonth);
        return newMonth;
    }

    private void updateMonthlyWorkAmount(Months monthlySummary, WorkloadDTO workload) {
        int updatedWorkAmount = monthlySummary.getWorkAmount();
        if (workload.getActionType().equals(ActionType.ADD)) {
            updatedWorkAmount += workload.getDuration();
        } else if (workload.getActionType().equals(ActionType.DELETE)) {
            updatedWorkAmount = Math.max(updatedWorkAmount - workload.getDuration(), 0);
        }
        monthlySummary.setWorkAmount(updatedWorkAmount);
    }
}