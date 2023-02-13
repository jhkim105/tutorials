package jhkim105.tutorials.aws.s3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

@SpringBootTest
@Slf4j
class S3ServiceTest {

  @Autowired
  S3Service s3Service;

  String bucketName = "rtm-test";

  @Test
  void upload() throws IOException {
    MockMultipartFile multipartFile = new MockMultipartFile( "file", "1.txt", MediaType.MULTIPART_FORM_DATA_VALUE,
        Files.readAllBytes(Paths.get("src/test/resources/1.txt")));
   log.info("size: {}", multipartFile.getSize());
    s3Service.upload(bucketName, multipartFile.getOriginalFilename(), multipartFile);
  }

  @Test
  void list() {
    log.info("{}", s3Service.getList(bucketName));
  }
}