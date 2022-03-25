package jhkim105.tutorials.aws.encryption.sdk;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class CryptoTest {

  @Autowired
  Crypto crypto;

  @Test
  void test() {
    String original = "01011112222";
    String encrypt = crypto.encrypt(original);
    log.info(encrypt);
    String decrypt = crypto.decrypt(encrypt);
    Assertions.assertThat(decrypt).isEqualTo(original);
  }
}