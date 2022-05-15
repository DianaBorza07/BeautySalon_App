package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.dto.AppUserDTO;
import ro.sd.a2.dto.UserLoginDTO;
import ro.sd.a2.entity.AppUser;
import ro.sd.a2.mapper.UserMapper;
import ro.sd.a2.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService   {

    @Autowired
    private UserRepository userRepository;

    public List<AppUserDTO> getAllUsers() {
        List<AppUser> appUsers = userRepository.findAll();
        List<AppUserDTO> appUserDTOS = new ArrayList<>();
        appUsers.stream().forEach(a->appUserDTOS.add(UserMapper.appUserToDtoUser(a)));
        return appUserDTOS;
    }

    public AppUserDTO getUserByEmailAndPassword(String email, String password){
        AppUser  appUser = userRepository.findByEmailAndPassword(email,password);
        return UserMapper.appUserToDtoUser(appUser);
    }

    public AppUser saveUser(UserLoginDTO user){
        AppUser appUser = UserMapper.dtoLoginUserToAppUser(user);
        if(userRepository.findByEmailAndUsername(user.getEmail(),user.getUsername())==null)
            return null;
        return userRepository.save(appUser);
    }

    public boolean updateUser(AppUserDTO appUser){
        Optional<AppUser> user = userRepository.findById(appUser.getId());
        if(appUser.getUsername()!=null)
            user.get().setUsername(appUser.getUsername());
        if(appUser.getEmail()!=null)
            user.get().setEmail(appUser.getEmail());
        if(!user.isPresent())
            return  false;
        try {
            userRepository.save(user.get());
        }catch (NoSuchElementException e){return false;}

        return  true;
    }

    public boolean deleteUser(AppUserDTO appUserDTO){
        Optional<AppUser> user = userRepository.findById(appUserDTO.getId());
        if(!user.isPresent())
            return false;
        try {
            userRepository.delete(user.get());
        }catch (NoSuchElementException e){
            return false;
        }
        return true;
    }


}
