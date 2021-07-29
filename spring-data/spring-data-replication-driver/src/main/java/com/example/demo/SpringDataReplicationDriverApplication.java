package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
//@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SpringDataReplicationDriverApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringDataReplicationDriverApplication.class, args);
  }

}
