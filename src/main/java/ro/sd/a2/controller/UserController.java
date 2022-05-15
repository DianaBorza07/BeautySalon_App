package ro.sd.a2.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.dto.AppUserDTO;
import ro.sd.a2.dto.UserLoginDTO;
import ro.sd.a2.entity.AppUser;
import ro.sd.a2.entity.UserRole;
import ro.sd.a2.factory.UserFactory;
import ro.sd.a2.service.ProducerService;
import ro.sd.a2.service.UserRoleService;
import ro.sd.a2.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller class for the user operations
 * @author Diana Borza
 */
@RestController
public class UserController {
    /**
     * Logger for displaying in the console error/info/success messages
     */
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    /**
     * Message to display if some requirements are empty
     */
    private StringBuilder insertMessage = new StringBuilder("Please insert ");

    /**
     * The service for the user
     */
    @Autowired
    private UserService userService ;

    /**
     * The service for the user role
     */
    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private ProducerService producerService;

    @Value("${app.message}")
    private String response;

    /**
     * Get method for displaying the profile html page
     * @return model and view for the html page
     */
    @GetMapping("/profile")
    public ModelAndView showProfile() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("profile");
        return mav;
    }

    /**
     * Get method for displaying the home page
     * @return model and view for the html page
     */
    @GetMapping("/home")
    public ModelAndView showHome() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("home");
        return mav;
    }

    /**
     * Get method for displaying sing in page
     * @return model and view for the html page
     */
    @GetMapping("/signin")
    public ModelAndView showSignIn() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/signin");
        return mav;
    }

    /**
     * Post method that searches the user into database in order to log in
     * @param email user email
     * @param password user password
     * @return model and view for the html page
     */
    @PostMapping("/signin")
    public ModelAndView signIn(@RequestParam String email, @RequestParam String password,HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        if(StringUtils.isBlank(email))
            insertMessage.append("email ");
        if(StringUtils.isBlank(password))
            insertMessage.append("password ");
        if(!(insertMessage.compareTo(new StringBuilder("Please insert "))==0)) {
            String message = insertMessage.toString();
            mav.addObject("errorMessage", message);
            insertMessage.delete(0,insertMessage.length());
            insertMessage.append("Please insert ");
            mav.setViewName("/signin");
        }
        else {
            AppUserDTO user = userService.getUserByEmailAndPassword(email, password);
            String errorMessage = null;
            if (user != null) {
                request.getSession().setAttribute("LOGGED_USER", user);
                log.info("User logged in!");
                if(user.getUserRole().getRoleName().compareTo("Customer") == 0 )
                    mav.setViewName("/home");
                else
                    mav.setViewName("/profile");
            }
            else {
                log.error("User not found!");
                errorMessage = "Incorrect credentials";
                mav.setViewName("/signin");
            }

            mav.addObject("errorMessage", errorMessage);
        }
        return mav;
    }

    /**
     * Get method for displaying sign up page
     * @return model and view for the html page
     */
    @GetMapping("/signup")
    public ModelAndView showSignUp() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/signup");
        return mav;
    }


    @RequestMapping(value="/signup", method = RequestMethod.POST)
    public ModelAndView sendMessage(@ModelAttribute(value="user") UserLoginDTO user, HttpServletRequest request) {
        System.out.println("User: "+user.getUsername());
        System.out.println("Email: "+user.getEmail());
        producerService.sendMessage(user);
        ModelAndView mav = signUp(user.getEmail(),user.getUsername(),user.getPassword(),user.getName());
        if(mav.getStatus() == HttpStatus.CREATED) {
            AppUserDTO appUser = userService.getUserByEmailAndPassword(user.getEmail(),user.getPassword());
            request.getSession().setAttribute("LOGGED_USER", appUser);
        }
        log.info("email sent: ");
        return mav;
    }
    /**
     * Post method that adds new user into database
     * @param email user email
     * @param username user username
     * @param password user password
     * @param name user name
     * @return model and view for the html page
     */
    public ModelAndView signUp(String email, String username, String password, String name){
        ModelAndView mav = new ModelAndView();
        if(StringUtils.isBlank(email))
            insertMessage.append("email ");
        if(StringUtils.isBlank(username))
            insertMessage.append("username ");
        if(StringUtils.isBlank(name))
            insertMessage.append("name ");
        if(StringUtils.isBlank(password))
            insertMessage.append("password ");
        if(!(insertMessage.compareTo(new StringBuilder("Please insert "))==0)) {
            String message = insertMessage.toString();
            mav.addObject("errorMessage", message);
            insertMessage.delete(0,insertMessage.length());
            insertMessage.append("Please insert ");
            mav.setViewName("/signup");
        }
        else {
            UserRole userRole = userRoleService.getUserRoleByName("Customer");
            UserFactory userFactory = new UserFactory();
            UserLoginDTO appUser = UserLoginDTO.builder().username(username).password(password).name(name).email(email).build();
            appUser = userFactory.createUser(userRole, appUser);
            AppUser newUser = userService.saveUser(appUser);

            if (newUser != null) {
                mav.setViewName("/home");
                log.info("User created successfully!");
                mav.setStatus(HttpStatus.CREATED);
            }
            else {
                mav.setViewName("/signup");
                mav.addObject("errorMessage", "User already exists");
                log.error("Error on creating the user");
                mav.setStatus(HttpStatus.NOT_MODIFIED);
            }
        }
        return mav;
    }

    /**
     * Post method that modifies the user email and username
     * @param newEmail user new email
     * @param newUsername user new username
     * @return model and view for the html page
     */
    @PostMapping("/updateProfile")
    public ModelAndView updateUser(@RequestParam String newEmail, @RequestParam String newUsername, HttpSession session){
        ModelAndView mav = new ModelAndView();
        if(StringUtils.isBlank(newEmail))
            insertMessage.append("new email ");
        if(StringUtils.isBlank(newUsername))
            insertMessage.append("new username ");
        if(!(insertMessage.compareTo(new StringBuilder("Please insert "))==0)) {
            String message = insertMessage.toString();
            mav.addObject("errorMessage", message);
            insertMessage.delete(0,insertMessage.length());
            insertMessage.append("Please insert ");
        }
        else {
            AppUserDTO user = (AppUserDTO) session.getAttribute("LOGGED_USER");
            AppUserDTO user1 = AppUserDTO.builder().id(user.getId()).username(newUsername).email(newEmail).name(user.getName()).build();
            boolean var = userService.updateUser(user1);

            if (var) {
                log.info("User updated successfully!");
                mav.addObject("successMessage", "User updated successfully!");
            } else {
                log.error("Error on updating the user!");
                mav.addObject("errorMessage", "Error on updating the user!");
            }
        }
        mav.setViewName("/updateProfile");
        return  mav;
    }

    /**
     * Get method for displaying the page for updating the user profile
     * @return model and view for the html page
     */
    @GetMapping("/updateProfile")
    public ModelAndView showUpdateProfile() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/updateProfile");
        return mav;
    }

    /**
     * Get method for displaying all the users from the database
     * @return model and view for the html page
     */
    @GetMapping("/viewUsers")
    public ModelAndView showUsers() {
        ModelAndView mav = new ModelAndView();
        List<AppUserDTO> userDTOList = userService.getAllUsers();
        mav.addObject("users", userDTOList);
        mav.setViewName("/viewUsers");
        return mav;
    }

    @PostMapping("/destroy")
    public ModelAndView destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/signin");
        return mav;
    }

    @PostMapping("/makeAdmin")
    public ModelAndView makeAdmin(@ModelAttribute(value="user") AppUser user){
        ModelAndView mav = new ModelAndView();
        System.out.println("Controller: "+user.getEmail());
        boolean var = userService.changeRole(user.getEmail());
        mav.setViewName("/viewUsers");
        List<AppUserDTO> userDTOList = userService.getAllUsers();
        mav.addObject("users", userDTOList);
        mav.setViewName("/viewUsers");
        return mav;
    }





}
