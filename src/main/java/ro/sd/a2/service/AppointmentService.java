package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.dto.AppUserDTO;
import ro.sd.a2.entity.AppUser;
import ro.sd.a2.entity.Appointment;
import ro.sd.a2.repository.AppointmentRepository;
import ro.sd.a2.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

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

    public List<Appointment> getAllAppointmentsOfAnUser(AppUserDTO appUserDTO){
        AppUser user = userRepository.findByEmailAndUsername(appUserDTO.getEmail(),appUserDTO.getUsername());
        return appointmentRepository.findByUser(user);
    }
}
