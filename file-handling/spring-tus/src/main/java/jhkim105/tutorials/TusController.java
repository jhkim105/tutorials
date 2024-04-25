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
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.desair.tus.server.TusFileUploadService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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


  @GetMapping("/download/{tusId}")
  @ResponseBody
  public ResponseEntity<Resource> download(@PathVariable String tusId) throws Exception {

    var uploadInfo = uploadService.getUploadInfo(tusId);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
            .filename(uploadInfo.getFileName(), StandardCharsets.UTF_8)
            .build().toString())
        .header(HttpHeaders.CONTENT_TYPE, uploadInfo.getFileMimeType())
        .body(new InputStreamResource(uploadService.getUploadedBytes(tusId)));
  }

}
