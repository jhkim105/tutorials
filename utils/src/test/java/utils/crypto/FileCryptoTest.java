package utils.crypto;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


@Slf4j
class FileCryptoTest {


  @Test
  void test() throws Exception {
    String originalContent = "abc123 z";
    String encFilePath = "target/baz.enc";
    SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey(); // key size 128
    log.debug("key size: {}", secretKey.getEncoded().length * 8);
    FileCrypto fileCrypto = new FileCrypto(secretKey, "AES/CBC/PKCS5Padding");
    fileCrypto.encrypt(originalContent, encFilePath);

    String decryptedContent = fileCrypto.decrypt(encFilePath);
    Assertions.assertThat(decryptedContent).isEqualTo(originalContent);
  }

  @Test
  void test_256() throws Exception {
    String originalContent = "abc123 z";
    String encFilePath = "target/baz.enc";
    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
    keyGenerator.init(256);
    SecretKey secretKey = keyGenerator.generateKey();
    log.debug("key size: {}", secretKey.getEncoded().length * 8);
    FileCrypto fileCrypto = new FileCrypto(secretKey, "AES/CBC/PKCS5Padding");
    fileCrypto.encrypt(originalContent, encFilePath);

    String decryptedContent = fileCrypto.decrypt(encFilePath);
    Assertions.assertThat(decryptedContent).isEqualTo(originalContent);
  }

  @Test
  void testWithKey() throws Exception {
    String originalContent = "abc123 z";
    String encFilePath = "target/testWithKey.txt";
    SecretKey secretKey = AesUtils.generateKey("abcdefghijklmnop1234567890123456", 32);

    FileCrypto fileCrypto = new FileCrypto(secretKey, "AES/CBC/PKCS5Padding");
    fileCrypto.encrypt(originalContent, encFilePath);

    String decryptedContent = fileCrypto.decrypt(encFilePath);
    Assertions.assertThat(decryptedContent).isEqualTo(originalContent);

  }
}