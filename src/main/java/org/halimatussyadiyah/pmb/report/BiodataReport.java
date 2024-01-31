package org.halimatussyadiyah.pmb.report;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.halimatussyadiyah.pmb.domain.Biodata;
import org.halimatussyadiyah.pmb.exception.ApiException;
import org.springframework.core.io.InputStreamResource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import static java.util.stream.IntStream.range;

@Slf4j
public class BiodataReport {
    private  XSSFWorkbook workbook;
    private  XSSFSheet sheet;
    private final List<Biodata> biodata;
    private static  String[] HEADERS = { "ID", "Name", "Tempat Lahir", "Tanggal Lahir", "Jenis Kelamin", "Agama", "Alamat", "Email", "Status Kawin", "Hobi", "Anak ke", "jumlah Saudara", "Status Verifikasi"};

    public BiodataReport(List<Biodata> biodata) {
        this.biodata = biodata;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Biodata");
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
            for(Biodata biodata1: biodata) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(biodata1.getId());
                row.createCell(1).setCellValue(biodata1.getNama());
                row.createCell(2).setCellValue(biodata1.getTempatLahir());
                row.createCell(3).setCellValue(biodata1.getTanggalLahir());
                row.createCell(4).setCellValue(biodata1.getJk().toString());
                row.createCell(5).setCellValue(biodata1.getAgama());
                row.createCell(6).setCellValue(biodata1.getEmail());
                row.createCell(7).setCellValue(biodata1.getStatusKawin().toString());
                row.createCell(8).setCellValue(biodata1.getHobi());
                row.createCell(9).setCellValue(biodata1.getAnakKe());
                row.createCell(10).setCellValue(biodata1.getJmlSaudara());
                row.createCell(11).setCellValue(biodata1.getStatusVerifikasi());
                row.createCell(12).setCellValue(biodata1.getCreatedAt());
            }
            workbook.write(out);
            return new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("Unable to export report file");
        }
    }
}