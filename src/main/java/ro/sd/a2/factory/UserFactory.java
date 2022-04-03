package ro.sd.a2.factory;

import ro.sd.a2.dto.UserLoginDTO;
import ro.sd.a2.entity.UserRole;

import java.util.UUID;

public class UserFactory {

    public UserLoginDTO createUser(UserRole userRole,UserLoginDTO user){
        user.setId(UUID.randomUUID().toString());
        user.setUserRole(userRole);
        return user;
    }
}
