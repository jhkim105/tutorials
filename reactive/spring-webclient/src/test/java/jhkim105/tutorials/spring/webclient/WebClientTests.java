package jhkim105.tutorials.spring.webclient;

import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;
import reactor.core.publisher.Mono;


@Slf4j
class WebClientTests {


  @Test
  void get() {
    String url = "http://httpbin.org/get";
    Mono<String> response = webClient(url).get().retrieve().bodyToMono(String.class);
    String result = response.block();
    log.info(result);
  }


  @Test
  void post() {
    String url = "http://httpbin.org/post";
    WebClient webClient = WebClient.create();
    UriSpec<RequestBodySpec> uriSpec = webClient.post();
    RequestBodySpec bodySpec = uriSpec.uri(URI.create(url));
    LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();
    linkedMultiValueMap.add("a", "1");
    RequestHeadersSpec<?> headersSpec =
        bodySpec.body(BodyInserters.fromMultipartData(linkedMultiValueMap));

    Mono<String> response = headersSpec.exchangeToMono(r -> {
      if (r.statusCode().equals(HttpStatus.OK)) {
        return r.bodyToMono(String.class);
      } else if (r.statusCode().is4xxClientError()) {
        return Mono.just("Error response");
      } else {
        return r.createException()
            .flatMap(Mono::error);
      }
    });

    log.info(response.block());
  }


  private WebClient webClient(String url) {
    return WebClient.builder()
        .baseUrl(url)
        .build();
  }

  @Test
  void file() {
    WebClient webClient = WebClient.create();
    String url = "http://httpbin.org/post";

    MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
        "text/plain", "Spring Framework".getBytes());
    MultipartBodyBuilder builder = new MultipartBodyBuilder();
    builder.part("file", multipartFile.getResource());

    Mono<String> res = webClient.post()
        .uri(url)
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(BodyInserters.fromMultipartData(builder.build()))
        .exchangeToMono(response -> {
          return response.bodyToMono(String.class);
        });
     log.info(res.block());

  }

}
