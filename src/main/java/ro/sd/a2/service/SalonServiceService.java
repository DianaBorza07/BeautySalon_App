package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.entity.SalonService;
import ro.sd.a2.repository.SalonServiceRepository;

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
}
