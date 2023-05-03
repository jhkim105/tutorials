package jhkim105.tutorials.spring.security.form_login.security;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class ApplicationSessionEventListener implements ApplicationListener<HttpSessionDestroyedEvent>  {

  @Override
  public void onApplicationEvent(HttpSessionDestroyedEvent event) {
    SecurityContext securityContext = (SecurityContext)event.getSession().getAttribute(SPRING_SECURITY_CONTEXT_KEY);
    log.info("securityContext: {}", securityContext);
  }
}
