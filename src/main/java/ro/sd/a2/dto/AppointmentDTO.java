package ro.sd.a2.dto;

import lombok.*;
import ro.sd.a2.entity.AppUser;
import ro.sd.a2.entity.BeautySalon;
import ro.sd.a2.entity.SalonService;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class AppointmentDTO {
    private String id;

    private String date;

    private String beautySalon;

    private AppUser user;

    private String salonService;

    private float price;
}
