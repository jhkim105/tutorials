package jhkim105.tutorials.spring.data.encrypt.jasypt;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class EncryptorTests {

  @Autowired
  StringEncryptor stringEncryptor;

  @Test
  void decrypt() {
    assertThat(stringEncryptor.decrypt("6zHyHZxfpTxWd75TbroBYB6D8vQQWpYo")).isEqualTo("01011112222");
  }

}
