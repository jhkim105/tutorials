package com.example.google.auth;

import com.example.google.auth.AuthenticationTokenUtil.AuthUser;
import com.example.google.user.User;
import com.example.google.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository userRepository;
  private final AuthenticationTokenUtil authenticationTokenUtil;

  @Transactional(readOnly = true)
  public String generateToken(String email) {
    User user = userRepository.findByEmail(email);
    AuthUser authUser = AuthUser.of(user);
    return authenticationTokenUtil.generateToken(authUser);
  }


}
