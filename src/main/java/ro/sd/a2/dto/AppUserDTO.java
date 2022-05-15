package ro.sd.a2.dto;

import lombok.*;
import org.springframework.stereotype.Component;
import ro.sd.a2.entity.UserRole;

import java.io.Serializable;

@Builder
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Component
public class AppUserDTO implements Serializable {

    private String id;

    private String username;

    private String name;

    private String email;

    private UserRole userRole;
}
