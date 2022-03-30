package ro.sd.a2.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserRole {

    @Id
    private String id;

    @Column
    private String roleName;

    @OneToMany(mappedBy = "userRole",cascade = CascadeType.ALL)
    private List<AppUser> userList;


}
