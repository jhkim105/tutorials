package com.example.demo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString
@ConfigurationProperties("mqtt")
public class MqttProperties {

  private String brokerUrl;
  private int qos;
  private boolean async;
}
