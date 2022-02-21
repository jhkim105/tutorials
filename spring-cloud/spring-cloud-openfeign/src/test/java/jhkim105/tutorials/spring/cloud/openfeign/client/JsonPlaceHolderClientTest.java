package jhkim105.tutorials.spring.cloud.openfeign.client;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class JsonPlaceHolderClientTest {

  @Autowired
  private JsonPlaceHolderClient jsonPlaceHolderClient;

  @Test
  void posts() {
    List<Post> list = jsonPlaceHolderClient.getPosts();
    log.info("{}", list);
  }
  @Test
  void post() {
    Post post = jsonPlaceHolderClient.getPost("3");
    log.info("{}", post);
  }
}