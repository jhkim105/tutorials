package com.example.beanio;

import com.example.beanio.example.Employee;
import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.beanio.BeanReader;
import org.beanio.StreamFactory;
import org.junit.jupiter.api.Test;

@Slf4j
public class CsvTest {


  @Test
  void readCsv() {
    // create a StreamFactory
    StreamFactory factory = StreamFactory.newInstance();
    // load the mapping file
    factory.load("/Users/jihwankim/dev/my/tutorials/beanio/src/main/resources/mapping-csv.xml");

    // use a StreamFactory to create a BeanReader
    BeanReader in = factory.createReader("employeeFile", new File("/Users/jihwankim/dev/my/tutorials/beanio/src/main/resources/employee.csv"));
    Employee employee;
    while ((employee = (Employee) in.read()) != null) {
      log.info("{}", employee);
    }
    in.close();
  }

}
