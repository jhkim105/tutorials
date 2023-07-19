package jhkim105.tutorials.crypto.aes;


import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;


@Slf4j
class KeyGenerateTests {

  @Test
  void generateKey() throws Exception {
    String password = "pass1234567890";
    String salt = "salt1234567890";

    Key secretKey = getPasswordBasedKey("AES", password, salt, 128);

    byte[] keyBytes = secretKey.getEncoded();
    log.debug("length: {}", keyBytes.length);
    log.debug(Hex.encodeHexString(keyBytes));
  }

  private Key getPasswordBasedKey(String algorithm, String password, String salt, int keySize) throws NoSuchAlgorithmException, InvalidKeySpecException {
    PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 1000, keySize);
    SecretKey pbeKey = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(pbeKeySpec);
    return new SecretKeySpec(pbeKey.getEncoded(), algorithm);
  }

}
