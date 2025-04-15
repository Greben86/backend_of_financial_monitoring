package workshop.financial.monitoring.backend.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import workshop.financial.monitoring.backend.model.TransactionRecord;
import workshop.financial.monitoring.backend.service.TransactionService;

@RequiredArgsConstructor
@RestController
@RequestMapping("transaction")
public class TransactionRestController {

    private final TransactionService transactionService;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TransactionRecord addTransaction(@RequestBody TransactionRecord transaction) {
        return transaction;
    }

    @PutMapping(value = "/{id}/edit", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TransactionRecord editTransaction(@PathVariable("id") Long id, @RequestBody TransactionRecord transaction) {
        return transaction;
    }
}
