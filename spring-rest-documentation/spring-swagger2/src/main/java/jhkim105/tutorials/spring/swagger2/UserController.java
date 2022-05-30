package jhkim105.tutorials.spring.swagger2;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @GetMapping("/{id}")
  public User findById(@PathVariable("id") final String id) {
    return new User(id);
  }

}
