package com.example.spring.cloud.zookeeper.consumer;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class HomeController {

  private final DiscoveryClient discoveryClient;


  private static final String SERVICE_NAME = "hello";

  @GetMapping("/")
  public String home() {
    ResponseEntity<String> response = new RestTemplate().getForEntity(serviceUrl(), String.class);
    return response.getBody();
  }

  private String serviceUrl() {
    List<ServiceInstance> list = discoveryClient.getInstances(SERVICE_NAME);
    if (list == null || list.isEmpty()) {
      throw new IllegalStateException("ServiceInstance not found.");
    }

    return list.get(0).getUri().toString();
  }
}
