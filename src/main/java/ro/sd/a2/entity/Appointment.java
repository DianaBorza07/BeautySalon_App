package ro.sd.a2.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Appointment {
    @Id
    private String id;

    @Column
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "beauty_salon_id")
    private BeautySalon beautySalon;

    @ManyToOne
    @JoinColumn(name =" user_id")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

}