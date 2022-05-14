package ro.sd.a2.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SalonService {

    @Id
    private String id;

    @Column
    private String name;

    @Column
    private float price;

    @ManyToMany(mappedBy = "services")
    private List<BeautySalon> beautySalonList;

    @OneToMany(mappedBy ="salonService", cascade = CascadeType.ALL)
    private List<Appointment> appointments;
}
