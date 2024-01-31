package org.halimatussyadiyah.pmb.report;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.halimatussyadiyah.pmb.domain.Card;
import org.halimatussyadiyah.pmb.domain.Dokumen;
import org.halimatussyadiyah.pmb.exception.ApiException;
import org.springframework.core.io.InputStreamResource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.stream.IntStream.range;

@Slf4j
public class DokumenReport {
    public static final String DATE_FORMATTER = "yyyy-MM-dd hh:mm:ss";
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Dokumen> cards;
    private static String[] HEADERS = {"ID", "Biodata Nama", "Username", "Password", "Tanggal", "Waktu", "Ruang"};

    public DokumenReport(List<Dokumen> cards) {
        this.cards = cards;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Cards");
        setHeaders();
    }

    private void setHeaders() {
        Row headerRow = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        style.setFont(font);
        range(0, HEADERS.length).forEach(index -> {
            Cell cell = headerRow.createCell(index);
            cell.setCellValue(HEADERS[index]);
            cell.setCellStyle(style);
        });
    }

    public InputStreamResource export() {
        return generateReport();
    }

    private InputStreamResource generateReport() {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            CellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setFontHeight(10);
            style.setFont(font);
            int rowIndex = 1;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            for (Dokumen invoice : cards) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(invoice.getId());
                row.createCell(1).setCellValue(invoice.getKartuKeluarga());
                row.createCell(2).setCellValue(invoice.getAkte());
                row.createCell(3).setCellValue(invoice.getBiodata().getNama());
                row.createCell(4).setCellValue(invoice.getIjazah());
                row.createCell(5).setCellValue(invoice.getPasFoto());
                row.createCell(6).setCellValue(invoice.getStatusVerifikasi());
            }
            workbook.write(out);
            return new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("Unable to export report file");
        }
    }
}