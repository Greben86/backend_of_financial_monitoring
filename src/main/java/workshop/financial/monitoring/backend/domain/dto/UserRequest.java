package workshop.financial.monitoring.backend.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Пользователь: DTO запроса")
public record UserRequest(
        @Schema(description = "Имя пользователя", example = "Вася")
        @Size(min = 1, max = 255, message = "Длина поля должна быть от 1 до 255 символов")
        @NotBlank(message = "Поле не может быть пустым")
        String username) {
}
