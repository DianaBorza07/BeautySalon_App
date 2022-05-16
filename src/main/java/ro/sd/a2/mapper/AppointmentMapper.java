package ro.sd.a2.mapper;

import ro.sd.a2.dto.AppointmentDTO;
import ro.sd.a2.entity.Appointment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentMapper {

    public static Appointment dtoToAppointment(AppointmentDTO appointmentDTO) {
        String str = appointmentDTO.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        return Appointment.builder().id(appointmentDTO.getId()).user(appointmentDTO.getUser()).date(dateTime).build();
    }

    public static AppointmentDTO appointmentToDto(Appointment appointment) {
        return AppointmentDTO.builder().id(appointment.getId()).beautySalon(appointment.getBeautySalon().getName()).salonService(appointment.getSalonService().getName()).user(appointment.getUser()).date(appointment.getDate().toString()).price(appointment.getSalonService().getPrice()).build();
    }
}
