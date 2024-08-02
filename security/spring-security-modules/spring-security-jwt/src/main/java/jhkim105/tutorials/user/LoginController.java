package jhkim105.tutorials.user;

import jhkim105.tutorials.jwt.JwtService;
import jhkim105.tutorials.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final JwtService jwtService;

  @PostMapping
  public LoginResponse login(@RequestBody LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    User user = userService.getByUsername(userDetails.getUsername());
    String authToken = jwtService.issueToken(new UserPrincipal(user));
    return new LoginResponse(authToken);
  }


}
