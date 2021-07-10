package com.example.demo.master;

import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("multitenancy.mtapp.master.datasource")
@Getter
@ToString
public class MasterDatabaseProperties {
  private String url;
  private String username;
  private String password;
  private String driverClassName;
  private long connectionTimeout;
  private int maxPoolSize;
  private long idleTimeout;
  private int minIdle;
  private String poolName;
}
