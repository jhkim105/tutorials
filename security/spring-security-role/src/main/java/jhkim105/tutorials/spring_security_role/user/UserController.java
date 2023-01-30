package jhkim105.tutorials.spring_security_role.user;

import jhkim105.tutorials.spring_security_role.security.SecurityUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {


  @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
  public UserDetails me() {
    return SecurityUtils.getCurrentUserDetails();
  }


}
