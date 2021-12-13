package utils.crypto;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class AesTest {


  @Test
  /**
   * openssl enc -d -aes-256-cbc -nosalt -K 6162636465666768696a6b6c6d6e6f7031323334353637383930313233343536 -iv 6162636465666768696a6b6c6d6e6f70 -in target/enc.txt -out target/dec-openssl.txt
   */
  void test() throws Exception{
    String key = "abcdefghijklmnop1234567890123456";
    Aes aes = new Aes(key, 32);
    String dec = "target/enc.txt";
    String originalString = "1234567890";
    byte[] encrypts = aes.encryptBytes(originalString);
    Files.write(Paths.get(dec), encrypts);
    encrypts = Files.readAllBytes(Paths.get(dec));

    String decryptString = new String(aes.decryptBytes(encrypts));
    Assertions.assertThat(originalString).isEqualTo(decryptString);
  }

  @Test
  void hex() {
    String key = Hex.encodeHexString("abcdefghijklmnop1234567890123456".getBytes(StandardCharsets.UTF_8)); //6162636465666768696a6b6c6d6e6f7031323334353637383930313233343536
    String iv = Hex.encodeHexString("abcdefghijklmnop".getBytes(StandardCharsets.UTF_8)); //6162636465666768696a6b6c6d6e6f70
    log.debug(key);
  }
}