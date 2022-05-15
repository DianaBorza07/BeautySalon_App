package ro.sd.a2.mapper;

import ro.sd.a2.dto.AppUserDTO;
import ro.sd.a2.dto.UserLoginDTO;
import ro.sd.a2.entity.AppUser;

public class UserMapper {

    public static AppUser dtoLoginUserToAppUser(UserLoginDTO user){
        return  AppUser.builder().id(user.getId()).
                username(user.getUsername()).userRole(user.getUserRole()).
                password(user.getPassword()).name(user.getName()).email(user.getEmail()).build();
    }

    public static UserLoginDTO appUserToDtoLoginUser(AppUser appUser){
        return  UserLoginDTO.builder().userRole(appUser.getUserRole()).
                username(appUser.getUsername()).email(appUser.getEmail()).
                password(appUser.getPassword()).id(appUser.getId()).name(appUser.getName()).build();
    }

    public static AppUser dtoUserToAppUser(AppUserDTO user){
        return  AppUser.builder().email(user.getEmail()).id(user.getId()).
                username(user.getUsername()).name(user.getName()).userRole(user.getUserRole()).build();
    }

    public static AppUserDTO appUserToDtoUser(AppUser user){
        return  AppUserDTO.builder().email(user.getEmail()).username(user.getUsername()).
                id(user.getId()).name(user.getName()).userRole(user.getUserRole()).build();
    }
}
