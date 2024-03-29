Excel Reading And Writing with Apache Poi
=========================================



## Maven Dependencies
```xml
    <!-- https://mvnrepository.com/artifact/org.apache.poi/poi -->
    <dependency>
      <groupId>org.apache.poi</groupId>
      <artifactId>poi</artifactId>
      <version>5.2.2</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.apache.poi/poi-ooxml -->
    <dependency>
      <groupId>org.apache.poi</groupId>
      <artifactId>poi-ooxml</artifactId>
      <version>5.2.2</version>
    </dependency>
```

## Reading from Excel
```java
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
```

## Writing to Excel

```java
public class ExcelWriter {

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


```

## Download
```java
  @GetMapping("/download")
  public ResponseEntity<InputStreamResource> download() {
    List<List<String>> list = getList();

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, contentDispositionHeader("output.xlsx"))
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(inputStreamResource(list));
  }

  private InputStreamResource inputStreamResource(List<List<String>> list) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    ExcelWriter.write(list, byteArrayOutputStream);

    return new InputStreamResource(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
  }

  private String contentDispositionHeader(String fileName) {
    return "attachment;filename=\"" + fileName + "\"";
  }

```

## Download TestCase
````java
  @Test
  void download() throws Exception {
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders
            .get("/download")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_OCTET_STREAM));

    resultActions
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM));

    File downloadFile = new File("target/output.xlsx");
    FileUtils.writeByteArrayToFile(downloadFile, resultActions.andReturn().getResponse().getContentAsByteArray());

    List<List<String>> list = ExcelReader.read(new FileInputStream(downloadFile));
    assertThat(list.size()).isEqualTo(4);
  }
````
