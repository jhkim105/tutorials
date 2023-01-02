package jhkim105.tutorials.spring.actuator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloWorldController {

  @GetMapping("hello-world")
  public Greeting greeting() {
    return new Greeting("id01", "Hello World");
  }


  @Getter
  @RequiredArgsConstructor
  public static class Greeting {
    private final String id;
    private final String content;
  }

}
