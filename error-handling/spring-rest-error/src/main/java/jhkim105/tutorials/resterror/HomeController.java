package jhkim105.tutorials.resterror;

import jhkim105.tutorials.resterror.error.ErrorCode;
import jhkim105.tutorials.resterror.exception.BusinessException;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {


  @GetMapping("/500")
  public void internalServerError() {
    throw new IllegalStateException("Error.");
  }

  @GetMapping("/400")
  public void badRequestError() throws Exception {
    throw new BadRequestException("Error.");
  }

  @GetMapping("/business-error")
  public void businessError() {
    throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
  }


}
