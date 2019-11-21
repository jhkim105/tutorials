package com.example.demo.user;

import com.example.demo.security.AuthUser;
import com.example.demo.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @PostMapping("/login")
  public LoginResponse login(@RequestBody LoginRequest loginRequest) {
    LoginResponse loginResponse = userService.login(loginRequest);
    return loginResponse;
  }

  @GetMapping("/me")
  public AuthUser me() {
    AuthUser authUser = SecurityUtils.getAuthUser();
    return authUser;
  }


  @PostMapping
  public User save(@RequestBody UserUpdateRequest userUpdateRequest) {
    User currentUser = userService.getCurrentUser();
    userUpdateRequest.applyTo(currentUser);
    return userService.save(currentUser);
  }

}
