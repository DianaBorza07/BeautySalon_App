package ro.sd.a2.factory;

import ro.sd.a2.dto.ScheduleDTO;

import java.util.UUID;

public class ScheduleFactory {
    public ScheduleDTO createSchedule(ScheduleDTO schedule){
    schedule.setId(UUID.randomUUID().toString());
    schedule.setAvailable(true);
    return schedule;
    }
}
