package jhkim105.tutorials.spring.jasypt;


import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class JasyptTest {

  @Resource(name = "stringEncryptor")
  StringEncryptor stringEncryptor;

  @Test
  void encrypt() {
    log.info("{}", stringEncryptor.encrypt("pass1234"));
  }

  @Test
  void decrypt() {
    log.info("{}", stringEncryptor.decrypt("ef0QM5m0w8R9HaQ4y4RqGIAIphZNXwyDwBWm165UdkiC7U3vRJzPbCoEzhD+SnknukrLzsqSzLWXvj5O77fVXQ=="));
  }
}