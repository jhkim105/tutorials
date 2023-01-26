package jhkim105.tutorials.spring.session.redis;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
@Slf4j
public class SessionController {
  @GetMapping
  public Map get(HttpSession session) {
    log.debug("{}", session);
    session.getMaxInactiveInterval();
    Map<String, Object> result = new HashMap<>();
    result.put("sessionId", session.getId());
    result.put("maxInactiveInterval", session.getMaxInactiveInterval());
    result.put("lastAccessedTime", session.getLastAccessedTime());
    result.put("user", session.getAttribute("user"));
    return result;
  }

  @GetMapping("/set")
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
