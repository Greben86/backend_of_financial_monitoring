package workshop.financial.monitoring.backend.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import workshop.financial.monitoring.backend.domain.dto.FilterDTO;
import workshop.financial.monitoring.backend.domain.dto.TransactionRequest;
import workshop.financial.monitoring.backend.domain.dto.TransactionResponse;
import workshop.financial.monitoring.backend.domain.dto.TransactionStatusRequest;
import workshop.financial.monitoring.backend.service.export.ExportExcelService;
import workshop.financial.monitoring.backend.service.TransactionService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("transaction")
@Tag(name = "REST API: Транзакции")
@Validated
public class TransactionController {

    private final TransactionService transactionService;
    private final ExportExcelService exportExcelService;

    @Operation(summary = "Добавление транзакции")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/add",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody TransactionResponse addTransaction(@RequestBody @Valid TransactionRequest transaction) {
        return transactionService.addTransaction(transaction);
    }

    @Operation(summary = "Редактирование транзакции")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}/edit",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody TransactionResponse editTransaction(
            @PathVariable("id") Long id,
            @RequestBody @Valid TransactionRequest transaction) {
        return transactionService.editTransaction(id, transaction);
    }

    @Operation(summary = "Изменение статуса транзакции")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}/status",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody TransactionResponse statusTransaction(
            @PathVariable("id") Long id,
            @RequestBody @Valid TransactionStatusRequest status) {
        return transactionService.changeStatus(id, status);
    }

    @Operation(summary = "Удаление транзакции")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<Void> deleteTransaction(@PathVariable("id") Long id) {
        transactionService.deleteTransactions(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Все транзакции")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<TransactionResponse> searchTransactions(@ModelAttribute FilterDTO properties) {
        return transactionService.searchTransactions(properties);
    }

    @Operation(summary = "Экспорт транзакций пользователя в Excel")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/export")
    @CrossOrigin
    public void export(@ModelAttribute FilterDTO properties, HttpServletResponse response) {
        exportExcelService.export(transactionService.searchTransactions(properties), response);
    }
}
