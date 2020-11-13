package com.example.mockserver;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/callback")
@Slf4j
public class CallbackController {

  @Autowired
  private RestTemplate restTemplate;

  @GetMapping
  public ResponseEntity<CallbackResponse> callback(String callback) {
    ResponseEntity<CallbackResponse> responseEntity = restTemplate.postForEntity(callback, CallbackRequest.builder().message("this is Callback").build(), CallbackResponse.class);
    log.debug("response->{}", responseEntity.getBody());
    return responseEntity;
  }


  @Getter
  @ToString
  public static class CallbackRequest {
    private String message;
    private LocalDateTime createdAt;

    @Builder
    public CallbackRequest(String message) {
      this.message = message;
      this.createdAt = LocalDateTime.now();
    }
  }

  @Getter
  @ToString
  @NoArgsConstructor
  public static class CallbackResponse {
    private String code;
    private String message;

    @Builder
    public CallbackResponse(String code, String message) {
      this.code = code;
      this.message = message;
    }
  }

}
