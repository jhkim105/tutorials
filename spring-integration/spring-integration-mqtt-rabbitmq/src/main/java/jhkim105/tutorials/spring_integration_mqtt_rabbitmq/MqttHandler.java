package jhkim105.tutorials.spring_integration_mqtt_rabbitmq;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MqttHandler {

  @Autowired
  private RedissonClient redissonClient;

  public void handle(String message) {
    executeOnce(message);
  }

  private void executeOnce(String message) {
    RAtomicLong atomicLong = redissonClient.getAtomicLong(message);
    long currentValue = atomicLong.addAndGet(1);
    log.debug("currentValue:{}", currentValue);
    if (currentValue == 1) {
      // do once
      log.debug(message);
    }

    // if get a unique key, this is not required.
    if (atomicLong.remainTimeToLive() < 0) {
      atomicLong.expire(3, TimeUnit.SECONDS);
    }
  }

  private void executeSynchronized(String message) {
    RLock lock = redissonClient.getLock(message);
    lock.lock(10, TimeUnit.SECONDS);
    try {
      boolean locked = lock.tryLock(10, 10, TimeUnit.SECONDS);
      log.debug("locked:", locked);
      if (locked) {
        // do once at a time
        log.debug(message);
      }
      Thread.sleep(5000);
      lock.unlock();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
