package workshop.financial.monitoring.backend.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import workshop.financial.monitoring.backend.domain.model.CustomerType;
import workshop.financial.monitoring.backend.domain.model.Status;
import workshop.financial.monitoring.backend.domain.model.TransactionType;

import java.math.BigDecimal;
import java.util.Date;

@Schema(description = "Транзакция: DTO запроса")
public record TransactionResponse(
        @Schema(description = "Первичный ключ", example = "1")
        @NotBlank
        Long id,

        @Schema(description = "Тип лица", example = "Физическое лицо")
        @NotBlank
        CustomerType customerType,

        @Schema(description = "Дата и время операции")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        Date transactionTime,

        @Schema(description = "Тип транзакции", example = "Поступление")
        @NotBlank
        TransactionType transactionType,

        @Schema(description = "Комментарий к операции")
        String description,

        @Schema(description = "Сумма", example = "100.5")
        @NotBlank
        BigDecimal sumValue,

        @Schema(description = "Статус операции", example = "Новая")
        @NotBlank
        Status status,

        @Schema(description = "Банк отправителя")
        String senderBank,

        @Schema(description = "Счет поступления/списания")
        String account,

        @Schema(description = "Банк получателя")
        String recipientBank,

        @Schema(description = "ИНН получателя")
        String inn,

        @Schema(description = "Категория")
        @NotBlank
        CategoryRequest category,

        @Schema(description = "Телефон")
        String phone) {
}
