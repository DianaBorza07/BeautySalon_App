package ro.sd.a2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.dto.AppUserDTO;
import ro.sd.a2.dto.UserLoginDTO;
import ro.sd.a2.entity.AppUser;
import ro.sd.a2.entity.UserRole;
import ro.sd.a2.factory.UserFactory;
import ro.sd.a2.service.UserRoleService;
import ro.sd.a2.service.UserService;

import java.util.List;


@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService ;

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("/profile")
    public ModelAndView showProfile() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("profile");
        return mav;
    }

    @GetMapping("/home")
    public ModelAndView showHome() {
        ModelAndView mav = new ModelAndView();
        List<AppUserDTO> users = userService.getAllUsers();
        mav.addObject("users",users);
        mav.setViewName("home");
        return mav;
    }

    @GetMapping("/signin")
    public ModelAndView showSignIn() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("signin");
        return mav;
    }

    @PostMapping("/signin")
    public ModelAndView signIn(@RequestParam String email, @RequestParam String password){
        AppUserDTO user = userService.getUserByEmailAndPassword(email,password);
        ModelAndView mav = new ModelAndView();
        String errorMessage = null;
        if(user!=null)
            mav.setViewName("/profile");
        else {
            log.error("User not found!");
            errorMessage = "Incorrect credentials";
            mav.setViewName("/signin");
        }
        mav.addObject("errorMessage",errorMessage);
        return mav;
    }

    @GetMapping("/signup")
    public ModelAndView showSignUp() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("signup");
        return mav;
    }

    @PostMapping("/signup")
    public ModelAndView signUp(@RequestParam String email,@RequestParam String username, @RequestParam String password,@RequestParam String name){
        UserRole userRole = userRoleService.getUserRoleByName("Customer");
        UserFactory userFactory = new UserFactory();
        UserLoginDTO appUser = UserLoginDTO.builder().username(username).password(password).name(name).email(email).build();
        appUser = userFactory.createUser(userRole,appUser);
        AppUser newUser = userService.saveUser(appUser);
        ModelAndView mav = new ModelAndView();
        if(newUser!=null)
            mav.setViewName("/profile");
        else{
            mav.setViewName("/signup");
        }
        return mav;
    }

    @PostMapping("/profile")
    public ModelAndView updateUser(@RequestParam String newEmail,@RequestParam String newUsername){
        ///////////// id hardcodat -> trebuie preluat id-ul userului logat
        AppUserDTO user = AppUserDTO.builder().id("8945449f-c3f8-4278-a4fd-48ae3e857345").username(newUsername).email(newEmail).name("Diana Borza").build();
        boolean var = userService.updateUser(user);
        ModelAndView mav = new ModelAndView();
        if(var) {
            log.info("User updated successfully!");
            mav.addObject("successMessage","User updated successfully!");
        }
        else {
            log.error("Error on updating the user!");
            mav.addObject("errorMessage","Error on updating the user!");
        }
        mav.setViewName("/profile");
        return  mav;
    }


}
