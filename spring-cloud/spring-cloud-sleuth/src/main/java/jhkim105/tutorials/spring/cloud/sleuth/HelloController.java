package jhkim105.tutorials.spring.cloud.sleuth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class HelloController {

  private final HelloService helloService;

  @GetMapping("/")
  public String home() {
    log.debug("Hello Sleuth");
    return "success";
  }

  @GetMapping("/async")
  public String async() throws InterruptedException {
    log.debug("Before async method call");
    helloService.doSomethingAsync();
    log.debug("After async method call");
    return "success";
  }
}
