
## Overview
@Async 를 사용하여 스프링에서 비동기 메소드 실행하기


## @EnableAsync, @Async
configuration 에 @EnableAsync 를 추가하고, 대상 메소드에 @Async 를 추가한다.
```java
@Configuration
@EnableAsync
public class AsyncConfig { ... }
```

```java
@Async
public void logDateAsync() {
  log.info(getDate() + " - " + Thread.currentThread().getName());
}
```


## Executor
별도 지정하지 않으면 기본적으로 SimpleAsyncTaskExecutor 가 사용되는데, 비동기 작업마다 새로운 스레드를 생성하므로 성능을 고려한다면 Executor 를 지정하는 것이 좋다.
### Executor 정의하기
- bean 으로 지정
  - 복수로 설정하고 @Async("threadPoolTaskExecutor") 처럼 Executor 를 선택할 수 있다
```java
  @Bean(name = "threadPoolTaskExecutor")
  public Executor threadPoolTaskExecutor() {
    return new ThreadPoolTaskExecutor();
  }
```

- AsyncConfigurer 구현하기
```java@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

  @Override
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
    threadPoolTaskExecutor.initialize();
    return threadPoolTaskExecutor;
  }
}

```

- Spring Boot properties
```text
spring.task.execution.xxx
```


## Exception Handling
리턴타입이 void 인 경우, 예외가 전파되지 않으므로 예외처리를 위한 핸들러를 지정해줘야 한다.
```java
  @Override
  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    return (ex, method, params) -> {
      log.error(String.format("Exception: method=%s, message=%s", method, ex.getMessage()), ex);
    };
  }
```


