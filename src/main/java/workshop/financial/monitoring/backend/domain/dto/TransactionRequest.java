package workshop.financial.monitoring.backend.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import workshop.financial.monitoring.backend.domain.model.CustomerType;
import workshop.financial.monitoring.backend.domain.model.Status;
import workshop.financial.monitoring.backend.domain.model.TransactionType;

import java.math.BigDecimal;
import java.util.Date;

@Schema(description = "Транзакция: DTO запроса")
public record TransactionRequest(
        @Schema(description = "Тип лица", example = "Физическое лицо")
        CustomerType customerType,

        @Schema(description = "Дата и время операции")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        Date transactionTime,

        @Schema(description = "Тип транзакции", example = "Поступление")
        TransactionType transactionType,

        @Schema(description = "Комментарий к операции")
        String description,

        @Schema(description = "Сумма", example = "100.5")
        BigDecimal sumValue,

        @Schema(description = "Статус операции", example = "Новая")
        Status status,

        @Schema(description = "Банк отправителя")
        @NotBlank(message = "Банк отправителя не может быть пустым")
        String senderBank,

        @Schema(description = "Счет поступления/списания")
        @NotBlank(message = "Счет не может быть пустым")
        String account,

        @Schema(description = "Банк получателя")
        @NotBlank(message = "Банк получателя не может быть пустым")
        String recipientBank,

        @Schema(description = "ИНН получателя")
        @Pattern(regexp = "\\d{10}", message = "ИНН должен содержать 10 символов")
        @NotBlank(message = "ИНН получателя не может быть пустым")
        String inn,

        @Schema(description = "Категория")
        CategoryRequest category,

        @Schema(description = "Телефон")
        @Pattern(regexp = "^(8|\\+7)\\d{10}",
                message = "Номер телефона должен соответствовать формат (+7 или 8) + 10 символов")
        @NotBlank(message = "Телефон не может быть пустым")
        String phone) {
}
