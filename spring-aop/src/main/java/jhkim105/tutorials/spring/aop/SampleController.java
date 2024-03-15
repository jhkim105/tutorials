package jhkim105.tutorials.spring.aop;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/sample")
@RestController
public class SampleController {


  @PostMapping
  public SampleResponse save(@RequestBody SampleRequest sampleRequest) {
    return new SampleResponse(sampleRequest.getName());
  }



  @Getter
  @Setter
  @ToString
  public static class SampleRequest implements Logging {
    private String name;
  }



  public record SampleResponse(String name) {

  }
}
