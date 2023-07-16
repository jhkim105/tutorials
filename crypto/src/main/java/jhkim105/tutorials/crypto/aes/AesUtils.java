package jhkim105.tutorials.crypto.aes;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AesUtils {

  public static SecretKey generateKey(int keySize) {
    KeyGenerator keyGenerator = null;
    try {
      keyGenerator = KeyGenerator.getInstance("AES");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    keyGenerator.init(keySize);
    SecretKey key = keyGenerator.generateKey();
    return key;
  }

  public static SecretKey generateKey(String password, String salt, int keySize) {
    SecretKeyFactory factory;
    try {
      factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, keySize);
    SecretKey secretKey = null;
    try {
      secretKey = new SecretKeySpec(factory.generateSecret(spec)
          .getEncoded(), "AES");
    } catch (InvalidKeySpecException e) {
      throw new RuntimeException(e);
    }
    return secretKey;
  }

  public static SecretKey generateKey(String secret, int keySize) {
    byte[] key = secret.getBytes(StandardCharsets.UTF_8);
    key = Arrays.copyOf(key, keySize);
    return new SecretKeySpec(key, "AES");
  }

  public static IvParameterSpec generateIv() {
    byte[] iv = new byte[16];
    new SecureRandom().nextBytes(iv);
    return new IvParameterSpec(iv);
  }



}
