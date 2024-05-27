package jhkim105.tutorials.service;


import org.springframework.stereotype.Service;

@Service
public class SimpleService {

  public String uppercase(String name) {
    return name.toUpperCase();
  }
}
