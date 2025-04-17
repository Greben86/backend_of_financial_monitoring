package workshop.financial.monitoring.backend.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import workshop.financial.monitoring.backend.model.Transaction;
import workshop.financial.monitoring.backend.service.TransactionService;

@RequiredArgsConstructor
@RestController
@RequestMapping("transaction")
@Tag(name = "Транзакции")
public class TransactionRestController {

    private final TransactionService transactionService;

    @Operation(summary = "Добавление транзакции")
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        return transaction;
    }

    @Operation(summary = "Редактирование транзакции")
    @PutMapping(value = "/{id}/edit", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Transaction editTransaction(@PathVariable("id") Long id, @RequestBody Transaction transaction) {
        return transaction;
    }
}
