
ShedLock을 활용하여 멀티플 인스턴스에서 Scheduler 사용하기
===========

## Redis

### dependency
```xml
<!-- https://mvnrepository.com/artifact/net.javacrumbs.shedlock/shedlock-spring -->
<dependency>
  <groupId>net.javacrumbs.shedlock</groupId>
  <artifactId>shedlock-spring</artifactId>
  <version>4.24.0</version>
</dependency>
<!-- https://mvnrepository.com/artifact/net.javacrumbs.shedlock/shedlock-provider-redis-spring -->
<dependency>
  <groupId>net.javacrumbs.shedlock</groupId>
  <artifactId>shedlock-provider-redis-spring</artifactId>
  <version>4.24.0</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.apache.commons/commons-pool2 -->
<dependency>
  <groupId>org.apache.commons</groupId>
  <artifactId>commons-pool2</artifactId>
  <version>2.10.0</version>
</dependency>
<!-- https://mvnrepository.com/artifact/redis.clients/jedis -->
<dependency>
  <groupId>redis.clients</groupId>
  <artifactId>jedis</artifactId>
  <version>3.6.1</version>
</dependency>
```

### configuration
```properties
spring.redis.host=localhost
spring.redis.port=6379
#spring.redis.password=
```

### SchedulerConfig And Schedule
```java
@Configuration
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "30s")
@Slf4j
public class ScheduleConfig {

  private static final int SCHEDULER_POOL_SIZE = 5;

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
```


## References
https://github.com/lukas-krecan/ShedLock#redis-using-spring-redisconnectionfactory