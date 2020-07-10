package com.example.demo.user;

import com.example.demo.security.AuthUser;
import com.example.demo.security.JwtAuthenticationTokenService;
import com.example.demo.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

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
    AuthUser authUser = (AuthUser)authentication.getPrincipal();

    ZonedDateTime today = ZonedDateTime.now();
    String authToken = jwtAuthenticationTokenService.generateToken(authUser);
    String refreshToken = jwtAuthenticationTokenService.generateRefreshToken(authUser);
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
