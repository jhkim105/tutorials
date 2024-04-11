package jhkim105.tutorials;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/samples")
@Slf4j
public class SampleController {

  @GetMapping()
  public List<Sample> getAll() {
    return List.of(Sample.of("name 01"), Sample.of("name 02"));
  }

  @PostMapping
  public Sample post(@RequestBody Sample sample) {
    log.info("{}", sample);
    return sample;
  }


  @GetMapping("/error")
  public void error() {
    throw new IllegalStateException("error");
  }

}
