package jhkim105.rsupport.tutorials.spring_async;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class SampleServiceTests {


  @Autowired
  private SampleService sampleService;

  @Test
  void test_no_return() {
    sampleService.logDateAsync();
    sampleService.logDateAsync();
    sampleService.logDateAsync();
    log.info(sampleService.getDate());
  }

  @Test
  void getReturnValue() {
    Future<String> futureResult = sampleService.getDateAsync();
    try {
      String result = futureResult.get();
      log.info("Async result: {}", result);
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void throwError() {
    sampleService.throwError();
  }

}
