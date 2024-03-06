package jhkim105.tutorials.spring.mvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sample")
@Slf4j
public class SampleController {

  @GetMapping("/get")
  public Sample get(String id) {
    return new Sample(id, "name");
  }

  @PostMapping("/post")
  public Sample post(Sample sample) {
    log.info("{}", sample);
    return sample;
  }

  @PostMapping("/postBody")
  public Sample postBody(@RequestBody  Sample sample) {
    log.info("{}", sample);
    return sample;
  }


  @GetMapping("/header")
  public ResponseEntity<Void> header() {
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CACHE_CONTROL, "no-cache");
    headers.add("X-ID", "user01");
    headers.add("X-Name", "홍길동"); // java.lang.IllegalArgumentException: The Unicode character [홍] at code point [54,861] cannot be encoded as it is outside the permitted range of 0 to 255

    return ResponseEntity.noContent()
        .headers(headers)
        .build();
  }


}
