package workshop.financial.monitoring.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workshop.financial.monitoring.backend.domain.dto.CategoryResponse;
import workshop.financial.monitoring.backend.domain.dto.TransactionRequest;
import workshop.financial.monitoring.backend.domain.dto.TransactionResponse;
import workshop.financial.monitoring.backend.domain.dto.TransactionStatusRequest;
import workshop.financial.monitoring.backend.domain.model.Status;
import workshop.financial.monitoring.backend.domain.model.Transaction;
import workshop.financial.monitoring.backend.exception.LogicException;
import workshop.financial.monitoring.backend.repository.CategoryRepository;
import workshop.financial.monitoring.backend.repository.TransactionRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Сервис управления транзакциями
 */
@RequiredArgsConstructor
@Service
@Transactional
public class TransactionService {

    private final TransactionRepository repository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    /**
     * Добавление транзакции
     *
     * @param request данные транзакции
     * @return новая транзакция
     */
    public TransactionResponse addTransaction(final TransactionRequest request) {
        var user = userService.getCurrentUser();
        var category = categoryRepository.findByIdAndUser(request.categoryId(), user);
        if (category.isEmpty()) {
            throw new LogicException(String.format("Категория пользователя \"%s\" c идентификатором \"%d\" не найдена",
                    user.getUsername(), request.categoryId()));
        }

        var transaction = new Transaction();
        transaction.setUser(user);
        transaction.setCustomerType(request.customerType());
        transaction.setTransactionTime(request.transactionTime());
        transaction.setTransactionType(request.transactionType());
        transaction.setDescription(request.description());
        transaction.setSumValue(request.sumValue());
        transaction.setStatus(Status.NEW);
        transaction.setSenderBank(request.senderBank());
        transaction.setAccount(request.account());
        transaction.setRecipientBank(request.recipientBank());
        transaction.setInn(request.inn());
        transaction.setCategory(category.get());
        transaction.setPhone(request.phone());

        repository.save(transaction);

        return convertToResponse(transaction);
    }

    /**
     * Редактирование транзакции
     *
     * @param id идентификатор транзакции
     * @param request данные транзакции
     * @return транзакция
     */
    public TransactionResponse editTransaction(final Long id, final TransactionRequest request) {
        var user = userService.getCurrentUser();
        var category = categoryRepository.findByIdAndUser(request.categoryId(), user);
        if (category.isEmpty()) {
            throw new LogicException(String.format("Категория пользователя \"%s\" c идентификатором \"%d\" не найдена",
                    user.getUsername(), request.categoryId()));
        }

        var transaction = repository.findByIdAndUser(id, user)
                .orElseThrow(() -> new LogicException(
                        String.format("Транзакция по идентификатору \"%d\" не найдена", id)));

        if (!Status.NEW.equals(transaction.getStatus())) {
            throw new LogicException(String.format("Редактировать можно только транзакцию в статусе \"%s\"",
                    Status.NEW.getName()));
        }

        transaction.setUser(user);
        transaction.setCustomerType(request.customerType());
        transaction.setTransactionTime(request.transactionTime());
        transaction.setTransactionType(request.transactionType());
        transaction.setDescription(request.description());
        transaction.setSumValue(request.sumValue());
        transaction.setStatus(Status.NEW);
        transaction.setSenderBank(request.senderBank());
        transaction.setAccount(request.account());
        transaction.setRecipientBank(request.recipientBank());
        transaction.setInn(request.inn());
        transaction.setCategory(category.get());
        transaction.setPhone(request.phone());

        repository.save(transaction);

        return convertToResponse(transaction);
    }

    /**
     * Выборка всех транзакций пользователя
     *
     * @return список транзакций
     */
    public List<TransactionResponse> allTransactions() {
        var user = userService.getCurrentUser();
        return repository.findByUser(user).stream()
                .map(this::convertToResponse)
                .toList();
    }

    /**
     * Изменение статуса транзакции
     *
     * @param id идентификатор транзакции
     * @param statusRequest новый статус
     * @return
     */
    public TransactionResponse changeStatus(final Long id, final TransactionStatusRequest statusRequest) {
        var user = userService.getCurrentUser();
        var transaction = repository.findByIdAndUser(id, user)
                .orElseThrow(() -> new LogicException(
                        String.format("Транзакция по идентификатору \"%d\" не найдена", id)));

        if (Objects.equals(transaction.getStatus(), statusRequest.status())) {
            throw new LogicException("Новый статус совпадает со старым");
        }

        transaction.setStatus(statusRequest.status());
        repository.save(transaction);

        return convertToResponse(transaction);
    }

    private TransactionResponse convertToResponse(final Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getCustomerType(),
                transaction.getTransactionTime(),
                transaction.getTransactionType(),
                transaction.getDescription(),
                transaction.getSumValue(),
                transaction.getStatus(),
                transaction.getSenderBank(),
                transaction.getAccount(),
                transaction.getRecipientBank(),
                transaction.getInn(),
                new CategoryResponse(transaction.getCategory().getId(),
                        transaction.getCategory().getName()),
                transaction.getPhone()
        );
    }
}
