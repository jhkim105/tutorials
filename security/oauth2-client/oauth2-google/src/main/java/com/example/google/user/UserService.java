package com.example.google.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public User register(RegisterForm registerForm) {
    User user = registerForm.toUser();
    userRepository.save(user);
    return user;
  }
}
