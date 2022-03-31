package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sd.a2.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule,String> {
}
