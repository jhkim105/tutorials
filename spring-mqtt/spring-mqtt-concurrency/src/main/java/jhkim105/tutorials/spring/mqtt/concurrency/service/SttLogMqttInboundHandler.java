package jhkim105.tutorials.spring.mqtt.concurrency.service;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SttLogMqttInboundHandler {
  private final RedissonClient redissonClient;

  @ServiceActivator
  public SttLogMessage handle(SttLogMessage message) {
    if (message.isNotValid()) {
      log.debug("Invalid Message. {}", message);
      return message;
    }

    SttLogMessage.Data data = message.getData();
    String key = String.format("%s_%s", data.getConferenceId(), data.getSeq());
    RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
    log.debug("[{}] value: {}, remainTimeToLive: {}", key, atomicLong.get(), atomicLong.remainTimeToLive());
    if (atomicLong.compareAndSet(0, 1)) {
      log.info("[{}] OK", key);
      atomicLong.expire(5, TimeUnit.SECONDS);
      return message;
    }

    return message;
  }

}
