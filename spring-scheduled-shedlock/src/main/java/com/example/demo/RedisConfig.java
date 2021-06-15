package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

  private final Environment env;

  @Bean
  public JedisConnectionFactory redisConnectionFactory() {
    String host = env.getProperty("spring.redis.host");
    Integer port = Integer.parseInt(env.getProperty("spring.redis.port"));
    String password = env.getProperty("spring.redis.password");

    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
    if (StringUtils.hasText(password))
      redisStandaloneConfiguration.setPassword(RedisPassword.of(password));

    JedisClientConfiguration.DefaultJedisClientConfigurationBuilder builder = (JedisClientConfiguration.DefaultJedisClientConfigurationBuilder)JedisClientConfiguration
        .builder().usePooling();
    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration, builder.build());
    return jedisConnectionFactory;
  }

  @Bean
  public RedisTemplate<String, String> redisTemplate() {
    RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory());
    return redisTemplate;
  }

}
