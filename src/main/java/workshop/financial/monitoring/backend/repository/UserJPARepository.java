package workshop.financial.monitoring.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import workshop.financial.monitoring.backend.model.User;

import java.util.Optional;

@Repository
public interface UserJPARepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
