package jhkim105.tutorials;


import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/session")
public class SessionController {

  @GetMapping
  public Map get(HttpSession session) {
    log.info("{}", session);
    Map<String, Object> result = new HashMap<>();
    result.put("sessionId", session.getId());
    result.put("maxInactiveInterval", session.getMaxInactiveInterval());
    result.put("lastAccessedTime", session.getLastAccessedTime());
    result.put("user", session.getAttribute("user"));
    return result;
  }

  @PostMapping
  public Map set(Integer maxInactiveInterval, HttpSession session) {
    if (maxInactiveInterval != null) {
      session.setMaxInactiveInterval(maxInactiveInterval);
    }

    session.setAttribute("user", new User("user01"));

    Map<String, Object> result = new HashMap<>();
    result.put("sessionId", session.getId());
    result.put("maxInactiveInterval", session.getMaxInactiveInterval());
    result.put("user", session.getAttribute("user"));
    return result;
  }

}
