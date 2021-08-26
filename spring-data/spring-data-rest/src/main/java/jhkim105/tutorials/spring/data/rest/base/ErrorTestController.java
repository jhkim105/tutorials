package jhkim105.tutorials.spring.data.rest.base;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorTestController {
  @GetMapping("/error/test")
  public ResponseEntity<?> error() {
    throw new BaseException(ErrorCodes.FAIL);
  }
}
