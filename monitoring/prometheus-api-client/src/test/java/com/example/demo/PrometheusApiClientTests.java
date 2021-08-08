package com.example.demo;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
public class PrometheusApiClientTests {


  PrometheusApiClient prometheusApiClient;

  @BeforeEach
  void beforeEach() {
    prometheusApiClient = new PrometheusApiClient();
  }

  @Test
  void getActiveServerList() {
    log.info("{}", prometheusApiClient.activeServerList());
  }
}
