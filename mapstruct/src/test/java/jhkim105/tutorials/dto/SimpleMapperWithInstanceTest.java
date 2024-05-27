package jhkim105.tutorials.dto;

import org.junit.jupiter.api.Test;

class SimpleMapperWithInstanceTest {

  @Test
  void test() {
    System.out.println(SimpleMapperWithInstance.INSTANCE.sourceToDestination(new SimpleSource("name 1", "desc 1")));
  }

}