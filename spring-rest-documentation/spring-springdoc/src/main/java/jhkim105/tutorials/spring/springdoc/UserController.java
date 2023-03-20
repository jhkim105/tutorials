package jhkim105.tutorials.spring.springdoc;


import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {


  @Parameter(name="X-TOKEN")
  @GetMapping("/{id}")
  public User findById(@PathVariable("id") final String id, @RequestHeader(name = "X-TOKEN") String token) {
    log.info("header token: {}", token);
    return new User(id);
  }



  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public User create(CreateRequest createRequest, @RequestPart(required = false) MultipartFile file) {
    User user = createRequest.toUser();
    if (file != null && !file.isEmpty()) {
      log.info("file: {}", file.getOriginalFilename());
      user.setFileName(file.getOriginalFilename());
    }
    return user;
  }

  @Getter
  @Setter
  @ToString
  @ParameterObject
  private static class CreateRequest {

    private String username;

    public User toUser() {
      return new User(username);
    }
  }

}
