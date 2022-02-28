package com.example.spring.cloud.zookeeper.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudZookeeperConsumerApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringCloudZookeeperConsumerApplication.class, args);
  }

}
