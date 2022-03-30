package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.entity.AppUser;
import ro.sd.a2.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService   {

    @Autowired
    private UserRepository userRepository;

    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    public AppUser getUserByUsernameAndPassword(String username, String password){
        return userRepository.findByUsernameAndPassword(username,password);
    }

    public AppUser saveUser(AppUser appUser){
        return userRepository.save(appUser);
    }

}
