package utils.crypto;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;


@Slf4j
class AesFileCryptoTest {


  @Test
  void encryptAndDecrypt() throws IOException {
    File plain = new File("src/test/resources/input.txt");
    File encrypted = new File("target/enc.txt");
    File decrypted = new File("target/dec.txt");

    String key = "1234567890abcdef";
    AesFileCrypto aesFileCrypto = new AesFileCrypto(key);
    aesFileCrypto.encrypt(plain, encrypted);
    aesFileCrypto.decrypt(encrypted, decrypted);
    assertTrue(IOUtils.contentEquals(Files.newInputStream(plain.toPath()), Files.newInputStream(decrypted.toPath())));
  }


}