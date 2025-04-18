package workshop.financial.monitoring.backend.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Категория: DTO запроса")
public record CategoryRequest(
        @Schema(description = "Название категории", example = "Доходы")
        @Size(min = 1, max = 255, message = "Длина поля должна быть от 1 до 255 символов")
        @NotBlank(message = "Поле не может быть пустыми")
        String name) {
}
