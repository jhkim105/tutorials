package jhkim105.tutorials.distributed_lock;

import java.time.Duration;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockConfiguration;
import net.javacrumbs.shedlock.core.LockingTaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShedLockCounter {


  private final LockingTaskExecutor lockingTaskExecutor;

  private final Counter counter = new Counter();

  /**
   * 잠금에 실패하면 실행안함
   */
  public void increaseUsingShedLock() {
    lockingTaskExecutor.executeWithLock((Runnable) counter::increase, lockConfiguration());
  }

  private LockConfiguration lockConfiguration() {
    String lockKey = "counter_shedlock";
    Instant now = Instant.now();
    Duration lockAtMostFor = Duration.ofMillis(30);
    Duration lockAtLeastFor = Duration.ofMillis(10);
    return new LockConfiguration(now, lockKey, lockAtMostFor, lockAtLeastFor);
  }

}
