package jhkim105.tutorials;


import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.desair.tus.server.TusFileUploadService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/tus")
@Slf4j
public class TusController {


  private final TusFileUploadService uploadService;

  @RequestMapping(value = {"", "/**"},
      method = {POST, PATCH, HEAD, DELETE, OPTIONS, GET})
  public void process(final HttpServletRequest request, final HttpServletResponse response) {
    try {
      uploadService.process(request, response);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    //access response header Location,Upload-Offset,Upload-length
//    response.addHeader("Access-Control-Expose-Headers","Location,Upload-Offset,Upload-Length");
    log.info("done: {}", request.getRequestURI());
  }


}
