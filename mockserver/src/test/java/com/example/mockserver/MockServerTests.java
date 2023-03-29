package com.example.mockserver;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {8888})
@RequiredArgsConstructor
@Slf4j
class MockServerTests {

  private final ClientAndServer mockServer;
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void test() throws Exception {
    Map<String, Object> resMap = new HashMap<>();
    resMap.put("code", "100");
    resMap.put("message", "return message");

    int delaySeconds = 3;
    mockServer
        .when(
            request()
                .withMethod("POST")
                .withPath("/mock_post")
        )
        .respond(
            response()
                .withBody(objectMapper.writeValueAsString(resMap))
                .withContentType(org.mockserver.model.MediaType.APPLICATION_JSON)
                .withDelay(TimeUnit.SECONDS, delaySeconds)
        );

    WebClient webClient = WebClient.create("http://localhost:8888");

    Mono<String> responseMono = webClient.post()
        .uri("/mock_post")
        .contentType(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(String.class);

    String expected = objectMapper.writeValueAsString(resMap);
    StepVerifier.create(responseMono)
        .expectSubscription()
        .expectNoEvent(Duration.ofSeconds(delaySeconds))
        .expectNextMatches(response -> {
          log.info("response: {}", response);
          return response.equals(expected);})
        .verifyComplete();

  }

}