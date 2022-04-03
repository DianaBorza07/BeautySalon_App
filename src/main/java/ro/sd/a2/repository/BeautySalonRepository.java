package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sd.a2.entity.BeautySalon;

public interface BeautySalonRepository extends JpaRepository<BeautySalon,String> {
    BeautySalon findByName(String name);
}
