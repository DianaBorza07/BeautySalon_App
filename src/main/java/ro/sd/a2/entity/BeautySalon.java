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
public class BeautySalon {
    @Id
    private String id;

    @Column
    private String name;

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "beautySalon",cascade = CascadeType.ALL)
    private List<Schedule> scheduleList;

    @OneToMany(mappedBy = "beautySalon",cascade= CascadeType.ALL)
    private List<Appointment> appointments;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "beautySalon_Service",
                joinColumns = @JoinColumn(name ="id_b"),
                inverseJoinColumns = @JoinColumn(name="id_s"))
    private List<Service> services;

}
