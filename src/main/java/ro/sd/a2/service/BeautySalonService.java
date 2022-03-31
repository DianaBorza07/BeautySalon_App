package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.entity.BeautySalon;
import ro.sd.a2.repository.BeautySalonRepository;

@Service
public class BeautySalonService {
    @Autowired
    private BeautySalonRepository beautySalonRepository;

    public BeautySalon saveBeautySalon(BeautySalon beautySalon){
        return beautySalonRepository.save(beautySalon);
    }
}
