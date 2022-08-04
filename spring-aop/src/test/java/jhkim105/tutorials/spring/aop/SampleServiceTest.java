package jhkim105.tutorials.spring.aop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class SampleServiceTest {

  @Autowired
  SampleService myService;

  @Test
  void process() throws InterruptedException {
    myService.process(Sample.builder()
            .id("id01")
          .build());

    myService.process("id999");
    myService.processAsync("id998");

  }
}