package jhkim105.tutorials.spring.integration.mqtt;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MqttClients implements InitializingBean {

  private LoadingCache<String, MqttPahoMessageHandler> cache;

  @Override
  public void afterPropertiesSet() {
    cache = Caffeine.newBuilder()
        .expireAfterAccess(10, TimeUnit.SECONDS)
        .removalListener((key, value, cause) -> {
          MqttPahoMessageHandler handler = (MqttPahoMessageHandler)value;
          handler.stop();
          log.info("handler stop. clientId: {}", handler.getClientId());
        })
        .build(url -> {
          MqttPahoMessageHandler handler = new MqttPahoMessageHandler(url,
              clientId(),
              createMqttClientFactory());
          handler.setCompletionTimeout(5000);
          handler.afterPropertiesSet();
          log.info("handler start. clientId: {}", handler.getClientId());
          return handler;
        });

  }

  private String clientId() {
    return UUID.randomUUID().toString();
  }


  private MqttPahoClientFactory createMqttClientFactory() {
    DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
    return factory;
  }


  public void publish(String url, String topic, String messageBody) {
    MqttPahoMessageHandler mqttPahoMessageHandler = get(url);
    Message<String> message = MessageBuilder.withPayload(messageBody)
        .setHeader(MqttHeaders.TOPIC, topic)
        .setHeader(MqttHeaders.QOS, 2)
        .build();

    try {
      mqttPahoMessageHandler.handleMessage(message);
    } catch (Exception e) {
      cache.invalidate(url);
      throw new IllegalStateException(String.format("mqtt publish failed..url:%s, topic:%s", url, topic), e);
    }
  }

  private MqttPahoMessageHandler get(String url) {
    return cache.get(url);
  }

  @Scheduled(fixedRate = 5_000)
  private void cleanUpCache() {
    cache.cleanUp();
  }


}
