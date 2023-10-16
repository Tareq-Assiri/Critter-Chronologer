package com.udacity.jdnd.course3.critter.schedule;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    ScheduleMapper MAPPER = Mappers.getMapper(ScheduleMapper.class);
    ScheduleDTO map(Schedule Schedule);
    Schedule map(ScheduleDTO ScheduleDTO);
    List<ScheduleDTO> map(List<Schedule> Schedules);
}
