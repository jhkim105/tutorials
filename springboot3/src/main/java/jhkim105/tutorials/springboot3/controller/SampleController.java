package jhkim105.tutorials.springboot3.controller;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/samples")
public class SampleController {

  @GetMapping
  public List<Sample> get() {
    return Arrays.asList(
        new Sample("id01", "name01"),
        new Sample("id02", "name02"));
  }

  @Getter
  @RequiredArgsConstructor
  public static class Sample {
    private final String id;
    private final String name;
  }
}
