package workshop.financial.monitoring.backend.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import workshop.financial.monitoring.backend.domain.model.CustomerType;
import workshop.financial.monitoring.backend.domain.model.TransactionType;

import java.math.BigDecimal;
import java.util.Date;

@Schema(description = "Транзакция: DTO запроса")
public record TransactionRequest(
        @Schema(description = "Тип лица", example = "Физическое лицо")
        @NotNull(message = "Тип лица не может быть пустым и должен иметь допустимое название")
        CustomerType customerType,

        @Schema(description = "Дата и время операции", pattern = "dd-MM-yyyy HH:mm:ss")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        @NotNull(message = "Дата и время операции не может быть пустым")
        Date transactionTime,

        @Schema(description = "Тип транзакции", example = "Поступление")
        @NotNull(message = "Тип транзакции не может быть пустым и должен иметь допустимое название")
        TransactionType transactionType,

        @Schema(description = "Комментарий к операции")
        @Size(max = 255, message = "Длина должна быть не более 255 символов")
        String description,

        @Schema(description = "Сумма", example = "100.5")
        @NotNull(message = "Сумма не может быть пустой")
        BigDecimal sumValue,

        @Schema(description = "Банк отправителя")
        @Size(max = 255, message = "Длина названия банка должна быть не более 255 символов")
        @NotBlank(message = "Банк отправителя не может быть пустым")
        String senderBank,

        @Schema(description = "Счет поступления/списания")
        @Size(max = 255, message = "Длина счета должна быть не более 255 символов")
        @NotBlank(message = "Счет не может быть пустым")
        String account,

        @Schema(description = "Банк получателя")
        @Size(max = 255, message = "Длина названия банка должна быть не более 255 символов")
        @NotBlank(message = "Банк получателя не может быть пустым")
        String recipientBank,

        @Schema(description = "ИНН получателя")
        @Pattern(regexp = "\\d{10}", message = "ИНН должен содержать 10 символов")
        @NotBlank(message = "ИНН получателя не может быть пустым")
        String inn,

        @Schema(description = "Расчетный счет получателя")
        @NotBlank(message = "Расчетный сче получателя не может быть пустым")
        String recipientAccount,

        @Schema(description = "Идентификатор категории")
        @NotNull(message = "Идентификатор категории не может быть пустым")
        Long categoryId,

        @Schema(description = "Телефон")
        @Pattern(regexp = "^(8|\\+7)\\d{10}",
                message = "Номер телефона должен соответствовать формат (+7 или 8) + 10 символов")
        @NotBlank(message = "Телефон не может быть пустым")
        String phone) {
}
