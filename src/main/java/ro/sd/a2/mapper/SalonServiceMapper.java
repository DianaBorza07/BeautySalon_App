package ro.sd.a2.mapper;

import ro.sd.a2.dto.SalonServiceDTO;
import ro.sd.a2.entity.SalonService;

public class SalonServiceMapper {
    public static SalonService dtoToSalonService(SalonServiceDTO salonServiceDTO){
        return SalonService.builder().id(salonServiceDTO.getId()).name(salonServiceDTO.getName()).price(salonServiceDTO.getPrice()).build();
    }

    public static SalonServiceDTO salonServiceToDTO(SalonService salonService){
        return SalonServiceDTO.builder().id(salonService.getId()).name(salonService.getName()).price(salonService.getPrice()).build();
    }
}
