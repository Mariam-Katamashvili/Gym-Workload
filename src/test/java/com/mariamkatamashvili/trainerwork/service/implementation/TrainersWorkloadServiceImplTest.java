package com.mariamkatamashvili.trainerwork.service.implementation;

import com.mariamkatamashvili.trainerwork.dto.ActionType;
import com.mariamkatamashvili.trainerwork.dto.WorkDTO;
import com.mariamkatamashvili.trainerwork.dto.WorkloadDTO;
import com.mariamkatamashvili.trainerwork.entity.Months;
import com.mariamkatamashvili.trainerwork.entity.TrainersWork;
import com.mariamkatamashvili.trainerwork.entity.Years;
import com.mariamkatamashvili.trainerwork.mapper.Mapper;
import com.mariamkatamashvili.trainerwork.repository.TrainersWorkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TrainersWorkServiceImplTest {

    @Mock
    private TrainersWorkRepository trainersWorkRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private TrainersWorkServiceImpl trainersWorkServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addWorkload_NewTrainer() {
        //given
        WorkloadDTO workloadDTO = new WorkloadDTO("john_doe", "John", "Doe", true, LocalDate.of(2023, 5, 20), 5, ActionType.ADD);
        when(trainersWorkRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(trainersWorkRepository.save(any(TrainersWork.class))).thenAnswer(i -> i.getArguments()[0]);

        //when
        trainersWorkServiceImpl.addWorkload(workloadDTO);

        //then
        verify(trainersWorkRepository, times(1)).findByUsername("john_doe");
        verify(trainersWorkRepository, times(1)).save(any(TrainersWork.class));
    }

    @Test
    void addWorkload_ExistingTrainer() {
        //given
        WorkloadDTO workloadDTO = new WorkloadDTO("john_doe", "John", "Doe", true, LocalDate.of(2023, 5, 20), 5, ActionType.ADD);
        TrainersWork trainer = new TrainersWork();
        trainer.setUsername("john_doe");
        Years year = new Years();
        year.setYear(2023);
        Months month = new Months();
        month.setMonth(Month.MAY);
        month.setWorkAmount(10);
        year.setMonths(Set.of(month));
        trainer.setYears(Set.of(year));
        when(trainersWorkRepository.findByUsername(anyString())).thenReturn(Optional.of(trainer));
        when(trainersWorkRepository.save(any(TrainersWork.class))).thenAnswer(i -> i.getArguments()[0]);

        //when
        trainersWorkServiceImpl.addWorkload(workloadDTO);

        //then
        verify(trainersWorkRepository, times(1)).findByUsername("john_doe");
        verify(trainersWorkRepository, times(1)).save(any(TrainersWork.class));
        assertEquals(15, month.getWorkAmount());
    }

    @Test
    void addWorkload_ExistingTrainer_DeleteWorkload() {
        //given
        WorkloadDTO workloadDTO = new WorkloadDTO("john_doe", "John", "Doe", true, LocalDate.of(2023, 5, 20), 5, ActionType.DELETE);
        TrainersWork trainer = new TrainersWork();
        trainer.setUsername("john_doe");
        Years year = new Years();
        year.setYear(2023);
        Months month = new Months();
        month.setMonth(Month.MAY);
        month.setWorkAmount(10);
        year.setMonths(Set.of(month));
        trainer.setYears(Set.of(year));
        when(trainersWorkRepository.findByUsername(anyString())).thenReturn(Optional.of(trainer));
        when(trainersWorkRepository.save(any(TrainersWork.class))).thenAnswer(i -> i.getArguments()[0]);

        //when
        trainersWorkServiceImpl.addWorkload(workloadDTO);

        //then
        verify(trainersWorkRepository, times(1)).findByUsername("john_doe");
        verify(trainersWorkRepository, times(1)).save(any(TrainersWork.class));
        assertEquals(5, month.getWorkAmount());
    }

    @Test
    void addWorkload_ExistingTrainer_CreateNewYear() {
        //given
        WorkloadDTO workloadDTO = new WorkloadDTO("john_doe", "John", "Doe", true, LocalDate.of(2024, 1, 15), 10, ActionType.ADD);
        TrainersWork trainer = new TrainersWork();
        trainer.setUsername("john_doe");
        Years existingYear = new Years();
        existingYear.setYear(2023);
        trainer.setYears(new HashSet<>(Set.of(existingYear)));

        when(trainersWorkRepository.findByUsername(anyString())).thenReturn(Optional.of(trainer));
        when(trainersWorkRepository.save(any(TrainersWork.class))).thenAnswer(i -> i.getArguments()[0]);

        //when
        trainersWorkServiceImpl.addWorkload(workloadDTO);

        //then
        verify(trainersWorkRepository, times(1)).findByUsername("john_doe");
        verify(trainersWorkRepository, times(1)).save(any(TrainersWork.class));
        assertEquals(2, trainer.getYears().size());
        Years newYear = trainer.getYears().stream().filter(ys -> ys.getYear() == 2024).findFirst().orElse(null);
        assertEquals(10, newYear.getMonths().stream().filter(ms -> ms.getMonth() == Month.JANUARY).findFirst().orElse(new Months()).getWorkAmount());
    }

    @Test
    void findAll() {
        //given
        TrainersWork trainer = new TrainersWork();
        WorkDTO workDTO = new WorkDTO("john_doe", "John", "Doe", true, Collections.emptySet());
        when(trainersWorkRepository.findAll()).thenReturn(Collections.singletonList(trainer));
        when(mapper.workEntityToDto(trainer)).thenReturn(workDTO);

        //when
        List<WorkDTO> result = trainersWorkServiceImpl.findAll();

        //then
        verify(trainersWorkRepository, times(1)).findAll();
        verify(mapper, times(1)).workEntityToDto(trainer);
        assertEquals(1, result.size());
        assertEquals(workDTO, result.get(0));
    }
}