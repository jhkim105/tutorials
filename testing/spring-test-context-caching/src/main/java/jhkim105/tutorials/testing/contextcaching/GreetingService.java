package jhkim105.tutorials.testing.contextcaching;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {

  @Value("${service.greeting}")
  private String greeting;
  public String greet() {
    return greeting;
  }



}
