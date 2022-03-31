package ro.sd.a2.dto;

import lombok.*;

@Builder
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDTO {

    private String id;

    private String username;

    private String name;

    private String email;
}
