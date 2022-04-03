package ro.sd.a2.mapper;

import ro.sd.a2.dto.AddressDTO;
import ro.sd.a2.entity.Address;

public class AddressMapper {
    public static Address dtoToAddress(AddressDTO addressDTO){
        return Address.builder().id(addressDTO.getId()).number(addressDTO.getNumber()).street(addressDTO.getStreet()).build();
    }

    public static AddressDTO addressToDto(Address address){
        return AddressDTO.builder().id(address.getId()).number(address.getNumber()).street(address.getStreet()).build();
    }
}
