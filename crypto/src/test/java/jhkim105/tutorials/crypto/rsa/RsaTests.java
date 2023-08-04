package jhkim105.tutorials.crypto.rsa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;


@Slf4j
class RsaTests {


  @Test
  void rsa() throws Exception {
    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
    generator.initialize(2048);
    KeyPair pair = generator.generateKeyPair();
    PrivateKey privateKey = pair.getPrivate();
    PublicKey publicKey = pair.getPublic();

    String plainText = "This is plain text.";
    Cipher encryptCipher = Cipher.getInstance("RSA");
    encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
    byte[] plainBytes = plainText.getBytes(StandardCharsets.UTF_8);
    byte[] encryptedBytes = encryptCipher.doFinal(plainBytes);

    Cipher decryptCipher = Cipher.getInstance("RSA");
    decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
    byte[] decryptedBytes = decryptCipher.doFinal(encryptedBytes);
    String decryptedText = new String(decryptedBytes, StandardCharsets.UTF_8);

    assertEquals(plainText, decryptedText);
  }

  @Test
  void generateKeyPair() {
    KeyPair keyPair = Rsa.generateKeyPair();
    log.debug("public: {}", Rsa.convertToKeyString(keyPair.getPublic()));
    log.debug("private: {}", Rsa.convertToKeyString(keyPair.getPrivate()));
  }

  @Test
  void encryptAndDecrypt() {
    KeyPair keyPair = Rsa.generateKeyPair();
    String publicKey = Rsa.convertToKeyString(keyPair.getPublic());
    String privateKey = Rsa.convertToKeyString(keyPair.getPrivate());

    String plainText = "This is plain text.";
    String encrypted = Rsa.encrypt(plainText, publicKey);
    String decrypted = Rsa.decrypt(encrypted, privateKey);

    assertEquals(decrypted, plainText);
  }


}
