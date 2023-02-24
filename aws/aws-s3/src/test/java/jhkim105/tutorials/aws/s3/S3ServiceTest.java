package jhkim105.tutorials.aws.s3;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

@SpringBootTest
@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class S3ServiceTest {

  @Autowired
  S3Service s3Service;

  String bucketName = "test-bucket-001";
  String objectKey = "a/1.txt";

  @Test
  @Order(1)
  void createBucket() {
    s3Service.createBucket(bucketName);
  }

  @Test
  @Order(2)
  void listBucket() {
    assertThat(s3Service.getBucketNameList().contains(bucketName)).isTrue();

  }

  @Test
  @Order(3)
  void upload() throws IOException {
    MockMultipartFile multipartFile =
        new MockMultipartFile("file", "1.txt", MediaType.TEXT_PLAIN_VALUE,
            Files.newInputStream(Paths.get("src/test/resources/1.txt")));
    log.info("size: {}", multipartFile.getSize());
    s3Service.upload(bucketName, "a/1.txt", multipartFile);
  }

  @Test
  @Order(4)
  void getObjectKeyList() {
    assertThat(s3Service.getObjectKeyList(bucketName).get(0)).isEqualTo(objectKey);
  }

  @Test
  @Order(5)
  void deleteObject() {
    s3Service.deleteObject(bucketName, objectKey);
    assertThat(s3Service.getObjectKeyList(bucketName)).isEmpty();
  }


  @Test
  @Order(6)
  void deleteBucket() {
    s3Service.deleteBucket(bucketName);
    assertThat(s3Service.getBucketNameList().contains(bucketName)).isFalse();
  }

}