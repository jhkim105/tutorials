package jhkim105.tutorials.spring.security.form_login.security;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

@Component
@WebListener
@Slf4j
public class HttpSessionDestroyedListener implements HttpSessionListener {

  @Override
  public void sessionDestroyed(HttpSessionEvent event) {
    HttpSession session = event.getSession();

    SecurityContext securityContext = (SecurityContext)session.getAttribute(SPRING_SECURITY_CONTEXT_KEY);
    log.info("securityContext-> {}", securityContext);
  }

}
