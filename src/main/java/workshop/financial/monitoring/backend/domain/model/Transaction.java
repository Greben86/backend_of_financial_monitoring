package workshop.financial.monitoring.backend.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "transactions")
@Schema(description = "Транзакция: сущность базы данных")
public class Transaction implements Serializable {

    @Serial
    private static final long serialVersionUID = -3163479472859140497L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Schema(description = "Первичный ключ", example = "1")
    private Long id;

    @Schema(description = "Пользователь")
    @ManyToOne
    private User user;

    @Schema(description = "Тип лица", example = "Физическое лицо")
    private CustomerType customerType;

    @Schema(description = "Дата и время операции")
    private Date transactionTime;

    @Schema(description = "Тип транзакции", example = "Поступление")
    private TransactionType transactionType;

    @Schema(description = "Комментарий к операции")
    private String description;

    @Schema(description = "Сумма", example = "100.5")
    @Column(precision = 16, scale = 5)
    private BigDecimal sumValue;

    @Schema(description = "Статус операции", example = "Новая")
    private Status status;

    @Schema(description = "Банк отправителя")
    private String senderBank;

    @Schema(description = "Счет поступления/списания")
    private String account;

    @Schema(description = "Банк получателя")
    private String recipientBank;

    @Schema(description = "ИНН получателя")
    private String inn;

    @Schema(description = "Категория")
    @ManyToOne
    private Category category;

    @Schema(description = "Телефон")
    private String phone;
}
