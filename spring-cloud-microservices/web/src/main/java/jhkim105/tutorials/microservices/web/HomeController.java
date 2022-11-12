package jhkim105.tutorials.microservices.web;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class HomeController {
  @GetMapping("/home")
  public String home() {
    log.info("/home");
    return "home";
  }

  @GetMapping("/")
  @ResponseBody
  public String main(HttpServletRequest request) {
    return String.format("severName: %s, serverPort:%s, remoteAddr: %s",
        request.getServerName(),
        request.getServerPort(),
        request.getRemoteAddr());
  }
}
