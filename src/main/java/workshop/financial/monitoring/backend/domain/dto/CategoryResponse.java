package workshop.financial.monitoring.backend.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Категория: DTO ответа")
public record CategoryResponse(
        @Schema(description = "Первичный ключ", example = "1")
        @NotBlank
        Long id,
        @Schema(description = "Название категории", example = "Доходы")
        @NotBlank
        String name) {
}
