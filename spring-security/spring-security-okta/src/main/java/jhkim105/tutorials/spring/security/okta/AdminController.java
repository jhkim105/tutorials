package jhkim105.tutorials.spring.security.okta;

import com.okta.sdk.client.Client;
import com.okta.sdk.resource.user.UserList;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

  private final Client client;

  @GetMapping("/users")
  public UserList getUsers() {
    return client.listUsers();
  }
}
