package com.example.google;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
  @GetMapping("/login")
  public void login() {
  }

  @GetMapping("/")
  public String index() {
    return "index";
  }
}
