package jhkim105.tutorials.redisson.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisKeyExpiredEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisKeyExpireEventListener {

  @EventListener
  public void keyExpiredEvent(RedisKeyExpiredEvent<?> event) {
    String id = new String(event.getId());
    log.info("expired. id: {}", id);
  }


}
