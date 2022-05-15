package ro.sd.a2.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SalonServiceDTO {
    @Id
    private String id;

    @Column
    private String name;

    @Column
    private float price;
}
