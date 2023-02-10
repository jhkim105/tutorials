package jhkim105.tutorials.guava.cache;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class MainController {

  private final SampleCache sampleCache;

  @GetMapping
  public String get() {
    return sampleCache.get("mm.ss.SSS");
  }


}
