package jhkim105.tutorials.spring.security.jwt3.user;

import jhkim105.tutorials.spring.security.jwt3.security.JwtAuthenticationTokenService;
import jhkim105.tutorials.spring.security.jwt3.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

  private final AuthenticationManager authenticationManager;
  private final JwtAuthenticationTokenService jwtAuthenticationTokenService;

  @PostMapping
  public LoginResponse login(@RequestBody LoginRequest loginRequest) {
    UsernamePasswordAuthenticationToken token = loginRequest.toUsernamePasswordAuthenticationToken();
    Authentication authentication = authenticationManager.authenticate(token);
    UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();

    String authToken = jwtAuthenticationTokenService.generateToken(userPrincipal);
    String refreshToken = jwtAuthenticationTokenService.generateRefreshToken(userPrincipal);
    return LoginResponse.builder().authToken(authToken).refreshToken(refreshToken).build();
  }


}
