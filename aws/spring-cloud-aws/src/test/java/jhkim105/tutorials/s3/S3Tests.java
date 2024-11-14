package jhkim105.tutorials.s3;


import java.io.File;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@SpringBootTest
@Slf4j
@Disabled
class S3Tests {

  @Autowired
  ResourceLoader resourceLoader;

  @Autowired
  S3Client s3;


  @Test
  void testResource() {
    String objectUrl = "s3://rtm-test/1.txt";
    Resource resource = resourceLoader.getResource(objectUrl);
    log.debug("{}", resource.exists());
  }

  @Test
  void upload() {
    String filePath = "/Users/jihwankim/Documents/large/129m.dmg";
    String bucketName = "rtm-test";
    String objectKey = "129m.dmg";
    File file = Paths.get(filePath).toFile();
    log.info("Upload object: {}", objectKey);
    s3.putObject(PutObjectRequest.builder()
        .bucket(bucketName)
        .key(objectKey)
        .build(), RequestBody.fromFile(file));

    log.info("Uploaded object: {}", objectKey);

  }


}
