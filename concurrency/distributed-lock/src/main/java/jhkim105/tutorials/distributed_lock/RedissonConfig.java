package jhkim105.tutorials.distributed_lock;

import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RedissonConfig {

  private final RedisProperties redisProperties;

  @Bean(destroyMethod = "shutdown")
  public RedissonClient redissonClient() {
    String host = redisProperties.getHost();
    int port = redisProperties.getPort();
    String password = redisProperties.getPassword();

    Config config = new Config();
    SingleServerConfig singleServerConfig = config.useSingleServer();
    if (password != null && !password.isBlank()) {
      singleServerConfig.setPassword(password);
    }
    singleServerConfig
        .setTimeout(10000)
        .setAddress(String.format("redis://%s:%s", host, port));

    return Redisson.create(config);
  }
}
