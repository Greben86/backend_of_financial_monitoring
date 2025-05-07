package workshop.financial.monitoring.backend.service.export;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workshop.financial.monitoring.backend.domain.dto.TransactionResponse;
import workshop.financial.monitoring.backend.exception.LogicException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Компонент экспорта в excel
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service("exportExcelService")
public class ExportExcelService implements ExportService<TransactionResponse>  {

    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
    private final DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

    /**
     * {@inheritDoc}
     */
    public void export(final List<TransactionResponse> items, final HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        final var currentDateTime = formatter.format(new Date());
        response.setHeader("Content-Disposition", "attachment; filename=export_" + currentDateTime + "." + getExtension());

        // Create a new workbook and sheet
        try (final var outputStream = response.getOutputStream();
             final var workbook = new XSSFWorkbook()) {
            final var sheet = workbook.createSheet("Report");

            // Create header row
            final var headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Тип лица");
            headerRow.createCell(1).setCellValue("Дата и время операции");
            headerRow.createCell(2).setCellValue("Тип транзакции");
            headerRow.createCell(3).setCellValue("Комментарий к операции");
            headerRow.createCell(4).setCellValue("Сумма");
            headerRow.createCell(5).setCellValue("Статус операции");
            headerRow.createCell(6).setCellValue("Банк отправителя");
            headerRow.createCell(7).setCellValue("Счет поступления/списания");
            headerRow.createCell(8).setCellValue("Банк получателя");
            headerRow.createCell(9).setCellValue("ИНН получателя");
            headerRow.createCell(10).setCellValue("Расчетный счет получателя");
            headerRow.createCell(11).setCellValue("Категория");
            headerRow.createCell(12).setCellValue("Телефон получателя");

            // Populate data rows
            int rowNum = 1;
            for (final var transaction : items) {
                final var row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(transaction.customerType().getName());
                row.createCell(1).setCellValue(formatter.format(transaction.transactionTime()));
                row.createCell(2).setCellValue(transaction.transactionType().getName());
                row.createCell(3).setCellValue(transaction.description());
                row.createCell(4).setCellValue(transaction.sumValue().toString());
                row.createCell(5).setCellValue(transaction.status().getName());
                row.createCell(6).setCellValue(transaction.senderBank());
                row.createCell(7).setCellValue(transaction.account());
                row.createCell(8).setCellValue(transaction.recipientBank());
                row.createCell(9).setCellValue(transaction.inn());
                row.createCell(10).setCellValue(transaction.recipientAccount());
                row.createCell(11).setCellValue(transaction.category().name());
                row.createCell(12).setCellValue(transaction.phone());
            }

            // Write to response stream
            workbook.write(outputStream);
        } catch (IOException e) {
            log.error("Ошибка экспорта в Excel", e);
            throw new LogicException("Ошибка экспорта в Excel: " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getExtension() {
        return "xlsx";
    }
}
