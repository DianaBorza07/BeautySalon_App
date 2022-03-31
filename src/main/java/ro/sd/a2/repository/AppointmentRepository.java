package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sd.a2.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment,String> {
}
