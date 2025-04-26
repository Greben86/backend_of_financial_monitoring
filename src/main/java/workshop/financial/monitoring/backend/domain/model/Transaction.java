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
public class Transaction implements Serializable {

    @Serial
    private static final long serialVersionUID = -3163479472859140497L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    private User user;

    private CustomerType customerType;

    private Date transactionTime;

    private TransactionType transactionType;

    private String description;

    @Column(precision = 16, scale = 5)
    private BigDecimal sumValue;

    private Status status;

    private String senderBank;

    private String account;

    private String recipientBank;

    private String inn;

    private String recipientAccount;

    @ManyToOne
    private Category category;

    private String phone;
}
