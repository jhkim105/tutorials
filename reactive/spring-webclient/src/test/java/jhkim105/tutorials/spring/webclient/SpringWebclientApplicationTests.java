package jhkim105.tutorials.spring.webclient;

import java.util.concurrent.CountDownLatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootTest
@Slf4j
class SpringWebclientApplicationTests {

  @Autowired
  WebClient webClient;

  @Test
  @Disabled
  void subscribe() throws InterruptedException {
    CountDownLatch cdl = new CountDownLatch(1);
    Mono<String> response = webClient.get()
        .uri("http://localhost:8080")
        .retrieve()
        .bodyToMono(String.class);

    response
        .doOnTerminate(() -> cdl.countDown())
        .subscribe(log::info);
    cdl.await();
  }

}
