package workshop.financial.monitoring.backend.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import workshop.financial.monitoring.backend.domain.dto.TransactionRequest;
import workshop.financial.monitoring.backend.domain.dto.TransactionResponse;
import workshop.financial.monitoring.backend.domain.dto.TransactionStatusRequest;
import workshop.financial.monitoring.backend.service.TransactionService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("transaction")
@Tag(name = "REST API: Транзакции")
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "Добавление транзакции")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/add",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public TransactionResponse addTransaction(@RequestBody @Valid TransactionRequest transaction) {
        return transactionService.addTransaction(transaction);
    }

    @Operation(summary = "Редактирование транзакции")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}/edit",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public TransactionResponse editTransaction(@PathVariable("id") Long id,
                                               @RequestBody @Valid TransactionRequest transaction) {
        return transactionService.editTransaction(id, transaction);
    }

    @Operation(summary = "Изменение статуса транзакции")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}/status",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public TransactionResponse editTransaction(@PathVariable("id") Long id,
                                               @RequestBody @Valid TransactionStatusRequest status) {
        return transactionService.changeStatus(id, status);
    }

    @Operation(summary = "Все транзакции")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransactionResponse> allTransactions() {
        return transactionService.allTransactions();
    }
}
