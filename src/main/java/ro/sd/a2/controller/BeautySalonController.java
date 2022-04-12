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
import ro.sd.a2.service.AddressService;
import ro.sd.a2.service.BeautySalonService;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;
@Controller
public class BeautySalonController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private StringBuilder insertMessage = new StringBuilder("Please insert ");

    @Autowired
    private BeautySalonService beautySalonService ;

    @Autowired
    private AddressService addressService;

    @GetMapping("/addSalon")
    public ModelAndView showNewSalon() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/addSalon");
        return mav;
    }

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
            System.out.println(insertMessage);
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
                log.info("Beauty salon added successfully!");
            } else {
                mav.addObject("errorMessage", "Error on saving the beauty salon!");
                log.info("Error on saving the beauty salon!");
            }
        }
        mav.setViewName("/addSalon");
        return mav;
    }
}
