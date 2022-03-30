package ro.sd.a2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.entity.AppUser;
import ro.sd.a2.entity.UserRole;
import ro.sd.a2.service.UserRoleService;
import ro.sd.a2.service.UserService;

import java.util.UUID;


@Controller
public class FirstController {

    private static final Logger log = LoggerFactory.getLogger(FirstController.class);

    @Autowired
    private UserService userService ;//= new UserService();

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("/profile")
    public ModelAndView showProfile() {
        //validation if needed
        //shall we log a little?
        ModelAndView mav = new ModelAndView();

        log.info("Logged");

        AppUser appUser =AppUser.builder().name("Bubu").build();
        mav.addObject("fullUserObj", appUser);
        mav.addObject("numeStudent", appUser.getName());
        // adaugi x obiecte
        mav.setViewName("profile");
        //log the final outcome: Success y?
        return mav;
    }

    @GetMapping("/home")
    public ModelAndView showHome() {
        //validation if needed
        //shall we log a little?
        ModelAndView mav = new ModelAndView();

        //log.info("Logged");

        //mav.addObject("grupa", 5);
        // adaugi x obiecte
        mav.setViewName("home");
        //log the final outcome: Success y?
        return mav;
    }

    @GetMapping("/signin")
    public ModelAndView showSignIn() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("signin");
        return mav;
    }

    @PostMapping("/signin")
    public ModelAndView signIn(@RequestParam String username, @RequestParam String password){
        AppUser user = userService.getUserByUsernameAndPassword(username,password);
        ModelAndView mav = new ModelAndView();
        String errorMessage = null;
        if(user!=null)
            mav.setViewName("/home");
        else {
            log.error("User not found!");
            errorMessage = "Incorect credentials";
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
    public ModelAndView signUp(@RequestParam String username, @RequestParam String password,@RequestParam String name){
        UserRole userRole = userRoleService.getUserRoleByName("Customer");
        AppUser appUser = AppUser.builder().id(UUID.randomUUID().toString()).username(username).password(password).name(name).userRole(userRole).build();
        AppUser newUser = userService.saveUser(appUser);
        ModelAndView mav = new ModelAndView();
        if(newUser!=null)
            mav.setViewName("/home");
        else{

            mav.setViewName("/signup");
        }

        return mav;
    }

}
