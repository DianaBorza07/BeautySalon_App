package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sd.a2.entity.BeautySalon;
import ro.sd.a2.entity.Schedule;

import java.time.LocalDateTime;

public interface ScheduleRepository extends JpaRepository<Schedule,String> {
     Schedule findByDayHour(LocalDateTime date);
     Schedule findByDayHourAndBeautySalon(LocalDateTime date, BeautySalon beautySalon);
}
