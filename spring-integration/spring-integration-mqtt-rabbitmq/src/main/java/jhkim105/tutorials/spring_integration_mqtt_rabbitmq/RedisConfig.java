package jhkim105.tutorials.spring_integration_mqtt_rabbitmq;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

  @Bean
  public RedissonClient redissonClient() {
    Config config = new Config();
    config.useSingleServer()
        .setTimeout(10000)
        .setAddress("redis://127.0.0.1:6379");
    RedissonClient client = Redisson.create(config);
    return client;
  }
}
