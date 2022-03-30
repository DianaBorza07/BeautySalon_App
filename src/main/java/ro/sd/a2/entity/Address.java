package ro.sd.a2.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Builder
@Setter
@Getter
@ToString
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
