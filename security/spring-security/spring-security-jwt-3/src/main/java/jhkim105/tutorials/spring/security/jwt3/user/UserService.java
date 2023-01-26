package jhkim105.tutorials.spring.security.jwt3.user;

import java.time.ZonedDateTime;
import java.util.Optional;
import jhkim105.tutorials.spring.security.jwt3.security.JwtAuthenticationTokenService;
import jhkim105.tutorials.spring.security.jwt3.security.SecurityUtils;
import jhkim105.tutorials.spring.security.jwt3.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtAuthenticationTokenService jwtAuthenticationTokenService;
  private final AuthenticationManager authenticationManager;

  @Transactional(readOnly = true)
  public LoginResponse login(LoginRequest loginRequest) {
    UsernamePasswordAuthenticationToken token = loginRequest.toUsernamePasswordAuthenticationToken();
    Authentication authentication = authenticationManager.authenticate(token);
    UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();

    ZonedDateTime today = ZonedDateTime.now();
    String authToken = jwtAuthenticationTokenService.generateToken(userPrincipal);
    String refreshToken = jwtAuthenticationTokenService.generateRefreshToken(userPrincipal);
    return LoginResponse.builder().authToken(authToken).refreshToken(refreshToken).build();
  }

  @Transactional(readOnly = true)
  public User getCurrentUser() {
    String userId = SecurityUtils.getAuthUser().getId();
    Optional<User> optionalUser = userRepository.findById(userId);
    optionalUser.orElseThrow(RuntimeException::new);
    return optionalUser.get();
  }

  @Transactional
  public User save(User user) {
    return userRepository.save(user);
  }
}
