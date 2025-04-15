package workshop.financial.monitoring.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import workshop.financial.monitoring.backend.model.Category;

@Repository
public interface CategoryJPARepository extends JpaRepository<Category, Long> {

}
