package workshop.financial.monitoring.backend.service.export;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workshop.financial.monitoring.backend.domain.dto.TransactionResponse;
import workshop.financial.monitoring.backend.exception.LogicException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * Компонент экспорта в csv
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service("exportCsvService")
public class ExportCsvService implements ExportService<TransactionResponse> {

    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
    private final DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

    /**
     * {@inheritDoc}
     */
    public void export(final List<TransactionResponse> items, final HttpServletResponse response) {
        response.setContentType("text/csv;charset=UTF-8");
        final var currentDateTime = formatter.format(new Date());
        response.setHeader("Content-Disposition", "attachment; filename=export_" + currentDateTime + "." + getExtension());

        try (final var printer = new CSVPrinter(response.getWriter(), CSVFormat.DEFAULT)) {
            String[] csvHeader = {"Тип лица", "Дата и время операции", "Тип транзакции", "Комментарий к операции", "Сумма",
                    "Статус операции", "Банк отправителя", "Счет поступления/списания", "Банк получателя", "ИНН получателя",
                    "Расчетный счет получателя", "Категория", "Телефон получателя"};
            printer.printRecord(Stream.of(csvHeader));

            // Populate data rows
            for (final var transaction : items) {
                printer.printRecord(transaction.customerType().getName(),
                        formatter.format(transaction.transactionTime()),
                        transaction.transactionType().getName(),
                        transaction.description(),
                        transaction.sumValue().toString(),
                        transaction.status().getName(),
                        transaction.senderBank(),
                        transaction.account(),
                        transaction.recipientBank(),
                        transaction.inn(),
                        transaction.recipientAccount(),
                        transaction.category().name(),
                        transaction.phone());
            }
        } catch (IOException e) {
            log.error("Ошибка экспорта в Csv", e);
            throw new LogicException("Ошибка экспорта в Csv: " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getExtension() {
        return "csv";
    }
}
