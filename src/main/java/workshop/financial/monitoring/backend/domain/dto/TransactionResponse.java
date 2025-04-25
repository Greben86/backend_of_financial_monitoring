package workshop.financial.monitoring.backend.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import workshop.financial.monitoring.backend.domain.model.CustomerType;
import workshop.financial.monitoring.backend.domain.model.Status;
import workshop.financial.monitoring.backend.domain.model.TransactionType;

import java.math.BigDecimal;
import java.util.Date;

@Schema(description = "Транзакция: DTO ответа")
public record TransactionResponse(
        @Schema(description = "Первичный ключ", example = "1")
        Long id,

        @Schema(description = "Тип лица", example = "Физическое лицо")
        CustomerType customerType,

        @Schema(description = "Дата и время операции", pattern = "dd-MM-yyyy HH:mm:ss")
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
        String senderBank,

        @Schema(description = "Счет поступления/списания")
        String account,

        @Schema(description = "Банк получателя")
        String recipientBank,

        @Schema(description = "ИНН получателя")
        String inn,

        @Schema(description = "Расчетный счет получателя")
        String recipientAccount,

        @Schema(description = "Категория")
        CategoryResponse category,

        @Schema(description = "Телефон")
        String phone) {
}
