package com.example.demo;

import com.example.demo.auth.AuthenticationTokenUtil.AuthUser;
import com.example.demo.auth.SecurityUtils;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@Slf4j
public class LoginController {

  @Autowired
  private OAuth2AuthorizedClientService authorizedClientService;

  @GetMapping("/oauth-login")
  public String oauthLogin(Model model) {
    return "oauth-login";
  }

  @GetMapping("/oauth-login2")
  public String oauthLogin2(Model model) {
    log.debug("login2");
    return "oauth-login";
  }

  @GetMapping("/login-success")
  public String loginSuccess(Model model, OAuth2AuthenticationToken authentication) {
    OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
    String userInfoEndpointUri = client.getClientRegistration()
        .getProviderDetails()
        .getUserInfoEndpoint()
        .getUri();

    if (StringUtils.hasText(userInfoEndpointUri)) {
      RestTemplate restTemplate = new RestTemplate();
      HttpHeaders headers = new HttpHeaders();
      headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
          .getTokenValue());

      HttpEntity<String> entity = new HttpEntity<>("", headers);

      ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
      log.debug("responseBody->{}", response.getBody());
      Map userAttributes = response.getBody();
      model.addAttribute("name", userAttributes.get("name"));
    }

    return "login-success";
  }

  @GetMapping("/me")
  @ResponseBody
  public AuthUser me() {
    return SecurityUtils.getAuthUser();
  }
}
