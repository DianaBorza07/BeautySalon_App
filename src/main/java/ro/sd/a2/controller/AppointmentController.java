package ro.sd.a2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.dto.AppUserDTO;
import ro.sd.a2.dto.AppointmentDTO;
import ro.sd.a2.dto.PayloadDTO;
import ro.sd.a2.entity.*;
import ro.sd.a2.mapper.AppointmentMapper;
import ro.sd.a2.service.*;
import ro.sd.a2.utils.PDFGenerator;
import ro.sd.a2.utils.TextEditor;
import ro.sd.a2.utils.TextGenerator;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private BeautySalonService beautySalonService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private UserService userService;

    @Autowired
    private SalonServiceService salonServiceService;

    private static final String TOKEN = "b88a21a4-45f0-42bc-9f26-499a403292e6";

    @GetMapping("/createAppointment")
    public ModelAndView showCreate(){
        ModelAndView mav = new ModelAndView();
        List<BeautySalon> beautySalonServiceList = beautySalonService.getAllSalons();
        mav.addObject("beautySalons",beautySalonServiceList);

        mav.setViewName("/createAppointment");
        return mav;
    }

    @PostMapping("/createAppointment")
    public ModelAndView createAppointment(@ModelAttribute(value="salon") String beautySalon, @RequestParam String service, @RequestParam String date, @RequestParam String time,@RequestParam String switchOne, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        List<BeautySalon> beautySalonServiceList = beautySalonService.getAllSalons();
        mav.addObject("beautySalons", beautySalonServiceList);
        String str = date+" "+time;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        LocalDateTime scheduleDate = LocalDateTime.of(dateTime.getYear(),dateTime.getMonth(),dateTime.getDayOfMonth(),dateTime.getHour(),0);
        BeautySalon beautySalon1 = beautySalonService.getSalonByName(beautySalon);
        Schedule schedule = scheduleService.findByDateAndSalon(scheduleDate,beautySalon1);
        mav.setViewName("/createAppointment");
        if(schedule!=null ){
            if(schedule.getAvailable()){
                AppUserDTO user = (AppUserDTO) session.getAttribute("LOGGED_USER");
                AppUser appUser = userService.findByEmail(user.getEmail());
                if(appUser!=null){
                    scheduleService.updateSchedule(scheduleDate,false,beautySalon1);

                    SalonService salonService = salonServiceService.getServiceByName(service);
                    Appointment appointment = Appointment.builder().id(UUID.randomUUID().toString()).date(scheduleDate)
                            .user(appUser).beautySalon(beautySalon1).salonService(salonService).build();
                    appointmentService.saveAppointment(appointment);
                    mav.addObject("successMessage","Appoint made successfully!");
                    String text = appUser.getName()+"#"+scheduleDate.toString()+"#"+beautySalon1.getName()+"#"+salonService.getName()+"#"+salonService.getPrice();

                    if(switchOne.compareTo("txt")==0){
                        TextGenerator textGenerator = new TextGenerator();
                        TextEditor textEditor = new TextEditor(textGenerator);
                        textEditor.generate(text);
                    }
                    else{
                        PDFGenerator pdfGenerator = new PDFGenerator();
                        TextEditor textEditor = new TextEditor(pdfGenerator);
                        textEditor.generate(text);
                    }

                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.set(HttpHeaders.AUTHORIZATION, TOKEN);

                    PayloadDTO payloadDTO = PayloadDTO.builder().id(appointment.getId()).username(user.getUsername()).email(user.getEmail()).name(user.getName()).beautySalon(beautySalon1.getName()).date(scheduleDate.toString()).salonService(salonService.getName()).price(salonService.getPrice()).build();
                    RestTemplate restTemplate = new RestTemplate();
                    HttpEntity< PayloadDTO > entity = new HttpEntity<>(payloadDTO, headers);

                    try {
                        Boolean response = restTemplate.postForObject("http://localhost:8082/sendEmail", entity, Boolean.class);

                    }catch (RestClientException e){
                        System.out.println("Server unavailable");
                        //e.printStackTrace();
                    }
                }
            }
            else
                mav.addObject("errorMessage","Appointment unavailable!");
        }
        else
            mav.addObject("errorMessage","Invalid appointment. Please select another date");
        return mav;
    }

    @GetMapping("/viewAppointments")
    public ModelAndView showAppointments(HttpSession session){
        ModelAndView mav = new ModelAndView();
        AppUserDTO user = (AppUserDTO) session.getAttribute("LOGGED_USER");
        List<Appointment> appointments1 = appointmentService.getAllAppointmentsOfAnUser(user);
        List<AppointmentDTO> appointments = new ArrayList<>();
        appointments1.stream().forEach(a->appointments.add(AppointmentMapper.appointmentToDto(a)));
        mav.addObject("appointments",appointments);
        mav.setViewName("/viewAppointments");
        return mav;
    }

    @RequestMapping(value="/deleteAppointment",method = RequestMethod.POST)
    public ModelAndView cancelAppointment(@ModelAttribute("ap1") AppointmentDTO ap1, HttpSession session){
        ModelAndView mav = new ModelAndView();
        appointmentService.deleteAppointmentWithId(ap1.getId());
        mav.addObject("successMessage","Appointment cancelled");
        AppUserDTO user = (AppUserDTO) session.getAttribute("LOGGED_USER");
        List<Appointment> appointments1 = appointmentService.getAllAppointmentsOfAnUser(user);
        List<AppointmentDTO> appointments = new ArrayList<>();
        appointments1.stream().forEach(a->appointments.add(AppointmentMapper.appointmentToDto(a)));
        mav.addObject("appointments",appointments);
        mav.setViewName("/viewAppointments");
        return mav;
    }
    }
