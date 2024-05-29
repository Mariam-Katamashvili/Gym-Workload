package com.mariamkatamashvili.trainerwork.mapper;

import com.mariamkatamashvili.trainerwork.dto.WorkDTO;
import com.mariamkatamashvili.trainerwork.entity.TrainersWork;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    Mapper INSTANCE = Mappers.getMapper(Mapper.class);

    WorkDTO workEntityToDto(TrainersWork trainersWork);
}