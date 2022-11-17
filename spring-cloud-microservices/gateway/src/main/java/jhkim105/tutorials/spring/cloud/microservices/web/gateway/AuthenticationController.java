package jhkim105.tutorials.spring.cloud.microservices.web.gateway;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthenticationController {

  @RequestMapping("/me")
  public String greeting(@AuthenticationPrincipal OidcUser oidcUser, Model model,
      @RegisteredOAuth2AuthorizedClient("keycloak") OAuth2AuthorizedClient client) {
    model.addAttribute("username", client.getPrincipalName());
    model.addAttribute("idToken", oidcUser.getIdToken().getTokenValue());
    model.addAttribute("accessToken", client.getAccessToken().getTokenValue());

    return "me";
  }

}
