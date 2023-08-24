package jhkim105.tutorials.crypto.rsa;

import java.security.Key;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;


@Slf4j
class RSAKeyParserTest {


  @Test
  void privateKey_pkcs1() {
    Key key = RSAKeyParser.loadPrivateKey("rsa-private.pem");
    log.debug(Rsa.convertToKeyString(key));
  }

  @Test
  void privateKey_pkcs8() {
    Key key = RSAKeyParser.loadPrivateKey("rsa-private-pkcs8.pem");
    log.debug(Rsa.convertToKeyString(key));
  }

  @Test
  void publicKey() {
    Key key = RSAKeyParser.loadPublicKey("rsa-public-key.pem");
    log.debug(Rsa.convertToKeyString(key));
  }

  @Test
  void publicKey_pkcs8() {
    Key key = RSAKeyParser.loadPublicKey("rsa-public-key-from-pkcs8.pem");
    log.debug(Rsa.convertToKeyString(key));
  }


}