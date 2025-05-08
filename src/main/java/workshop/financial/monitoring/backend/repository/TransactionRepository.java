package workshop.financial.monitoring.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import workshop.financial.monitoring.backend.domain.model.Status;
import workshop.financial.monitoring.backend.domain.model.Transaction;
import workshop.financial.monitoring.backend.domain.model.User;

import java.util.Optional;

/**
 * Репозиторий транзакций
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

    long countByStatus(Status status);
    Optional<Transaction> findByIdAndUser(Long id, User user);
}
