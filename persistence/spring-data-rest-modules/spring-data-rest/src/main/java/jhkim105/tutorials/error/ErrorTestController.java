package jhkim105.tutorials.error;

import jhkim105.tutorials.base.BaseException;
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
