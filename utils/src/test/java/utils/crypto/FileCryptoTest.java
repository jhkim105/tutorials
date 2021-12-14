package utils.crypto;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class FileCryptoTest {


  @Test
  void test() throws Exception {
    String originalContent = "abc123 z";
    String encFilePath = "target/baz.enc";
    SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();

    FileCryptor fileCrypto = new FileCryptor(secretKey, "AES/CBC/PKCS5Padding");
    fileCrypto.encrypt(originalContent, encFilePath);

    String decryptedContent = fileCrypto.decrypt(encFilePath);
    Assertions.assertThat(decryptedContent).isEqualTo(originalContent);

  }

  @Test
  void testWithKey() throws Exception {
    String originalContent = "abc123 z";
    String encFilePath = "target/input-enc-256.txt";
    SecretKey secretKey = AesUtils.generateKey("abcdefghijklmnop1234567890123456", 32);

    FileCryptor fileCrypto = new FileCryptor(secretKey, "AES/CBC/PKCS5Padding");
//    fileCrypto.encrypt(originalContent, encFilePath);

    String decryptedContent = fileCrypto.decrypt(encFilePath);
    Assertions.assertThat(decryptedContent).isEqualTo(originalContent);

  }
}