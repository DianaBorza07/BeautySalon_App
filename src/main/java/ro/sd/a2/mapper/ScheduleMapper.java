package ro.sd.a2.mapper;

import ro.sd.a2.dto.ScheduleDTO;
import ro.sd.a2.entity.Schedule;

public class ScheduleMapper {

    public static Schedule dtoToSchedule(ScheduleDTO scheduleDTO){
        return Schedule.builder().id(scheduleDTO.getId()).dayHour(scheduleDTO.getDayHour()).available(scheduleDTO.getAvailable()).build();
    }

    public static ScheduleDTO scheduleToDto(Schedule schedule){
        return ScheduleDTO.builder().id(schedule.getId()).dayHour(schedule.getDayHour()).available(schedule.getAvailable()).build();
    }
}
