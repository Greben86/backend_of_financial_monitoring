package workshop.financial.monitoring.backend.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "categories")
@Schema(description = "Категория: сущность базы данных")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    @Schema(description = "Первичный ключ", example = "1")
    private Long id;
    @Schema(description = "Пользователь")
    @ManyToOne
    private User user;
    @Schema(description = "Название категории", example = "Доход")
    private String name;
}
