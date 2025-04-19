package workshop.financial.monitoring.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import workshop.financial.monitoring.backend.domain.model.Transaction;
import workshop.financial.monitoring.backend.domain.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий транзакций
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUser(User user);
    Optional<Transaction> findByIdAndUser(Long id, User user);
}
