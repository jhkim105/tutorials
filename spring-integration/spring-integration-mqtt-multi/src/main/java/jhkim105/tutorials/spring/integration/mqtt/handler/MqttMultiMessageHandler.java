package jhkim105.tutorials.spring.integration.mqtt.handler;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.management.ManageableLifecycle;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;

@RequiredArgsConstructor
@Slf4j
public class MqttMultiMessageHandler extends AbstractMessageHandler implements ManageableLifecycle {

  public static final String MQTT_OUTBOUND_BROKER_URL = MqttHeaders.PREFIX + "outboundBrokerUrl";

  private final AtomicBoolean running = new AtomicBoolean();
  private final Map<String, MessageHandler> mqttHandlers = new ConcurrentHashMap<>();

  private final List<String> brokerUrls;
  private final MqttPahoClientFactory mqttClientFactory;

  @Override
  protected void handleMessageInternal(Message<?> message) {
    if (mqttHandlers.isEmpty()) {
      throw new RuntimeException("There is no registered handler");
    }

    String mqttOutboundBrokerUrl = message.getHeaders().get(MQTT_OUTBOUND_BROKER_URL, String.class);
    CustomMqttPahoMessageHandler messageHandler = (CustomMqttPahoMessageHandler) mqttHandlers.get(mqttOutboundBrokerUrl);
    if (messageHandler == null) {
      throw new RuntimeException(String.format("There is no registered handler. url : %s", mqttOutboundBrokerUrl));
    }

    messageHandler.handleMessageInternal(message);
  }

  @Override
  public void start() {
    if (!running.getAndSet(true)) {
      doStart();
    }
  }

  private void doStart() {
    initializeHandlers();

    log.info("finish doStart, current handler count : {}", mqttHandlers.size());
    mqttHandlers.keySet().forEach(k -> log.info("registered mqttOutboundBrokerUrl : {}", k));
  }

  private void initializeHandlers() {
    brokerUrls.forEach(brokerUrl -> mqttHandlers.put(brokerUrl, createHandler(brokerUrl)));
  }

  private MessageHandler createHandler(String brokerUrl) {
    String clientId = UUID.randomUUID().toString();

    CustomMqttPahoMessageHandler messageHandler = new CustomMqttPahoMessageHandler(brokerUrl, clientId, mqttClientFactory);
    messageHandler.setCompletionTimeout(5000);
    messageHandler.afterPropertiesSet();
    return messageHandler;
  }

  @Override
  public void stop() {
    if (running.getAndSet(false)) {
      mqttHandlers.forEach((k, v) -> ((CustomMqttPahoMessageHandler) v).doStop());
    }
  }

  @Override
  public boolean isRunning() {
    return running.get();
  }


  private static class CustomMqttPahoMessageHandler extends MqttPahoMessageHandler {

    public CustomMqttPahoMessageHandler(String brokerUrl, String clientId, MqttPahoClientFactory clientFactory) {
      super(brokerUrl, clientId, clientFactory);
    }

    @Override
    public void doStop() {
      super.doStop();
    }

    @Override
    public void handleMessageInternal(Message<?> message) {
      super.handleMessageInternal(message);
    }

    @Override
    public void onInit() {
      try {
        super.onInit();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

  }

}
