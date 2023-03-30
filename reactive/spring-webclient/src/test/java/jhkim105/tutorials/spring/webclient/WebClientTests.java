package jhkim105.tutorials.spring.webclient;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URI;
import java.util.concurrent.CountDownLatch;
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

  @Test
  void post_error() {
    String url = "http://httpbin.org";

    LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();
    linkedMultiValueMap.add("a", "1");

    Mono<String> response = WebClient.builder().baseUrl(url).build()
        .post()
        .uri("/post2")
        .body(BodyInserters.fromMultipartData(linkedMultiValueMap))
        .retrieve()
        .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(IllegalStateException::new))
        .bodyToMono(String.class);


    assertThrows(IllegalStateException.class, () -> {
      response.block();
    });

  }

  private WebClient webClient(String url) {
    return WebClient.builder()
        .baseUrl(url)
        .build();
  }

  @Test
  void file() {
    WebClient webClient = WebClient.create("http://httpbin.org");
    MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
        "text/plain", "Spring Framework".getBytes());
    MultipartBodyBuilder builder = new MultipartBodyBuilder();
    builder.part("file", multipartFile.getResource());

    Mono<String> res = webClient
        .post()
        .uri("/post")
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(BodyInserters.fromMultipartData(builder.build()))
        .exchangeToMono(response -> response.bodyToMono(String.class));
     log.info(res.block());

  }

  @Test
  void exceptionHandling() throws InterruptedException {
    Mono<String> response = WebClient.create("http://localhost:8080").get()
        .retrieve()
        .onStatus(
            httpStatus -> httpStatus != HttpStatus.OK,
            clientResponse ->
                clientResponse.createException()
                    .flatMap(it -> Mono.error(new RuntimeException("status: " + clientResponse.statusCode())))
        )
        .bodyToMono(String.class)
        .onErrorResume(throwable -> Mono.error(new RuntimeException(throwable)));


    CountDownLatch cdl = new CountDownLatch(1);
    response
        .doOnTerminate(cdl::countDown)
        .subscribe(log::info, e -> log.warn("Error: " + e));
    cdl.await();
  }
}
