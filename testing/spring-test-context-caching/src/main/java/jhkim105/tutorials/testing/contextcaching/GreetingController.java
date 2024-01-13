package jhkim105.tutorials.testing.contextcaching;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
@RequiredArgsConstructor
public class GreetingController {

  private final GreetingService greetingService;

  @GetMapping()
  public String get() {
    return greetingService.greet();
  }


}
