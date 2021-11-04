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
    return result;
  }

  @GetMapping("/set")
  public Map set(int maxInactiveInterval, HttpSession session) {
    session.setMaxInactiveInterval(maxInactiveInterval);
    Map<String, Object> result = new HashMap<>();
    result.put("sessionId", session.getId());
    result.put("maxInactiveInterval", session.getMaxInactiveInterval());
    return result;
  }

}
