package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {


  @GetMapping("/google")
  public String google() {
    return "google";
  }

  @GetMapping("/google2")
  public String google2() {
    return "google2";
  }

}
