package ro.sd.a2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.dto.AddressDTO;
import ro.sd.a2.dto.BeautySalonDTO;
import ro.sd.a2.entity.Address;
import ro.sd.a2.entity.BeautySalon;
import ro.sd.a2.entity.Schedule;
import ro.sd.a2.service.AddressService;
import ro.sd.a2.service.BeautySalonService;
import org.apache.commons.lang3.StringUtils;
import ro.sd.a2.service.ScheduleService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Controller for the beauty salon
 * @author Diana Borza
 */
@Controller
public class BeautySalonController {
    /**
     * Logger for displaying in the console error/info/success messages
     */
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    /**
     * Message to display if some requirements are empty
     */
    private StringBuilder insertMessage = new StringBuilder("Please insert ");

    /**
     * The service for the beauty salon
     */
    @Autowired
    private BeautySalonService beautySalonService ;

    /**
     * The service for the address of the beauty salon
     */
    @Autowired
    private AddressService addressService;

    /**
     * Get method that returns the view for the html page that allow provider to add new salon
     * @return model and view for the html page
     */
    @GetMapping("/addSalon")
    public ModelAndView showNewSalon() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/addSalon");
        return mav;
    }

    /**
     * Post method that inserts into database the new salon with the specified fields by the provider
     * @param name the name of the beauty salon
     * @param number the street number of the beauty salon
     * @param street the street of the beauty salon
     * @return model and view for the html page
     */
    @PostMapping("/addSalon")
    public ModelAndView addNewSalon(@RequestParam String name, @RequestParam String number, @RequestParam String street){
        ModelAndView mav = new ModelAndView();
        if(StringUtils.isBlank(name))
            insertMessage.append("salon name ");
        if(StringUtils.isBlank(number))
            insertMessage.append("street number ");
        if(StringUtils.isBlank(street))
            insertMessage.append("street name ");
        if(!(insertMessage.compareTo(new StringBuilder("Please insert "))==0)) {
            String message = insertMessage.toString();
            mav.addObject("errorMessage", message);
            insertMessage.delete(0,insertMessage.length());
            insertMessage.append("Please insert ");
        }
        else {
            BeautySalonDTO beautySalonDTO = BeautySalonDTO.builder().id(UUID.randomUUID().toString()).name(name).build();
            BeautySalon beautySalon = beautySalonService.saveBeautySalon(beautySalonDTO);
            AddressDTO addressDTO = AddressDTO.builder().id(UUID.randomUUID().toString()).street(street).number(number).build();
            Address address = addressService.saveAddress(addressDTO);
            if (beautySalon == null) {
                log.info("Error on saving the beauty salon!");
            }
            if (address == null) {
                log.info("Error on saving the address!");
            }
            boolean var = beautySalonService.addAddress(beautySalonDTO, address);
            if (var) {
                mav.addObject("successMessage", "Beauty salon added successfully");
                init();
                log.info("Beauty salon added successfully!");
            } else {
                mav.addObject("errorMessage", "Error on saving the beauty salon!");
                log.info("Error on saving the beauty salon!");
            }
        }
        mav.setViewName("/addSalon");
        return mav;
    }

    /**
     * Method that lists all salons form the database
     * @return model and view for the html page
     */
    @GetMapping("/listSalons")
    public ModelAndView showSalons() {
        ModelAndView mav = new ModelAndView();
        List<BeautySalon> beautySalonList = beautySalonService.getAllSalons();
        mav.addObject("salonList",beautySalonList);
        mav.setViewName("/listSalons");
        return mav;
    }
    @Autowired
    private ScheduleService scheduleService;

    public void init(){
        LocalDateTime a = LocalDateTime.of(2022, 5, 1, 10, 0);
        List<BeautySalon> beautySalonList = beautySalonService.getAllSalons();
        List<LocalDateTime> localDateTimes = new ArrayList<>();
        LocalDateTime aux;
        for(int day = 0;day<31;day++) {
            if(a.get(ChronoField.DAY_OF_WEEK)+day != 6 && a.get(ChronoField.DAY_OF_WEEK)+day!=7 )
                for (int i = 0; i < 7; i++) {
                         aux = LocalDateTime.of(a.getYear(), a.getMonth(), a.getDayOfMonth()+day, a.getHour() + i, 0);
                         if(aux!=null)
                             localDateTimes.add(aux);
                }
            else if(a.get(ChronoField.DAY_OF_WEEK)+day==6){
                for (int i = 0; i < 4; i++) {
                    aux = LocalDateTime.of(a.getYear(), a.getMonth(), a.getDayOfMonth()+day, a.getHour() + i, 0);
                    if(aux!=null)
                        localDateTimes.add(aux);
                }
            }
        }

        beautySalonList.stream().forEach(beautySalon ->
              localDateTimes.stream().forEach(dateTime -> {
                Schedule schedule1 = Schedule.builder().id(UUID.randomUUID().toString()).available(true).beautySalon(beautySalon).dayHour(dateTime).build();
                scheduleService.saveSchedule(schedule1);
            })
        );

    }
}
