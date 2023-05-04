package jhkim105.tutorials.spring.security.form_login.security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationEventListener {
  @EventListener({AuthenticationSuccessEvent.class, InteractiveAuthenticationSuccessEvent.class})
  public void processLoginEvent(AbstractAuthenticationEvent e) {
    String username = ((UserPrincipal)e.getAuthentication().getPrincipal()).getUsername();
    log.info("username: {}", username);
  }

  @EventListener(LogoutSuccessEvent.class)
  public void processLogoutEvent(LogoutSuccessEvent e) {
    String username = ((UserPrincipal)e.getAuthentication().getPrincipal()).getUsername();
    log.info("username: {}", username);
  }
}
