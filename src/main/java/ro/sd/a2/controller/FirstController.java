package ro.sd.a2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.entity.AppUser;
import ro.sd.a2.service.UserService;


@Controller
public class FirstController {

    private static final Logger log = LoggerFactory.getLogger(FirstController.class);

    @Autowired
    private UserService userService ;//= new UserService();

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

        log.info("Logged");

        mav.addObject("grupa", 5);
        // adaugi x obiecte
        mav.setViewName("home");
        //log the final outcome: Success y?
        return mav;
    }

}
