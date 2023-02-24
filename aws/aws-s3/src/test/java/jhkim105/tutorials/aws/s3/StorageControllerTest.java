package jhkim105.tutorials.aws.s3;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@Slf4j
class StorageControllerTest {

  @Autowired
  MockMvc mockMvc;

  String bucketName = "test-bucket-002";
  String sourceFilePath = "src/test/resources/1.txt";
  String objectKey = "c/1.txt";

  @Test
  @Order(1)
  void createBucket() throws Exception {
    mockMvc.perform(post("/" + bucketName))
        .andDo(print());
  }

  @Test
  @Order(2)
  void upload() throws Exception {
    MockMultipartFile multipartFile =
        new MockMultipartFile("file", "1.txt", MediaType.TEXT_PLAIN_VALUE,
            Files.newInputStream(Paths.get(sourceFilePath)));
    mockMvc.perform(
          multipart(String.format("/%s/%s", bucketName, objectKey))
            .file(multipartFile))
        .andExpect(status().isOk());
  }

  @Test
  @Order(3)
  void download() throws Exception {
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders
            .get(String.format("/%s/%s", bucketName, objectKey))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.IMAGE_JPEG));

    resultActions
        .andExpect(status().isOk());

    File downloadFile = new File("target/1.txt");
    FileUtils.writeByteArrayToFile(downloadFile, resultActions.andReturn().getResponse().getContentAsByteArray());

    IOUtils.contentEquals(new FileInputStream(sourceFilePath), new FileInputStream(downloadFile));
  }

  @Test
  @Order(4)
  void deleteObject() throws Exception {
    mockMvc.perform(delete(String.format("/%s/%s", bucketName, objectKey)))
        .andDo(print());
  }

  @Test
  @Order(5)
  void deleteBucket() throws Exception  {
    mockMvc.perform(delete(String.format("/%s", bucketName)))
        .andDo(print());
  }

}