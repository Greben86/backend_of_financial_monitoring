package workshop.financial.monitoring.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import workshop.financial.monitoring.backend.model.UserAccount;

@Repository
public interface UserJPARepository extends JpaRepository<UserAccount, Long> {

}
