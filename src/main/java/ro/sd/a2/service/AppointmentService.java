package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.entity.Appointment;
import ro.sd.a2.repository.AppointmentRepository;

import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment saveAppointment(Appointment appointment){
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointments(){
        return appointmentRepository.findAll();
    }

    public boolean deleteAppointmentWithId(String id){
        Appointment appointment = appointmentRepository.getById(id);
        if(appointment == null)
            return false;
        appointmentRepository.delete(appointment);
        return true;
    }
}
