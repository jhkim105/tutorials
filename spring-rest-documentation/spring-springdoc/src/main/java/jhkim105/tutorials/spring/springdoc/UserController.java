package jhkim105.tutorials.spring.springdoc;


import lombok.Getter;
import lombok.ToString;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @GetMapping("/{id}")
  public User findById(@PathVariable("id") final String id) {
    return new User(id);
  }



  @PostMapping
  public User create(CreateRequest createRequest) {
    return createRequest.toUser();
  }

  @Getter
  @ToString
  @ParameterObject
  private static class CreateRequest {
    private String username;

    public User toUser() {
      return new User(username);
    }
  }

}
