Spring Quartz Scheduler
=============================


## Simple Example: in-memory store

### Step 1: Dependency

```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-quartz</artifactId>
    </dependency>
```

### Step 2: Sample Service and Job
SampleService.java
```java
@Service
@Slf4j
public class SampleService {
  public void doSomething() {
    log.info("doSomething start.");
  }
}
```

SampleJob.java
```java
@Component
@RequiredArgsConstructor
public class SampleJob extends QuartzJobBean {

  private final SampleService sampleService;

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    sampleService.doSomething();
  }

}
```

### Step 3: Configuration

SchedulerConfig.java
```java
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
  public Trigger trigger(JobDetail job) {
    return TriggerBuilder.newTrigger().forJob(job)
        .withIdentity("trigger1")
        .withDescription("Sample trigger")
        .withSchedule(simpleSchedule().repeatForever().withIntervalInSeconds(10))
        .build();
  }
  
}
```

Step: Run

```shell
2022-03-12 22:07:29.156  INFO 1595 --- [  restartedMain] o.s.s.quartz.SchedulerFactoryBean        : Starting Quartz Scheduler now
2022-03-12 22:07:29.156  INFO 1595 --- [  restartedMain] org.quartz.core.QuartzScheduler          : Scheduler quartzScheduler_$_NON_CLUSTERED started.
2022-03-12 22:07:29.159  INFO 1595 --- [eduler_Worker-1] j.t.spring.quartz.service.SampleService  : doSomething start.
2022-03-12 22:07:29.163  INFO 1595 --- [  restartedMain] j.t.s.quartz.SpringQuartzApplication     : Started SpringQuartzApplication in 0.847 seconds (JVM running for 1.249)
2022-03-12 22:07:39.016  INFO 1595 --- [eduler_Worker-2] j.t.spring.quartz.service.SampleService  : doSomething start.
2022-03-12 22:07:49.014  INFO 1595 --- [eduler_Worker-3] j.t.spring.quartz.service.SampleService  : doSomething start.
2022-03-12 22:07:59.015  INFO 1595 --- [eduler_Worker-4] j.t.spring.quartz.service.SampleService  : doSomething start.
2022-03-12 22:08:09.015  INFO 1595 --- [eduler_Worker-5] j.t.spring.quartz.service.SampleService  : doSomething start.
```

## JDBC-based store
```yaml
spring:
  quartz:
    job-store-type: jdbc
    jdbc:
      initialize-schema: always
  datasource:
    url: jdbc:mariadb://localhost/demo_quartz?createDatabaseIfNotExist=true
    username: root
    password: 111111
```

## Quartz Properties
```yaml
spring:
  quartz:
    properties:
      "org.quartz.jobStore.useProperties": true
```

jobStore.useProperties=true 일 경우 QRTZ_JOB_DETAILS.JOB_DATA 를 String 으로 저장

## References
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#io.quartz
