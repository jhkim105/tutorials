package jhkim105.tutorials.exel.poi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class ExcelDownloadControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void download() throws Exception {
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders
            .get("/download")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_OCTET_STREAM));

    resultActions
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM));

    File downloadFile = new File("target/output.xlsx");
    FileUtils.writeByteArrayToFile(downloadFile, resultActions.andReturn().getResponse().getContentAsByteArray());

    List<List<String>> list = ExcelReader.read(new FileInputStream(downloadFile));
    assertThat(list.size()).isEqualTo(4);
  }


}