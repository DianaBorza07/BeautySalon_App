package ro.sd.a2.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Builder
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Component
public class PayloadDTO implements Serializable {
    private String id;

    private String username;

    private String name;

    private String email;

    private String date;

    private String beautySalon;

    private String salonService;

    private float price;
}
