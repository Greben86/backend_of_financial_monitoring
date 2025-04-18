package workshop.financial.monitoring.backend.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import workshop.financial.monitoring.backend.domain.dto.TransactionRequest;
import workshop.financial.monitoring.backend.domain.dto.TransactionResponse;
import workshop.financial.monitoring.backend.service.TransactionService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("transaction")
@Tag(name = "REST API: Транзакции")
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "Добавление транзакции")
    @PostMapping(value = "/add",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public TransactionResponse addTransaction(@RequestBody @Valid TransactionRequest transaction) {
        return new TransactionResponse(
                0L,
                transaction.customerType(),
                transaction.transactionTime(),
                transaction.transactionType(),
                transaction.description(),
                transaction.sumValue(),
                transaction.status(),
                transaction.senderBank(),
                transaction.account(),
                transaction.recipientBank(),
                transaction.inn(),
                transaction.category(),
                transaction.phone()
        );
    }

//    @Operation(summary = "Редактирование транзакции")
//    @PutMapping(value = "/{id}/edit", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public Transaction editTransaction(@PathVariable("id") Long id, @RequestBody Transaction transaction) {
//        return transaction;
//    }

    @Operation(summary = "Все транзакции")
    @GetMapping(value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransactionResponse> allTransactions() {
        return List.of();
    }
}
