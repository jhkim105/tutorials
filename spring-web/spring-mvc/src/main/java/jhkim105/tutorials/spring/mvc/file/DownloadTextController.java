package jhkim105.tutorials.spring.mvc.file;

import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/download/text")
public class DownloadTextController {

  @GetMapping
  public void downloadText(HttpServletResponse response) {
    String contents = "aafafafaf\n가나다";
    String filename = "test가나다.txt";

    download(filename, contents, response);
  }

  private static void download(String filename, String contents, HttpServletResponse response) {
    try {
      InputStream in = new ByteArrayInputStream(contents.getBytes(Charset.forName("UTF-8")));

      response.setContentType("application/octet-stream");
      response.setHeader("Content-Disposition", String.format("attachment;  filename*=UTF-8''%s", URLEncoder.encode(filename, "UTF-8")));
      OutputStream out = response.getOutputStream();

      FileCopyUtils.copy(in, out);
      out.flush();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

}
