package com.tutorials.test.service;


import com.tutorials.test.domain.User;
import com.tutorials.test.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public List<User> getAll() {
    return userRepository.findAll();
  }

  public User getById(String id) {
    return userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("user not found"));
  }

  public User save(User user) {
    return userRepository.save(user);
  }

  public void deleteById(String id) {
    userRepository.deleteById(id);
  }
}
