package utils.crypto;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class AesTest {


  @Test
  void aes256() throws Exception{
    String key = "abcdefghijklmnop1234567890123456";
    Aes aes = new Aes(key, 32);
    String dec = "target/enc.txt";
    String originalString = "1234567890";
    byte[] encrypts = aes.encrypt(originalString.getBytes(StandardCharsets.UTF_8));
    Files.write(Paths.get(dec), encrypts);
    encrypts = Files.readAllBytes(Paths.get(dec));

    String decryptString = new String(aes.decrypt(encrypts));
    Assertions.assertThat(originalString).isEqualTo(decryptString);
  }

  @Test
  void aes128() throws Exception{
    String key = "abcdefghijklmnop";
    Aes aes = new Aes(key, 16);
    String dec = "target/enc_128.txt";
    String originalString = "1234567890";
    byte[] encrypts = aes.encrypt(originalString.getBytes(StandardCharsets.UTF_8));
    Files.write(Paths.get(dec), encrypts);
    encrypts = Files.readAllBytes(Paths.get(dec));

    String decryptString = new String(aes.decrypt(encrypts));
    Assertions.assertThat(originalString).isEqualTo(decryptString);
  }

}