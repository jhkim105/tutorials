package com.example.beanio;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "protocol", ignoreUnknownFields = false)
@Getter
@Setter
public class ProtocolProperties {
  private String mappingFilePath;
}
