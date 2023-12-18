package jhkim105.tutorials.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("rm")
@Slf4j
class S3Tests {


  @Autowired
  private AmazonS3 s3;


  String bucketName = "rtm-test";
  String objectKey = "1.txt";



  @Test
  void download() {
    try {
      S3Object o = s3.getObject(bucketName, objectKey);
      S3ObjectInputStream s3is = o.getObjectContent();
      byte[] bytes = IOUtils.toByteArray(s3is);
      log.info(new String(bytes));
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }



}