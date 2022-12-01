package jhkim105.tutorials.spring.files;


import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Paths;
import java.util.stream.Stream;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;



@AutoConfigureMockMvc
@SpringBootTest
class UploadControllerTests {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private StorageService storageService;

  @Test
  void shouldListAllFiles() throws Exception {
    given(this.storageService.loadAll())
        .willReturn(Stream.of(Paths.get("first.txt"), Paths.get("second.txt")));

    this.mvc.perform(get("/")).andExpect(status().isOk())
        .andExpect(model().attribute("files",
            Matchers.contains("http://localhost/first.txt",
                "http://localhost/second.txt")));
  }

  @Test
  void shouldSaveUploadedFile() throws Exception {
    MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
        "text/plain", "Spring Framework".getBytes());
    this.mvc.perform(multipart("/").file(multipartFile).param("key", "test123"))
        .andExpect(status().isFound())
        .andExpect(header().string("Location", "/"));

    then(this.storageService).should().store(multipartFile, "test123");
  }

  @SuppressWarnings("unchecked")
  @Test
  void should404WhenMissingFile() throws Exception {
    given(this.storageService.loadAsResource("test.txt"))
        .willThrow(StorageFileNotFoundException.class);

    this.mvc.perform(get("/test.txt")).andExpect(status().isNotFound());
  }

}