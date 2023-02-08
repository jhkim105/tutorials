package jhkim105.tutorials.keycloak_spring;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthInfoController {

  @GetMapping("/auth-info")
  public Map<String, Object> userInfo(Model model, KeycloakAuthenticationToken keycloakAuthenticationToken, @AuthenticationPrincipal KeycloakPrincipal keycloakPrincipal) {
    Map<String, Object> result = new HashMap<>();
    result.put("username", keycloakAuthenticationToken.getName());
    result.put("authorities", keycloakAuthenticationToken.getAuthorities());

    KeycloakSecurityContext keycloakSecurityContext =
        keycloakPrincipal.getKeycloakSecurityContext();

    log.debug("keycloakSecurityContext->{}", keycloakSecurityContext);
    result.put("idTokenString", keycloakSecurityContext.getIdTokenString());
    result.put("tokenString", keycloakSecurityContext.getTokenString());

    return result;
  }

}
