package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sd.a2.entity.Service;

public interface ServiceRepository extends JpaRepository<Service,String> {
}
