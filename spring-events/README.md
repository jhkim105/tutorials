Spring Events
==================

## Custom Events

### Event
```java
public class CustomSpringEvent extends ApplicationEvent {
    private String message;
    @Getter
    private boolean success;

    public CustomSpringEvent(Object source, String message) {
        super(source);
        this.message = message;
        this.success = true;
    }
    public String getMessage() {
        return message;
    }
}
```

### Publisher
```java
@Component
@RequiredArgsConstructor
@Slf4j
public class CustomSpringEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishCustomEvent(final String message) {
        log.info("Publishing custom event. ");
        CustomSpringEvent customApplicationEvent = new CustomSpringEvent(this, message);
        applicationEventPublisher.publishEvent(customApplicationEvent);
    }
}
```
### Listener
```java
@Component
@Slf4j
public class CustomSpringEventListener implements ApplicationListener<CustomSpringEvent> {
    @Override
    public void onApplicationEvent(CustomSpringEvent event) {
        log.info("Received spring custom event - {}", event.getMessage());
    }
}
```
### Annotation-Driven Event Listener
```java
@Component
@Slf4j
public class CustomAnnotationDrivenEventListener {
    @EventListener
    public void handleEvent(final CustomSpringEvent event) {
        log.info("handleEvent: {}", event.getMessage());
    }

    @EventListener(condition = "#event.success")
    public void handleEventByCondition(final CustomSpringEvent event) {
        log.info("handleEventByCondition: " + event.getMessage());
    }
    

}
```

### Tests
동기로 동작한다.
```java
@SpringBootTest
@Slf4j
class CustomSpringEventTest {

  @Autowired
  private CustomSpringEventPublisher publisher;
  
  @Test
  void event() {
    publisher.publishCustomEvent("Hello world!!");
    log.info("Done publishing synchronous custom event.");
  }

}
```

```shell
2022-07-25 11:23:39.346  INFO 61260 --- [           main] j.t.s.e.s.CustomSpringEventPublisher     : Publishing custom event. 
2022-07-25 11:23:39.346  INFO 61260 --- [           main] j.t.s.e.s.CustomSpringEventListener      : Received spring custom event - Hello world!!
2022-07-25 11:23:39.348  INFO 61260 --- [           main] .e.s.CustomAnnotationDrivenEventListener : handleEvent: Hello world!!
2022-07-25 11:23:39.373  INFO 61260 --- [           main] .e.s.CustomAnnotationDrivenEventListener : handleEventByCondition: Hello world!!
2022-07-25 11:23:39.373  INFO 61260 --- [           main] j.t.s.e.simple.CustomSpringEventTest     : Done publishing synchronous custom event.
```
@Async 를 지정하면 비동기로 동작
```java
  @EventListener
  @Async
  public void handleEvent(final CustomSpringEvent event) {
    log.info("handleEvent: {}", event.getMessage());
  }
```
``shell
2023-12-28 12:07:24.619  INFO 92404 --- [           main] j.t.s.e.s.CustomSpringEventListener      : Received spring custom event - Hello world!!
2023-12-28 12:07:24.628  INFO 92404 --- [         task-1] .e.s.CustomAnnotationDrivenEventListener : handleEvent: Hello world!!
2023-12-28 12:07:24.628  INFO 92404 --- [           main] .e.s.CustomAnnotationDrivenEventListener : handleEvent
``

## Transaction-Bound Events
```java
  @TransactionalEventListener
  public void handleTransactionalEvent(final CustomSpringEvent springEvent) {
    log.info("handleTransactionalEvent: {}", springEvent);
  }
```

다음과 같이 transaction phase 를 지정 가능
- @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
- AFTER_COMMIT(default), AFTER_ROLLBACK, AFTER_COMPLETION, BEFORE_COMMIT

## Refs
https://www.baeldung.com/spring-events