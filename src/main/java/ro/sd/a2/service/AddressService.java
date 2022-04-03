package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.dto.AddressDTO;
import ro.sd.a2.dto.BeautySalonDTO;
import ro.sd.a2.entity.Address;
import ro.sd.a2.entity.BeautySalon;
import ro.sd.a2.mapper.AddressMapper;
import ro.sd.a2.repository.AddressRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository ;

    public Address saveAddress(AddressDTO address){
        Address address1 = AddressMapper.dtoToAddress(address);
        return addressRepository.save(address1);
    }

    public List<AddressDTO> getAllAddresses(){
        List<Address>addresses = addressRepository.findAll();
        List<AddressDTO> addressDTOS = new ArrayList<>();
        addresses.stream().forEach(a->addressDTOS.add(AddressMapper.addressToDto(a)));
        return  addressDTOS;
    }

    public boolean updateAddress(AddressDTO addr){
        Address address = addressRepository.findByStreetAndNumber(addr.getStreet(),addr.getNumber());
        if(address == null)
            return  false;
        Address addres1 = addressRepository.save(address);
        if(addres1==null)
            return false;
        return  true;
    }

    public boolean deleteAddress(AddressDTO addr){
        Address address = addressRepository.findByStreetAndNumber(addr.getStreet(),addr.getNumber());
        if(address == null)
            return  false;
        addressRepository.delete(address);
        return  true;
    }


}
