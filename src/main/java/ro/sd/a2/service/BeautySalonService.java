package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.dto.BeautySalonDTO;
import ro.sd.a2.entity.Address;
import ro.sd.a2.entity.BeautySalon;
import ro.sd.a2.mapper.BeautySalonMapper;
import ro.sd.a2.repository.BeautySalonRepository;

import java.util.ArrayList;
import java.util.List;


@Service
public class BeautySalonService {
    @Autowired
    private BeautySalonRepository beautySalonRepository;

    public BeautySalon saveBeautySalon(BeautySalonDTO beautySalon){
        BeautySalon salon = BeautySalonMapper.dtoToBeautySalon(beautySalon);
        return beautySalonRepository.save(salon);
    }

    public List<BeautySalonDTO> getAllSalonsDTO(){
        List<BeautySalon> beautySalonList = beautySalonRepository.findAll();
        List<BeautySalonDTO> beautySalonDTOS = new ArrayList<>();
        beautySalonList.stream().forEach(b->beautySalonDTOS.add(BeautySalonMapper.beautySalonToDto(b)));
        return beautySalonDTOS;
    }

    public List<BeautySalon> getAllSalons(){
        return beautySalonRepository.findAll();

    }

    public boolean updateBeautySalon(BeautySalon salonDTO){
        //BeautySalon beautySalon = beautySalonRepository.findByName(salonDTO.getName());
        if(salonDTO == null)
            return  false;
        BeautySalon beautySalon1 = beautySalonRepository.save(salonDTO);
        if(beautySalon1==null)
            return false;
        return  true;
    }

    public boolean deleteBeautySalon(BeautySalonDTO beautySalonDTO){
        BeautySalon beautySalon = beautySalonRepository.findByName(beautySalonDTO.getName());
        if(beautySalon == null)
            return  false;
       beautySalonRepository.delete(beautySalon);
        return  true;
    }

    public boolean addAddress(BeautySalonDTO beautySalonDTO,Address address){
        BeautySalon beautySalon = beautySalonRepository.findByName(beautySalonDTO.getName());
        if(beautySalon == null)
            return  false;
        beautySalon.setAddress(address);
        BeautySalon beautySalon1 = beautySalonRepository.save(beautySalon);
        if(beautySalon1==null)
            return false;
        return  true;
    }

    public BeautySalon getSalonByName(String name){
        return beautySalonRepository.findByName(name);
    }
}
