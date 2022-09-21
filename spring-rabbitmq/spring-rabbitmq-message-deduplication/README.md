메시지 중복 제거하기
===================
RabbitMQ Message Deduplication Plugin 을 설치하고, x-deduplication-header 에 식별자를 보내면 중복된 메시지는 큐에 전송되지 않음. 
3.8.9 이하 버전에서는 지원하지 않음

## 플러그인 설치
[RabbitMQ Message Deduplication Plugin](https://github.com/noxdafox/rabbitmq-message-deduplication/blob/master/README.md)


## Queue 생성
`x-message-deduplication` true
```java
  @Bean
  Queue deduplicateQueue() {
    return QueueBuilder
        .durable(QUEUE_DD)
        .autoDelete()
        .withArgument("x-message-deduplication", true)
        .build();
  }
```
## Message headers
`x-deduplication-header` 에 식별자 설정
```java
  public void sendMessage(String message) {
    Message m = MessageBuilder
        .withBody(message.getBytes(StandardCharsets.UTF_8))
        .build();

    m.getMessageProperties().setHeader("x-deduplication-header", message.hashCode());
    rabbitTemplate.send(AmqpConfig.QUEUE_DD, m);
  }
```

## Tests
queue 에 메시지가 있는 경우에만 메시지가 중복으로 쌓이지 않는다. 메시지가 비워진 후에는 중복을 알 수 없다.  

Mirrored queue 는 지원하지 않는다고 되어 있는데 HA-Mode 에서도 동작 안하는 것인지는 확인해봐야 겠다.

## Refs
https://github.com/noxdafox/rabbitmq-message-deduplication/blob/master/README.md

