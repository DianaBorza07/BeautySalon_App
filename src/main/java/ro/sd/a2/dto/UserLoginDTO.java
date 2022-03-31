package ro.sd.a2.dto;

import lombok.*;
import ro.sd.a2.entity.AppUser;
import ro.sd.a2.entity.UserRole;

@Builder
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {

    private String id;

    private String username;

    private String password;

    private String name;

    private UserRole userRole;

    private String email;

    public AppUser dtoToUser(){
        AppUser appUser = new AppUser();
        appUser.setId(id);
        appUser.setUsername(username);
        appUser.setUserRole(userRole);
        appUser.setPassword(password);
        appUser.setName(name);
        appUser.setEmail(email);
        return appUser;
    }
}
