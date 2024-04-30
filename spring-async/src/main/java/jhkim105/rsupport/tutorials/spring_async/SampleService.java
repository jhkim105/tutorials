package jhkim105.rsupport.tutorials.spring_async;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SampleService {


  @Async
  public void logDateAsync() {
    log.info(getDate() + " - " + Thread.currentThread().getName());
  }

  public String getDate() {
    return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
  }

  @Async
  public void throwError() {
    log.info("throwError");
    throw new RuntimeException("Error!");
  }


  @Async
  public Future<String> getDateAsync() {
    return CompletableFuture.completedFuture((getDate() + " - " + Thread.currentThread().getName()));
  }

}
