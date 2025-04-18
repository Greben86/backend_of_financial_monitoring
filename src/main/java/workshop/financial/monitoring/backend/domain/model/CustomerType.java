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
 * Тип лица
 */
@RequiredArgsConstructor
public enum CustomerType {

    INDIVIDUAL("Физическое лицо"),
    LEGAL("Юридическое лицо");

    @Getter(onMethod_ = @JsonValue)
    private final String name;

    private static final Map<String, CustomerType> MAP = Stream.of(values())
            .collect(Collectors.toMap(CustomerType::getName, Function.identity()));

    @JsonCreator
    public static CustomerType forValue(final String value) {
        return MAP.get(value);
    }
}
