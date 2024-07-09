package jhkim105.tutorials;


import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

class CsvTest {

  @Test
  void test() {
    String csvFile = "src/test/resources/sample.csv";

    try (Reader reader = new FileReader(csvFile);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

      for (CSVRecord csvRecord : csvParser) {
        for (String field : csvRecord) {
          System.out.print(field + " ");
        }
        System.out.println();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
