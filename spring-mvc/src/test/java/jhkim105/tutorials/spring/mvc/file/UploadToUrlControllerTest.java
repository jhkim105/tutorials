package jhkim105.tutorials.spring.mvc.file;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import jhkim105.tutorials.spring.mvc.AppProperties;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest
@AutoConfigureMockMvc
@Disabled
class UploadToUrlControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AppProperties appProperties;


  @Test
  void uploadFile() throws Exception {
    MockMultipartFile multipartFile = new MockMultipartFile( "file", "input.pdf", MediaType.MULTIPART_FORM_DATA_VALUE,
        Files.readAllBytes(Paths.get(appProperties.getStoragePath(), "files", "input.pdf")));

    URI uri = UriComponentsBuilder.fromUriString("/uploadToUrl/file").queryParam("url", "http://docimage.remotemeeting.com/upload").build().toUri();
    mockMvc.perform(multipart(uri).file(multipartFile))
        .andExpect(status().isOk());
  }

  @Test
  void uploadStream() throws Exception {
    MockMultipartFile multipartFile = new MockMultipartFile( "file", "input.pdf", MediaType.MULTIPART_FORM_DATA_VALUE,
        Files.readAllBytes(Paths.get(appProperties.getStoragePath(), "files", "input.pdf")));

    URI uri = UriComponentsBuilder.fromUriString("/uploadToUrl/stream").queryParam("url", "http://docimage.remotemeeting.com/upload").build().toUri();
    mockMvc.perform(multipart(uri).file(multipartFile))
        .andExpect(status().isOk());
  }
}