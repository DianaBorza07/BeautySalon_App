package ro.sd.a2.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Builder
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Component
public class AppUserDTO {

    private String id;

    private String username;

    private String name;

    private String email;
}
