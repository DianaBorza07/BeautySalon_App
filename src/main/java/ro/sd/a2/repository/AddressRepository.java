package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sd.a2.entity.Address;

public interface AddressRepository extends JpaRepository<Address,String> {

}
