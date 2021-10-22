package com.example.multitenant.tenant;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final UserRepository userRepository;


  @GetMapping
  public ResponseEntity<List<User>> getAll() {
    List<User> users = userRepository.findAll();
    return ResponseEntity.ok(users);
  }


}
