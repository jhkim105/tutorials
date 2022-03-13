package jhkim105.tutorials.spring.quartz;

import static org.quartz.CronScheduleBuilder.cronSchedule;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled
@Slf4j
public class SchedulerTests {
  @Autowired
  Scheduler scheduler;

  @Test
  void getJobDetail() throws SchedulerException {
    JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey("quartz_job"));
    log.info("{}", jobDetail);

  }

  @Test
  void pauseJob() throws SchedulerException {
    scheduler.pauseJob(JobKey.jobKey("quartz_job"));
  }

  @Test
  void resumeJob() throws SchedulerException {
    scheduler.resumeJob(JobKey.jobKey("quartz_job"));
  }

  @Test
  void reschedule() throws SchedulerException {
    scheduler.rescheduleJob(TriggerKey.triggerKey("crontrigger1"), TriggerBuilder.newTrigger()
        .withIdentity("crontrigger1")
        .withDescription("Sample cron trigger")
        .withSchedule(cronSchedule("0/20 * * * * ?"))
        .build());
  }
}
