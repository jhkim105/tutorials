package com.example.beanio;

import com.example.beanio.example.Body;
import com.example.beanio.example.Header;
import com.example.beanio.example.Message;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import lombok.extern.slf4j.Slf4j;
import org.beanio.BeanReader;
import org.beanio.BeanWriter;
import org.beanio.StreamFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class FixedLengthTest {

  @Autowired
  StreamFactory factory;

  @Test
  void testFixedLength() throws IOException {
    Message message = Message.builder()
        .header(Header.builder()
            .size(20)
            .token("token")
            .build())
        .body(Body.builder()
            .msg("msg")
//            .code("code")
            .number(21)
            .build())
        .build();

    log.info("message->{}", message);
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

}
