package ro.sd.a2.mapper;

import ro.sd.a2.dto.BeautySalonDTO;
import ro.sd.a2.entity.BeautySalon;

public class BeautySalonMapper {

    public static BeautySalon dtoToBeautySalon(BeautySalonDTO beautySalon){
        return BeautySalon.builder().id(beautySalon.getId()).name(beautySalon.getName()).build();
    }

    public static BeautySalonDTO beautySalonToDto(BeautySalon beautySalon){
        return BeautySalonDTO.builder().id(beautySalon.getId()).name(beautySalon.getName()).build();
    }
}
