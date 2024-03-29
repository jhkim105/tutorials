package jhkim105.tutorials.spring.jasypt;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class SpringJasyptApplicationTests {

  @Autowired
  StringEncryptor stringEncryptor;

  @Test
  void encrypt() {
    log.info("{}", stringEncryptor.encrypt("1234"));
  }

  @Test
  void decrypt() {
    log.info("{}", stringEncryptor.decrypt("VCr9a+qfrOy50aheLoseCKmZ/YeDZzMBQwk7UPUoUr6PISgiW+VXvF+a6yBB2vzn"));
  }
}
