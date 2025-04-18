package workshop.financial.monitoring.backend.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Тип транзакции
 */
@RequiredArgsConstructor
public enum TransactionType {

    DEBIT("Списание"),
    CREDIT("Поступление");

    @Getter(onMethod_ = @JsonValue)
    private final String name;

    private static final Map<String, TransactionType> MAP = Stream.of(values())
            .collect(Collectors.toMap(TransactionType::getName, Function.identity()));

    @JsonCreator
    public static TransactionType forValue(final String value) {
        return MAP.get(value);
    }
}
