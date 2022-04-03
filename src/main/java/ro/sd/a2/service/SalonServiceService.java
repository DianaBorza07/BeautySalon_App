package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.entity.SalonService;
import ro.sd.a2.repository.SalonServiceRepository;

@Service
public class SalonServiceService {
    @Autowired
    private SalonServiceRepository salonServiceRepository;

    public SalonService saveSalonService(SalonService salonService){
        return salonServiceRepository.save(salonService);
    }
}
