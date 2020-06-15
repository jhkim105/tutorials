package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserRegisterController {

  private final UserService userService;

  @GetMapping("/register")
  public String registerForm() {
    log.debug("/register");
    return "user/register";
  }

  @PostMapping
  @ResponseBody
  public ResponseEntity register(RegisterForm registerForm) {
    userService.register(registerForm);
    return ResponseEntity.ok().build();
  }

}
