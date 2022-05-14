package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.entity.UserRole;
import ro.sd.a2.repository.UserRoleRepository;

@Service
public class UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;

    public UserRole getUserRoleByName(String roleName){
        return userRoleRepository.findByRoleName(roleName);
    }

}
