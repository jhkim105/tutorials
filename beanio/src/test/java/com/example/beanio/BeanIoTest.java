package com.example.beanio;

import com.example.beanio.example.Body;
import com.example.beanio.example.Employee;
import com.example.beanio.example.Header;
import com.example.beanio.example.Message;
import com.example.beanio.example.Message2;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.beanio.BeanReader;
import org.beanio.BeanWriter;
import org.beanio.Marshaller;
import org.beanio.StreamFactory;
import org.junit.jupiter.api.Test;

@Slf4j
public class BeanIoTest {


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


  @Test
  void testFixedLength() {
    Message message = Message.builder()
        .header(Header.builder()
            .size(20)
            .token("token")
            .build())
        .body(Body.builder()
            .msg("msg")
            .code("code")
            .number(21)
            .build())
        .build();

    log.info("message->{}", message);

    StreamFactory factory = StreamFactory.newInstance();
    factory.loadResource("mapping-fixedLength.xml");
    StringWriter stringWriter = new StringWriter();
    BeanWriter beanWriter = factory.createWriter("message", stringWriter);
    beanWriter.write(message);
    String messageString = stringWriter.toString();
    log.info("messageString->{}", messageString);


    StringReader reader = new StringReader(messageString);
    BeanReader beanReader = factory.createReader("message", reader);
    Message message2 = (Message)beanReader.read();
    log.info("{}", message2);
  }

  @Test
  void testFixedLength2() {
    Message message = Message.builder()
        .header(Header.builder()
            .size(20)
            .token("token")
            .build())
        .body(Body.builder()
            .msg("msg")
            .code("code")
            .number(21)
            .build())
        .build();

    log.info("message->{}", message);

    StreamFactory factory = StreamFactory.newInstance();
    factory.loadResource("mapping-fixedLength.xml");
    Marshaller marshaller = factory.createMarshaller("message");
    log.info(marshaller.marshal("message01", message).toString());
  }

  @Test
  void testFixedLength3() {
    Map<String, Object> message = new HashMap<>();
    message.put("size", 20);
    message.put("token", "token");
    message.put("msg", "msg");
    message.put("code", "code");
    message.put("number", 111);

    log.info("message->{}", message);

    StreamFactory factory = StreamFactory.newInstance();
    factory.loadResource("mapping-fixedLength2.xml");
    StringWriter stringWriter = new StringWriter();
    BeanWriter beanWriter = factory.createWriter("message", stringWriter);
    beanWriter.write(message);
    String messageString = stringWriter.toString();
    log.info("messageString->{}", messageString);

    StringReader reader = new StringReader(messageString);
    BeanReader beanReader = factory.createReader("message", reader);
    Map<String, Object> message2 =  (Map<String, Object>)beanReader.read();
    log.info("{}", message2);
  }

  @Test
  void testFixedLength4() {
    Message2 message = Message2.builder()
        .code("code")
        .number(1234)
        .value("avafeafafaf")
        .build();

    log.info("message->{}", message);

    StreamFactory factory = StreamFactory.newInstance();
    factory.loadResource("mapping-fixedLength2.xml");
    StringWriter stringWriter = new StringWriter();
    BeanWriter beanWriter = factory.createWriter("message", stringWriter);
    beanWriter.write("message01", message);
    String messageString = stringWriter.toString();
    log.info("messageString->{}", messageString);

    StringReader reader = new StringReader(messageString);
    BeanReader beanReader = factory.createReader("message", reader);
    Map<String, Object> message2 =  (Map<String, Object>)beanReader.read();
    log.info("{}", message2);
  }


}
