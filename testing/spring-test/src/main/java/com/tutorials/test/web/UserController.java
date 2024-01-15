package com.tutorials.test.web;


import com.tutorials.test.domain.User;
import com.tutorials.test.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  public ResponseEntity<List<User>> getAll() {
    return ResponseEntity.ok().body(userService.getAll());
  }

  @PostMapping
  public ResponseEntity<User> save(@RequestBody User user) {
    User newUser = userService.save(user);
    return new ResponseEntity<>(newUser, HttpStatus.CREATED);
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<User> getById(@PathVariable String id) {
    return ResponseEntity.ok().body(userService.getById(id));
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable String id) {
    userService.deleteById(id);
    return ResponseEntity.ok().build();
  }

}
