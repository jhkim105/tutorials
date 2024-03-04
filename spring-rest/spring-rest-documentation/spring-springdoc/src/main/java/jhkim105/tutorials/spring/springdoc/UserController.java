package jhkim105.tutorials.spring.springdoc;


import static jhkim105.tutorials.spring.springdoc.SwaggerConfig.SWAGGER_SECURITY_SCHEME_KEY;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
@Slf4j
@Tag(name = "User")
public class UserController {


  @GetMapping("/{id}")
  @SecurityRequirement(name = SWAGGER_SECURITY_SCHEME_KEY)
  public User findById(@PathVariable("id") final String id) {
    return new User(id);
  }


  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public User create(@ModelAttribute CreateRequest createRequest) {
    User user = createRequest.toUser();
    if (createRequest.file != null && !createRequest.file.isEmpty()) {
      log.info("file: {}", createRequest.file.getOriginalFilename());
      user.setFileName(createRequest.file.getOriginalFilename());
    }
    return user;
  }

  @Getter
  @Setter
  @ToString
  public static class CreateRequest {

    private String username;

    private MultipartFile file;
    public User toUser() {
      return new User(username);
    }
  }

}
