package jhkim105.tutorials.exel.poi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class ExcelReaderTest {

  @Test
  void test() throws FileNotFoundException {
    FileInputStream fileInputStream = new FileInputStream("src/test/resources/file_example_XLSX_10.xlsx");
    List<List<String>> list = ExcelReader.read(fileInputStream);
    log.info("{}", list);
  }

}