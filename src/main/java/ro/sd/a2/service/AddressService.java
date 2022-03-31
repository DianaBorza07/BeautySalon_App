package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.entity.Address;
import ro.sd.a2.repository.AddressRepository;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository ;

    public Address saveAddress(Address address){
        return addressRepository.save(address);
    }
}
