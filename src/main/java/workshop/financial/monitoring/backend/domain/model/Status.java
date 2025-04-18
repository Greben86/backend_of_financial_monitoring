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
 * Статус операции
 */
@RequiredArgsConstructor
public enum Status {

    NEW("Новая"),
    CONFIRMED("Подтвержденная"),
    IN_PROGRESS("В обработке"),
    ABORTED("Отменена"),
    SUCCESS("Платеж выполнен"),
    DELETED("Платеж удален"),
    RETURN("Возврат");

    @Getter(onMethod_ = @JsonValue)
    private final String name;

    private static final Map<String, Status> MAP = Stream.of(values())
            .collect(Collectors.toMap(Status::getName, Function.identity()));

    @JsonCreator
    public static Status forValue(final String value) {
        return MAP.get(value);
    }
}
