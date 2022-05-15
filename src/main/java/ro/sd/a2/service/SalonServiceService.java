package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.dto.BeautySalonDTO;
import ro.sd.a2.dto.SalonServiceDTO;
import ro.sd.a2.entity.BeautySalon;
import ro.sd.a2.entity.SalonService;
import ro.sd.a2.mapper.BeautySalonMapper;
import ro.sd.a2.mapper.SalonServiceMapper;
import ro.sd.a2.repository.SalonServiceRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalonServiceService {
    @Autowired
    private SalonServiceRepository salonServiceRepository;

    public SalonService saveSalonService(SalonService salonService){
        return salonServiceRepository.save(salonService);
    }

    public List<SalonService> getAllSalonServices(){
        return salonServiceRepository.findAll();
    }

    public boolean updateSalonService(String serviceName, float price){
        SalonService salonService = salonServiceRepository.findByName(serviceName);
        if(salonService!=null) {
            salonService.setPrice(price);
            SalonService salonService1 = salonServiceRepository.save(salonService);
            if (salonService1 == null)
                return false;
            return true;
        }
        return false;
    }

    public boolean deleteService(String serviceName){
        SalonService salonService = salonServiceRepository.findByName(serviceName);
        if(salonService == null)
            return false;
        salonServiceRepository.delete(salonService);
        return true;
    }

    public List<SalonServiceDTO> getAllServicesDTO(){
        List<SalonService> salonServices = salonServiceRepository.findAll();
        List<SalonServiceDTO> salonServicesDTO = new ArrayList<>();
        salonServices.stream().forEach(b->salonServicesDTO.add(SalonServiceMapper.salonServiceToDTO(b)));
        return salonServicesDTO;
    }

    public SalonService getServiceByName(String name){
        return salonServiceRepository.findByName(name);
    }
}
