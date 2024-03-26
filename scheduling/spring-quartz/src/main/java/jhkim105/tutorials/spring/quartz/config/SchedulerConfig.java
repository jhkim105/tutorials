package jhkim105.tutorials.spring.quartz.config;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

import jhkim105.tutorials.spring.quartz.scheduler.SampleJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SchedulerConfig {

  @Bean
  public JobDetail jobDetail() {
    return JobBuilder.newJob().ofType(SampleJob.class)
        .storeDurably()
        .withIdentity("quartz_job")
        .withDescription("Sample Job")
        .build();
  }

  @Bean
  public Trigger simpleTrigger(JobDetail job) {
    return TriggerBuilder.newTrigger().forJob(job)
        .withIdentity("trigger1")
        .withDescription("Sample trigger")
        .withSchedule(simpleSchedule().repeatForever().withIntervalInSeconds(60))
        .build();
  }

  @Bean
  public Trigger cronTrigger(JobDetail job) {
    return TriggerBuilder.newTrigger().forJob(job)
        .withIdentity("crontrigger1")
        .withDescription("Sample cron trigger")
        .withSchedule(cronSchedule("0/10 * * * * ?"))
        .build();
  }

}
