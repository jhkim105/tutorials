package jhkim105.tutorials.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class SimpleMapperUsingInjectServiceTest {

  @Autowired
  SimpleMapperUsingInjectService mapper;


  @Test
  void test() {
    var destination = mapper.sourceToDestination(new SimpleSource("name 123", "dest 123"));
    assertThat(destination.name()).isEqualTo("NAME 123");
  }


}