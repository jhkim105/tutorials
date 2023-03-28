package jhkim105.tutorials.spring.webclient;


import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MainController {

  @GetMapping("/")
  public LocalDateTime get() {
    log.info("home");
    return LocalDateTime.now();
  }
}
