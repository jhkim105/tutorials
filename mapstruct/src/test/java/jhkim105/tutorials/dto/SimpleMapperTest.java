package jhkim105.tutorials.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class SimpleMapperTest {

  @Autowired
  SimpleMapper mapper;


  @Test
  void test() {
    System.out.println(mapper.sourceToDestination(new SimpleSource("name 1", "desc 1")));
  }
}