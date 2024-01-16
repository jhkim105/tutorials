package jhkim105.tutorials.spring.mvc.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/url")
public class UrlController {


  @GetMapping("/server-url")
  public String getServerUrl() {
    return MvcUriComponentsBuilder.fromController(this.getClass()).build().toUri().toString();
  }

  @GetMapping("/server-url2")
  public String getServerUrl2(HttpServletRequest request) {
    return ServletUriComponentsBuilder.fromRequest(request).build().toUri().toString();
  }

}
