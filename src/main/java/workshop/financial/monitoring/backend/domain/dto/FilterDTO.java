package workshop.financial.monitoring.backend.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@Schema(description = "DTO с полями для фильтрации транзакций")
public final class FilterDTO {
    @Schema(description = "Фильтр по банку отправителя")
    private final String senderBank;
    @Schema(description = "Фильтр по банку получателя")
    private final String recipientBank;
    @Schema(description = "Минимальная дата диапазона (условие - больше или равно)")
    @DateTimeFormat(pattern="dd-MM-yyyy")
    private final Date dateStart;
    @Schema(description = "Максимальная дата диапазона (условие - строго меньше)")
    @DateTimeFormat(pattern="dd-MM-yyyy")
    private final Date dateEnd;
    @Schema(description = "Фильтр по статусу транзакции")
    private final String status;
    @Schema(description = "Фильтр по инн")
    private final String inn;
    @Schema(description = "Минимальное значение суммы операции (условие - больше или равно)")
    @NumberFormat(pattern = "###.#####")
    private final BigDecimal minSumValue;
    @Schema(description = "Максимальное значение суммы операции (условие - строго меньше)")
    @NumberFormat(pattern = "###.#####")
    private final BigDecimal maxSumValue;
    @Schema(description = "Фильтр по типу операции")
    private final String transactionType;
    @Schema(description = "Фильтр по категории")
    private final String categoryName;
}
