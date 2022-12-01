package jhkim105.tutorials.spring.mvc;

import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequiredArgsConstructor
public class ProxyController {

  private final RestTemplate restTemplate;

  @GetMapping("/proxy")
  public void proxy(String url, HttpServletResponse response) {

    restTemplate.execute(
        url,
        HttpMethod.GET,
        null,
        clientHttpResponse -> {
          StreamUtils.copy(clientHttpResponse.getBody(), response.getOutputStream());
          return null;
        }
    );
  }

}
