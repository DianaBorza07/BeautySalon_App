package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.dto.ScheduleDTO;
import ro.sd.a2.entity.Schedule;
import ro.sd.a2.mapper.ScheduleMapper;
import ro.sd.a2.repository.ScheduleRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    public Schedule saveSchedule(ScheduleDTO schedule) {
        Schedule schedule1 = ScheduleMapper.dtoToSchedule(schedule);
        return scheduleRepository.save(schedule1);
    }
    public List<ScheduleDTO> getAllSchedules(){
        List<Schedule> scheduleList = scheduleRepository.findAll();
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        scheduleList.stream().forEach(a->scheduleDTOList.add(ScheduleMapper.scheduleToDto(a)));
        return scheduleDTOList;
    }

    public boolean updateSchedule(LocalDateTime date,boolean available){
        Schedule schedule = scheduleRepository.findByDayHour(date);
        schedule.setAvailable(available);
        if(schedule==null)
            return false;
        Schedule schedule1 = scheduleRepository.save(schedule);
        if(schedule1==null)
            return false;
        return true;
    }

    public boolean deleteSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = ScheduleMapper.dtoToSchedule(scheduleDTO);
        Optional<Schedule> schedule1 = scheduleRepository.findById(schedule.getId());
        if(!schedule1.isPresent())
            return false;
        try {
            scheduleRepository.delete(schedule1.get());
        }
        catch (NoSuchElementException e){
            return false;
        }
        return true;
    }
}

