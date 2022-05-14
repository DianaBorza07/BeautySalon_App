package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sd.a2.entity.SalonService;

public interface SalonServiceRepository extends JpaRepository<SalonService,String> {
    SalonService findByName(String name);
}
