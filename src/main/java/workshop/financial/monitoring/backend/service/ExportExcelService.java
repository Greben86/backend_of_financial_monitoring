package workshop.financial.monitoring.backend.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import workshop.financial.monitoring.backend.exception.LogicException;
import workshop.financial.monitoring.backend.repository.TransactionRepository;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExportExcelService {

    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
    private final DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

    private final TransactionRepository repository;

    public void export(final HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=export.xlsx");

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
            for (final var transaction : repository.findAll()) {
                final var row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(transaction.getCustomerType().getName());
                row.createCell(1).setCellValue(formatter.format(transaction.getTransactionTime()));
                row.createCell(2).setCellValue(transaction.getTransactionType().getName());
                row.createCell(3).setCellValue(transaction.getDescription());
                row.createCell(4).setCellValue(transaction.getSumValue().toString());
                row.createCell(5).setCellValue(transaction.getStatus().getName());
                row.createCell(6).setCellValue(transaction.getSenderBank());
                row.createCell(7).setCellValue(transaction.getAccount());
                row.createCell(8).setCellValue(transaction.getRecipientBank());
                row.createCell(9).setCellValue(transaction.getInn());
                row.createCell(10).setCellValue(transaction.getRecipientAccount());
                row.createCell(11).setCellValue(transaction.getCategory().getName());
                row.createCell(12).setCellValue(transaction.getPhone());
            }

            // Write to response stream
            workbook.write(outputStream);
        } catch (IOException e) {
            log.error("Ошибка экспорта в Excel", e);
            throw new LogicException("Ошибка экспорта в Excel: " + e.getMessage());
        }
    }
}
