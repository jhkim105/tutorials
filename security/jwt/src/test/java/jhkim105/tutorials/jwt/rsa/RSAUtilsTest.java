package jhkim105.tutorials.jwt.rsa;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class RSAUtilsTest {


  @Autowired
  RSAUtils rsaUtils;


  @Test
  void jwks() {
    log.debug(rsaUtils.jwks());
  }

  @Test
  void getPublicKeyPEM() throws IOException {
    log.debug(rsaUtils.getPublicKeyPEM());
  }


}