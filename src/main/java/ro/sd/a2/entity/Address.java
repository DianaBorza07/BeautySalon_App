package ro.sd.a2.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {
    @Id
    private String id;

    @Column
    private String street;

    @Column
    private String number;

    @OneToOne(mappedBy = "address")
    private BeautySalon beautySalon;
}
