package workshop.financial.monitoring.backend.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import workshop.financial.monitoring.backend.domain.model.Status;

@Schema(description = "Транзакция: DTO смены статуса")
public record TransactionStatusRequest(
        @Schema(description = "Статус операции", example = "Платеж выполнен")
        @NotNull(message = "Статус операции не может быть пустым и должен иметь допустимое название")
        Status status) {
}
