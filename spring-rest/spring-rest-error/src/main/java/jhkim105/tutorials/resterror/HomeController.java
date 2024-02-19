package jhkim105.tutorials.resterror;

import jhkim105.tutorials.resterror.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {


  @GetMapping("/")
  public void handle() {
    throw new ResourceNotFoundException();
  }

}
