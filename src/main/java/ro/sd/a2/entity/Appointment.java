package ro.sd.a2.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Builder
@Setter
@Getter
@ToString
@Entity
public class Appointment {
    @Id
    private String id;

    @Column
    private Date date;

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
