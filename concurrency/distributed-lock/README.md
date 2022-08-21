Distributed Lock
===================


## Distributed Lock Using ShedLock (Redis)
### Dependencies
```xml
    <dependency>
      <groupId>net.javacrumbs.shedlock</groupId>
      <artifactId>shedlock-provider-redis-spring</artifactId>
      <version>4.41.0</version>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>

```

### Configure
```java
@Configuration
public class ShedLockConfig {

  @Bean
  public LockProvider lockProvider(RedisConnectionFactory connectionFactory) {
    return new RedisLockProvider(connectionFactory);
  }

  @Bean
  public LockingTaskExecutor lockingTaskExecutor(LockProvider lockProvider) {
    LockingTaskExecutor lockingTaskExecutor = new DefaultLockingTaskExecutor(lockProvider);
    return lockingTaskExecutor;
  }
}
```

### Example
잠금시간 동안 한번만 실행을 보장한다. 잠금이 있을 경우 실행되지 않음.
```java
  public void increaseUsingShedLock() {
    lockingTaskExecutor.executeWithLock((Runnable) this::increase, lockConfiguration());
  }

  private LockConfiguration lockConfiguration() {
    String lockKey = "counter";
    Instant now = Instant.now();
    Duration lockAtMostFor = Duration.ofMillis(3);
    Duration lockAtLeastFor = Duration.ofMillis(1);
    return new LockConfiguration(now, lockKey, lockAtMostFor, lockAtLeastFor);
  }
```

## Distributed Lock Using Redisson

### Dependencies
```xml
    <dependency>
      <groupId>org.redisson</groupId>
      <artifactId>redisson</artifactId>
      <version>3.12.1</version>
    </dependency>
```


### Example
잠금을 얻지 못할 경우 실행안함.
```java
  public void increaseUsingRedissonLock() {
    String key = "counter";
    RLock lock = redissonClient.getLock(key);
    try {
      boolean res = lock.tryLock(100, 10, TimeUnit.MILLISECONDS);
      if (res) {
        try {
          increase();
        } finally {
          lock.unlock();
        }
      }
    } catch (InterruptedException e) {
      throw new IllegalStateException(e);
    }
  }
```
순서대로 모두 실행
```java
  public void increaseSynchronizedUsingRedissonLock() {
    String key = "counter";
    RLock lock = redissonClient.getLock(key);
    lock.lock();
    increase();
    lock.unlock();
  }
```


## Refs
https://github.com/lukas-krecan/ShedLock#locking-without-a-framework
https://github.com/redisson/redisson/wiki/8.-Distributed-locks-and-synchronizers
