package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sd.a2.entity.AppUser;
import ro.sd.a2.entity.Appointment;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,String> {
    List<Appointment> findByUser(AppUser user);
}
