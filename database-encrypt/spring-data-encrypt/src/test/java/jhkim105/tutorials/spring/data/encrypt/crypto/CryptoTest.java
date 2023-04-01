package jhkim105.tutorials.spring.data.encrypt.crypto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class CryptoTest {

  @Autowired
  Crypto crypto;

  @Test
  void test() {
    String dec = crypto.encrypt("abcd");

  }
}