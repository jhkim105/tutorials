package jhkim105.tutorials.resource_server.opaque;

import java.util.Map;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthinfoController {

  @GetMapping("/authinfo")
  public Map<String, Object> authinfo(BearerTokenAuthentication authentication) {
    return authentication.getTokenAttributes();
  }

  @GetMapping("/authinfo2")
  public Map<String, Object> authinfo2(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
    return principal.getAttributes();
  }

}
