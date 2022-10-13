package jhkim105.tutorials.spring.mqtt.concurrency.service;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SttLogMqttHandler {
  private final RedissonClient redissonClient;

  @ServiceActivator
  public SttLogMessage handle(SttLogMessage message) {
    SttLogMessage.Data data = message.getData();
    String key = String.format("%s_%s", data.getConferenceId(), data.getSeq());

    RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
    if (atomicLong.remainTimeToLive() < 0) {
      atomicLong.expire(10, TimeUnit.SECONDS);
    }

    if (atomicLong.compareAndSet(0, 1)) {
      return message;
    }

    return new SttLogMessage();
  }
}
