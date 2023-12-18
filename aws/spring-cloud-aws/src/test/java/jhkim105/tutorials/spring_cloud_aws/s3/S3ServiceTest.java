package jhkim105.tutorials.spring_cloud_aws.s3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class S3ServiceTest {

  @Autowired
  private S3Service s3Service;

  @Test
  void testDownload() {
    String objectUrl = "s3://rtm-test/1.txt";
    String tempDir = "target/tmp/s3";

    File downloadedFile = s3Service.download(objectUrl, tempDir);

    assertTrue(downloadedFile.exists());

  }

}