package com.example.demo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "30s")
@Slf4j
public class ScheduleConfig {


  private static final int SCHEDULER_POOL_SIZE = 5;


  /**
   *
   * pool size나 threadName을 지정하고 싶다면
   * 아래 bean을 선언하여 사용
   */
//  @Bean
//  public ThreadPoolTaskScheduler taskScheduler() {
//    ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
//    taskScheduler.setPoolSize(SCHEDULER_POOL_SIZE);
//    taskScheduler.setThreadNamePrefix("scheduling-");
//    return taskScheduler;
//  }

  @Bean
  public LockProvider lockProvider(RedisConnectionFactory connectionFactory) {
    return new RedisLockProvider(connectionFactory);
  }


  @Scheduled(cron = "0/10 * * * * *")
  @SchedulerLock(name = "scheduledTaskName", lockAtLeastFor = "5s", lockAtMostFor = "9s")
  public void scheduledTask() {
    log.info("scheduledTask executed. {}", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
  }
}
