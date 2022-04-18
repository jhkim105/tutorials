package jhkim105.tutorials.exel.poi;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {
  public static void write(List<List<String>> input, OutputStream outputStream) {
    write(input, null, outputStream);
  }
  public static void write(List<List<String>> input, String sheetName, OutputStream outputStream) {
    Workbook workbook = new XSSFWorkbook();
    Sheet sheet = StringUtils.isBlank(sheetName) ? workbook.createSheet() : workbook.createSheet(sheetName);
    writeHeader(workbook, sheet, input);
    writeBody(workbook, sheet, input);
    try(workbook) {
      workbook.write(outputStream);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  private static void writeHeader(Workbook workbook, Sheet sheet, List<List<String>> input) {
    CellStyle headerStyle = workbook.createCellStyle();
    headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    XSSFFont font = ((XSSFWorkbook) workbook).createFont();
    font.setFontName("Arial");
    font.setFontHeightInPoints((short) 16);
    font.setBold(true);
    headerStyle.setFont(font);

    Row header = sheet.createRow(0);
    List<String> rowInput = input.get(0);
    int cellIndex = 0;
    for(String cellInput : rowInput) {
      Cell headerCell = header.createCell(cellIndex++);
      headerCell.setCellValue(cellInput);
      headerCell.setCellStyle(headerStyle);
    }
  }

  private static void writeBody(Workbook workbook, Sheet sheet, List<List<String>> input) {
    CellStyle style = workbook.createCellStyle();
    style.setWrapText(true);

    for (int i = 1; i < input.size(); i++) {
      List<String> rowInput = input.get(i);
      Row row = sheet.createRow(i);
      int cellIndex = 0;
      for(String cellInput : rowInput) {
        Cell cell = row.createCell(cellIndex++);
        cell.setCellValue(cellInput);
        cell.setCellStyle(style);
      }
    }
  }

}
