package jhkim105.tutorials.spring.mvc.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
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
    return Sample.builder().id(id).name("name").build();
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

  @Getter
  @ToString
  @Builder
  public static class Sample {
    private String id;
    private String name;
  }

}
