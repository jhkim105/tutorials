package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class ExcelReader {
  public static List<List<String>> read(MultipartFile multipartFile, int columnCount) {
    List<List<String>> result = new ArrayList<>();
    try {
      InputStream inputStream = multipartFile.getInputStream();
      XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
      XSSFSheet sheet = workBook.getSheetAt(0);
      XSSFRow row;
      int rowCount = sheet.getPhysicalNumberOfRows();
      int cellNum;

      for (int r = 1; r < rowCount; r++) {
        cellNum = 0;
        row = sheet.getRow(r);
        if (row == null) {
          break;
        }
        List<String> columns = new ArrayList<>();
        for (int c = 0; c < columnCount; c++) {
          columns.add(getDefaultStringCellValue(row.getCell(cellNum++)));
        }
        result.add(columns);
      }
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
    return result;
  }

  public static String getDefaultStringCellValue(XSSFCell cell) {
    return StringUtils.defaultString(getStringCellValue(cell));
  }

  public static String getStringCellValue(XSSFCell cell) {
    if (cell == null)
      return null;

    CellType cellType = cell.getCellTypeEnum();
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
      result = null;
    }
    return result;
  }
}
