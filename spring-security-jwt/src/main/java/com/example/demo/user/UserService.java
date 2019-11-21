package com.example.demo.user;

import com.example.demo.security.AuthUser;
import com.example.demo.security.JwtAuthenticationTokenService;
import com.example.demo.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtAuthenticationTokenService jwtAuthenticationTokenService;

  @Transactional(readOnly = true)
  public LoginResponse login(LoginRequest loginRequest) {
    String username = loginRequest.getUsername();
    String password = loginRequest.getPassword();

    User user = userRepository.findByUsername(loginRequest.getUsername());
    if (user == null) {
      throw new NoSuchElementException(String.format("[%s] not found", username));
    }

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new IllegalArgumentException("password not matched.");
    }
    AuthUser authUser = new AuthUser(user);
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
