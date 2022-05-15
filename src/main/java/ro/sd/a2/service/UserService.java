package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.dto.AppUserDTO;
import ro.sd.a2.dto.UserLoginDTO;
import ro.sd.a2.entity.AppUser;
import ro.sd.a2.entity.UserRole;
import ro.sd.a2.mapper.UserMapper;
import ro.sd.a2.repository.UserRepository;
import ro.sd.a2.repository.UserRoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService   {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

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

    public boolean changeRole(String email){
        AppUser user = userRepository.findByEmail(email);
        System.out.println(email+"---------");
        if(user==null)
            return false;
        else {
            UserRole userRole = user.getUserRole();
            System.out.println(userRole.getRoleName()+"------------------------------------------");

            if(userRole.getRoleName().compareTo("Customer") == 0) {
                System.out.println("Customer");
                user.setUserRole(userRoleRepository.findByRoleName("Provider"));
            }
            else if(userRole.getRoleName().compareTo("Provider") == 0) {
                System.out.println("Provider");
                user.setUserRole(userRoleRepository.findByRoleName("Customer"));
            }
            userRepository.save(user);
        }
        return true;
    }


}
