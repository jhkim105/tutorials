package jhkim105.tutorials.resource_server.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class UserInfoController {

  @GetMapping("/user-info")
  public String userInfo(JwtAuthenticationToken jwtAuthenticationToken, @AuthenticationPrincipal Jwt jwt) {
    log.info("{}", jwt);
    log.info("{}", jwtAuthenticationToken);
    return jwt.getTokenValue();
  }

}
