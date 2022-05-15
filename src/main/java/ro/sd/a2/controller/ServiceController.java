package ro.sd.a2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.dto.BeautySalonDTO;
import ro.sd.a2.dto.SalonServiceDTO;
import ro.sd.a2.entity.BeautySalon;
import ro.sd.a2.entity.SalonService;
import ro.sd.a2.service.BeautySalonService;
import ro.sd.a2.service.SalonServiceService;

import java.util.List;

@Controller
public class ServiceController {
    @Autowired
    private SalonServiceService salonServiceService;

    @Autowired
    private BeautySalonService beautySalonService;

    @PostMapping("/addService")
    public ModelAndView addNewService(@RequestParam String salon,@RequestParam String service){
        ModelAndView mav = new ModelAndView();
        List<BeautySalonDTO> salons = beautySalonService.getAllSalonsDTO();
        mav.addObject("salons",salons);
        List<SalonServiceDTO> services = salonServiceService.getAllServicesDTO();
        mav.addObject("services",services);
        mav.setViewName("/addservice");
        BeautySalon beautySalon = beautySalonService.getSalonByName(salon);
        SalonService salonService = salonServiceService.getServiceByName(service);
        if(!beautySalon.getServices().contains(salonService) && !salonService.getBeautySalonList().contains(beautySalon)) {
            beautySalon.getServices().add(salonService);
            salonService.getBeautySalonList().add(beautySalon);
            boolean var = beautySalonService.updateBeautySalon(beautySalon);
            SalonService s = salonServiceService.saveSalonService(salonService);
            if (var != true || s == null)
                mav.addObject("errorMessage", "Cannot add service");
            else
                mav.addObject("successMessage", "Service added successfully");
        }
        else
            mav.addObject("errorMessage","Service already belongs to the selected salon");
        mav.setViewName("/addservice");
        return mav;
    }

    @GetMapping("/addService")
    public ModelAndView showAddService(){
        ModelAndView mav = new ModelAndView();
        List<BeautySalonDTO> salons = beautySalonService.getAllSalonsDTO();
        mav.addObject("salons",salons);
        List<SalonServiceDTO> services = salonServiceService.getAllServicesDTO();
        mav.addObject("services",services);
        mav.setViewName("/addservice");
        return mav;
    }

}
