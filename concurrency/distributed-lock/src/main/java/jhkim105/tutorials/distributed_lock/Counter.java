package jhkim105.tutorials.distributed_lock;

import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Counter {

  @Getter
  private int count;

  public void increase() {
    int before = count;
    sleep();
    count++;
    log.info("s: {}, e: {}, t: {}", before, count, Thread.currentThread().getName());
  }

  @SneakyThrows
  private void sleep() {
    TimeUnit.MILLISECONDS.sleep(100);
  }



}
