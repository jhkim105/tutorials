package jhkim105.tutorials.spring.mqtt.concurrency.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class RedisConfig {

  @Bean
  public RedissonClient redissonClient(Environment env) {
    String host = env.getProperty("spring.redis.host");
    Integer port = env.getProperty("spring.redis.port", Integer.class);

    Config config = new Config();
    SingleServerConfig singleServerConfig = config.useSingleServer();
    singleServerConfig
        .setTimeout(10000)
        .setAddress(String.format("redis://%s:%s", host, port));

    return Redisson.create(config);
  }
}
