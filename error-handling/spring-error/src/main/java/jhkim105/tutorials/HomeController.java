package jhkim105.tutorials;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class HomeController {


  @GetMapping("/")
  public void home() {
    throw new IllegalStateException("Error");
  }

  @GetMapping("/bad-request")
  public void badRequestError() throws Exception{
    // error trace 안함
    throw new BadRequestException("Error");
  }



}
