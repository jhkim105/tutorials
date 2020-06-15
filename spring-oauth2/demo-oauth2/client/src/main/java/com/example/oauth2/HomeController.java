package com.example.oauth2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class HomeController {

  @RequestMapping("/")
  public String home(Model model) {
    model.addAttribute("messages", "Welcome! this is OAuth client.");
    return "index";
  }
}
