package utils.crypto;

import java.io.File;
import org.junit.jupiter.api.Test;

class AesFileCryptoTest {

  @Test
  void test() {
    String secret = "abc";
    AesFileCryptor aesFileCrypto = new AesFileCryptor(secret);
    aesFileCrypto.encrypt(new File("src/test/resources/input.txt"), new File("target/enc.txt"));
    aesFileCrypto.decrypt(new File("target/enc.txt"), new File("target/dec.txt"));
  }

}