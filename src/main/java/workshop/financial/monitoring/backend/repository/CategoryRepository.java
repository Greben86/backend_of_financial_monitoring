package workshop.financial.monitoring.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import workshop.financial.monitoring.backend.domain.model.Category;
import workshop.financial.monitoring.backend.domain.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUser(User user);
    Optional<Category> findByIdAndUser(Long id, User user);
    boolean existsByNameAndUser(String name, User user);
}
