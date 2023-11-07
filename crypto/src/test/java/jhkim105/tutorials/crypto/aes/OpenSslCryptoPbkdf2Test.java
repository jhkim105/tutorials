package jhkim105.tutorials.crypto.aes;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


/**
 * openssl encrypt
 * openssl enc -aes-128-cbc --in src/test/resources/image.png -out src/test/resources/image.png.enc -k 31323334353637383930616263646566 -p -pbkdf2 -iter 1000
 * openssl enc -d -aes-128-cbc --in src/test/resources/image.png.enc -out src/test/resources/image-dec.png -k 31323334353637383930616263646566 -p -pbkdf2 -iter 1000
 */
@Slf4j
class OpenSslCryptoPbkdf2Test {


  @Test
  void encryptAndDecryptFile() throws IOException {
    File original = new File("src/test/resources/image.png");
    File encrypted = new File("src/test/resources/image.png.enc");
    File decrypted =new File("src/test/resources/image-dec.png");

    OpenSslCryptoPbkdf2 openSslCrypto = new OpenSslCryptoPbkdf2("1234567890abcdef");
    openSslCrypto.encrypt(original, encrypted);
    openSslCrypto.decrypt(encrypted, decrypted);
    assertTrue(IOUtils.contentEquals(Files.newInputStream(original.toPath()), Files.newInputStream(decrypted.toPath())));
  }

  @Disabled
  @Test
  void decrypt() throws IOException {
    File encrypted = new File("src/test/resources/image.png.enc");
    File decrypted =new File("src/test/resources/image-dec.png");

    OpenSslCryptoPbkdf2 openSslCrypto = new OpenSslCryptoPbkdf2("1234567890abcdef");
    openSslCrypto.decrypt(encrypted, decrypted);
  }


  @Test
  @Disabled
  void deriveKey() throws Exception {
    String password = "1234567890abcdef";
    String saltHex = "C8319EE577FB34BA";

    byte[] salt = Hex.decodeHex(saltHex);

    String keyAndIv = deriveKeyAndIv(password, salt, 1000);
    log.debug("keyAndIv: {}", keyAndIv);
  }


  private String deriveKeyAndIv(String key, byte[] salt, int iterationCount) {
    String keyHex = Hex.encodeHexString(key.getBytes());
    return Hex.encodeHexString(deriveKeyAndIv(keyHex.getBytes(), salt, iterationCount));
  }

  private byte[] deriveKeyAndIv(final byte[] password, final byte[] salt, int iterationCount) {
    int keyLength = 16;
    int ivLength = 16;
    KeySpec keySpec = new PBEKeySpec(new String(password).toCharArray(), salt, iterationCount, (keyLength + ivLength) * 8);
    SecretKeyFactory factory;
    try {
      factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
      return factory.generateSecret(keySpec).getEncoded();
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException(e);
    }
  }
}