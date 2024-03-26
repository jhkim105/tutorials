package jhkim105.tutorials;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class Scheduler {

  @Scheduled(cron = "${scheduler.scheduledTask}")
  @SchedulerLock(name = "scheduledTask", lockAtMostFor = "9s", lockAtLeastFor = "9s")
  public void scheduledTask() {
    // To assert that the lock is held (prevents misconfiguration errors)
    LockAssert.assertLocked();
    log.info("scheduledTask executed. {}", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
  }

}
