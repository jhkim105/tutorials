


## ShedLock 
To use ShedLock, you do the following
- Enable and configure Scheduled locking
- Annotate your scheduled tasks
- Configure a Lock Provider

### Setup
- Dependencies
```xml
<dependency>
    <groupId>net.javacrumbs.shedlock</groupId>
    <artifactId>shedlock-spring</artifactId>
    <version>5.12.0</version>
</dependency>
<dependency>
  <groupId>net.javacrumbs.shedlock</groupId>
  <artifactId>shedlock-provider-redis-spring</artifactId>
  <version>5.12.0</version>
</dependency>
```

- Config
```java
@Configuration
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "10m")
public class SchedulerConfig {

}
```
- LockProvider
```java
@Bean
public LockProvider lockProvider(RedisConnectionFactory connectionFactory) {
    return new RedisLockProvider(connectionFactory);
}
```

- Annotate scheduled tasks
```java
@Scheduled(cron = "0/10 * * * * *")
@SchedulerLock(name = "scheduledTask", lockAtMostFor = "9s", lockAtLeastFor = "9s")
public void scheduledTask() {
  // To assert that the lock is held (prevents misconfiguration errors)
  LockAssert.assertLocked();
  log.info("scheduledTask executed. {}", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
}
```
## References
[ShedLock](https://github.com/lukas-krecan/ShedLock)

