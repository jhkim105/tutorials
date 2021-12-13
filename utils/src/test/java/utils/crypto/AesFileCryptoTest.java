package utils.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AesFileCryptoTest {

  @Test
  void test() throws IOException {
    String secret = "abc";
    AesFileCryptor aesFileCrypto = new AesFileCryptor(secret);
    aesFileCrypto.encrypt(new File("src/test/resources/input.txt"), new File("target/enc.txt"));
    aesFileCrypto.decrypt(new File("target/enc.txt"), new File("target/dec.txt"));
    Assertions.assertTrue(IOUtils.contentEquals(new FileInputStream(new File("src/test/resources/input.txt")), new FileInputStream(new File("target/dec.txt"))));
  }

}