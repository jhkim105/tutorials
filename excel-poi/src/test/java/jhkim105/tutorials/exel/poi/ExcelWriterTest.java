package jhkim105.tutorials.exel.poi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import org.junit.jupiter.api.Test;


class ExcelWriterTest {

  @Test
  void test() throws FileNotFoundException {
    FileInputStream fileInputStream = new FileInputStream("src/test/resources/file_example_XLSX_10.xlsx");
    List<List<String>> list = ExcelReader.read(fileInputStream);

    FileOutputStream fileOutputStream = new FileOutputStream("target/out.xlsx");
    ExcelWriter.write(list, fileOutputStream);
  }
}