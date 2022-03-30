package ro.sd.a2.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Schedule {
    @Id
    private String id;

    @Column
    private Date dayAndHour;

    @Column
    private Boolean available;

    @ManyToOne
    @JoinColumn(name= "beauty_salon_id")
    private BeautySalon beautySalon;
}
