package jhkim105.tutorials.spring.cloud.vault;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SpringCloudVaultApplication implements ApplicationRunner {

  public static void main(String[] args) {
    SpringApplication.run(SpringCloudVaultApplication.class, args);
  }

  @Value("${mykey}")
  String mykey;


  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("mykey:{}", mykey);
  }
}
