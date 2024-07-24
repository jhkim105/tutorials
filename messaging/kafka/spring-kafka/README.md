## Application Setup

- Messaging > Spring for Apache Kafka

```xml
<dependency>
  <groupId>org.springframework.kafka</groupId>
  <artifactId>spring-kafka</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.kafka</groupId>
  <artifactId>spring-kafka-test</artifactId>
  <scope>test</scope>
</dependency>        
```

```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
    admin:
      auto-create: false
    consumer:
      group-id: group-01
```

## Producing Messages

```java

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaSender {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ObjectMapper objectMapper;


  public void send(String topic, Object message) {
    kafkaTemplate.send(topic, json(message));
    log.debug("message sent. topic: {}, message: {}", topic, message);
  }

  private String json(Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }


}
```

## Consuming Messages

```java

@Component
@Slf4j
public class SampleMessageListener {

  @KafkaListener(topics = Topics.SAMPLE)
  public void handle(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
    log.debug("message received. message: {}, partition: {}", message, partition);
  }

}

```

## ERROR Handling

- 에러 발생시 재시도 횟수 지정하기 (FixedBackOff)

```java

@Bean
public DefaultErrorHandler defaultErrorHandler() {
  return new DefaultErrorHandler(new FixedBackOff(0, 2));
}
```

## Log Compaction
- cleanup.policy=compact
- 메시지 전송시 메시지 키를 같이 보내야 한다
- 각 키에 대한 최신값을 유지, 이전 값은 삭제 (log compaction) -> 공간 절약
- https://kafka.apache.org/documentation/#compaction
- ![log-compaction.png](log-compaction.png)
## References

- [Spring Boot Kafka](https://docs.spring.io/spring-boot/docs/current/reference/html/messaging.html#messaging.kafka)
- [Baeldung](https://www.baeldung.com/spring-kafka) 
- [Apache Kafka](https://kafka.apache.org/documentation/#gettingStarted) 

