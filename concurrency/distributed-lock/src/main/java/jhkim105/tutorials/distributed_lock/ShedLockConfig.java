package jhkim105.tutorials.distributed_lock;

import net.javacrumbs.shedlock.core.DefaultLockingTaskExecutor;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.core.LockingTaskExecutor;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

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
