package workshop.financial.monitoring.backend.service.export;

import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * Интерфейс экспорта в заданный формат
 */
public interface ExportService<T> {

    /**
     * Метод экспорта
     *
     * @param items список позиций для экспорта
     * @param response объект {@link HttpServletResponse} из контроллера
     */
    void export(final List<T> items, final HttpServletResponse response);

    /**
     * Метод возвращает расширение имени файла экспорта
     *
     * @return расширение имени файла экспорта
     */
    String getExtension();
}
