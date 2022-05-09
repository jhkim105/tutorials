package jhkim105.tutorials.spring.cloud.microservices.book;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class BookApplication implements ApplicationRunner  {

  private final ServiceProperties serviceProperties;

  public static void main(String[] args) {
    SpringApplication.run(BookApplication.class, args);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("{}", serviceProperties);
  }



}
