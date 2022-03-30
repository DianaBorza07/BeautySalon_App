package ro.sd.a2.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Builder
@Setter
@Getter
@ToString
@Entity
public class UserRole {

    @Id
    private String id;

    @Column
    private String roleName;

    @OneToMany(mappedBy = "userRole",cascade = CascadeType.ALL)
    private List<AppUser> userList;


}
