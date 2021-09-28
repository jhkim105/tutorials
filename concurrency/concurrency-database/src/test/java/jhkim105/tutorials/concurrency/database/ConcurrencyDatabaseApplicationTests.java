package jhkim105.tutorials.concurrency.database;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConcurrencyDatabaseApplicationTests {

  @Autowired
  NoticeRepository noticeRepository;

  private String noticeId;

  private final int execCount = 100;

  @BeforeEach
  void beforeEach() {
    Notice notice = Notice.builder().title("test").build();
    noticeRepository.save(notice);
    noticeId = notice.getId();
  }

  @Test
  void testByParallelStream() {
    IntStream.range(0, execCount).parallel().forEach(
        n -> doSomething()
    );

    Notice notice = noticeRepository.findById(noticeId).get();
    Assertions.assertThat(notice.getCount()).isEqualTo(execCount);
  }

  @Test
  void testByExecutorService() throws InterruptedException {
    ExecutorService service = Executors.newFixedThreadPool(execCount);
    CountDownLatch countDownLatch = new CountDownLatch(execCount);
    for (int i = 0; i < execCount; i++) {
      service.submit(() -> {
        doSomething();
        countDownLatch.countDown();
      });
    }
    countDownLatch.await();
    Notice notice = noticeRepository.findById(noticeId).get();
    Assertions.assertThat(notice.getCount()).isEqualTo(execCount);
  }

  @Test
  void testByForkJoinPool() throws InterruptedException {
    ForkJoinPool forkJoinPool = new ForkJoinPool(execCount);
    CountDownLatch countDownLatch = new CountDownLatch(execCount);
    for (int i = 0; i < execCount; i++) {
      forkJoinPool.submit(() -> {
        doSomething();
        countDownLatch.countDown();
      });
    }
    countDownLatch.await();
    Notice notice = noticeRepository.findById(noticeId).get();
    Assertions.assertThat(notice.getCount()).isEqualTo(execCount);
  }

  private void doSomething() {
    noticeRepository.increaseCount(noticeId);
  }

  @AfterEach
  void afterEach() {
    noticeRepository.deleteById(noticeId);
  }

}
