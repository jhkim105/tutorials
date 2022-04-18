package jhkim105.tutorials.exel.poi;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
  public static List<List<String>> read(InputStream inputStream) {
    List<List<String>> result = new ArrayList<>();
    try {
      Workbook workBook = new XSSFWorkbook(inputStream);
      Sheet sheet = workBook.getSheetAt(0);
      for (Row row : sheet) {
        List<String> columns = new ArrayList<>();
        if (row.getCell(0) == null || StringUtils.isBlank(getDefaultStringCellValue(row.getCell(0)))) {
          continue;
        }
        for (Cell cell : row) {
          columns.add(getDefaultStringCellValue(cell));
        }
        result.add(columns);
      }
    } catch (IOException ex) {
      throw new IllegalStateException(ex);
    }
    return result;
  }

  public static String getDefaultStringCellValue(Cell cell) {
    return StringUtils.defaultString(getStringCellValue(cell));
  }

  public static String getStringCellValue(Cell cell) {
    CellType cellType = cell.getCellType()
        .equals(CellType.FORMULA) ? cell.getCachedFormulaResultType() : cell.getCellType();

    String result;
    if (cellType == CellType.NUMERIC) {
      result = String.valueOf(cell.getNumericCellValue());
      result = org.apache.commons.lang3.StringUtils.removeEnd(result, ".0");
    } else if (cellType == CellType.STRING) {
      result = cell.getStringCellValue();
    } else if (cellType == CellType.FORMULA) {
      result = String.valueOf((int) cell.getNumericCellValue());
    } else if (cellType == CellType.BOOLEAN) {
      result = String.valueOf(cell.getBooleanCellValue());
    } else {
      throw new IllegalStateException(String.format("Invalid Cell Type. [%s]", cellType));
    }
    return result;
  }
}
