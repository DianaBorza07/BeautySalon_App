package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.AppUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, String> {
    AppUser findByEmailAndPassword(String email, String password);
    AppUser findByEmailAndUsername(String email, String username);
}
