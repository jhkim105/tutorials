package utils.crypto;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

public class EncryptorsTest {

  @Test
  void test() {
    String original = "original1234";
    String password = "pass1234";
    String salt = KeyGenerators.string().generateKey(); // must be hex-character
    TextEncryptor encryptor = Encryptors.text(password, salt);
    String enc = encryptor.encrypt(original);
    String dec = encryptor.decrypt(enc);
    Assertions.assertThat(dec).isEqualTo(original);
  }

}
