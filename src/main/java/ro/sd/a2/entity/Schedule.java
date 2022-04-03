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
public class Schedule {
    @Id
    private String id;

    @Column
    private LocalDateTime dayHour;

    @Column
    private Boolean available;

    @ManyToOne
    @JoinColumn(name= "beauty_salon_id")
    private BeautySalon beautySalon;
}
