package workshop.financial.monitoring.backend.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Контроллер для перехвата исключений доступа к методам {@link MethodArgumentNotValidException}
 */
@ControllerAdvice
public class NoValidAdvice {

    /**
     * Если поймали исключение {@link MethodArgumentNotValidException}, то возвращаем статус 401
     *
     * @return ответ со статусом 401
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<String> handleException(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.TEXT_PLAIN)
                .body(exception.getMessage());
    }
}
