package jhkim105.tutorials.resource_server.opaque;

import java.util.Map;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserInfoController {

  @GetMapping("/user-info")
  public Map<String, Object> userInfo(BearerTokenAuthentication authentication) {
    return authentication.getTokenAttributes();
  }

  @GetMapping("/user-info2")
  public Map<String, Object> userInfo2(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
    return principal.getAttributes();
  }

}
