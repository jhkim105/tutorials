package jhkim105.tutorials.s3;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import software.amazon.awssdk.services.s3.S3Client;

@SpringBootTest
@Slf4j
class S3Tests {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    S3Client s3Client;


    @Test
    void testResource() {
        String objectUrl = "s3://rtm-test/1.txt";
        Resource resource = resourceLoader.getResource(objectUrl);
        log.debug("{}", resource.exists());
    }



}
