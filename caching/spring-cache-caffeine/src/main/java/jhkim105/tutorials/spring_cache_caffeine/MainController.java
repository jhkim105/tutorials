package jhkim105.tutorials.spring_cache_caffeine;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class MainController {

  private final DateService dateService;

  @GetMapping
  public String get() {
    return dateService.getDateString("mm.ss.SSS");
  }



}
