package ro.sd.a2.dto;

import lombok.*;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private String id;

    private String street;

    private String number;

}
