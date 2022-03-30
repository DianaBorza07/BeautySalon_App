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
public class Service {

    @Id
    private String id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "services")
    private List<BeautySalon> beautySalonList;

    @OneToMany(mappedBy ="service", cascade = CascadeType.ALL)
    private List<Appointment> appointments;
}
