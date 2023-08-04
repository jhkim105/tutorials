package jhkim105.tutorials.crypto.rsa;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Rsa {

  private static final String ALGORITHM = "RSA";

  public static KeyPair generateKeyPair() {
    try {
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
      keyPairGenerator.initialize(2048, new SecureRandom());
      return keyPairGenerator.generateKeyPair();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }


  public static String encrypt(String plainText, String publicKey) {
    try {
      Cipher cipher = Cipher.getInstance(ALGORITHM);
      cipher.init(Cipher.ENCRYPT_MODE, convertToPublicKey(publicKey));
      byte[] bytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(bytes);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
             BadPaddingException e) {
      throw new RuntimeException(e);
    }
  }

  public static String decrypt(String encryptedText, String privateKey) {
    byte[] bytes = Base64.getDecoder().decode(encryptedText.getBytes());
    try {
      Cipher cipher = Cipher.getInstance(ALGORITHM);
      cipher.init(Cipher.DECRYPT_MODE, convertToPrivateKey(privateKey));
      return new String(cipher.doFinal(bytes), StandardCharsets.UTF_8);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
             BadPaddingException e) {
      throw new RuntimeException(e);
    }
  }

  private static Key convertToPublicKey(String keyString) {
    byte[] keyBytes = Base64.getDecoder().decode(keyString.getBytes());
    try {
      return KeyFactory.getInstance(ALGORITHM).generatePublic(new X509EncodedKeySpec(keyBytes));
    } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }

  }
  private static Key convertToPrivateKey(String keyString) {
    byte[] keyBytes = Base64.getDecoder().decode(keyString.getBytes());
    try {
      return KeyFactory.getInstance(ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
    } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }

  }

  public static String convertToKeyString(Key key) {
    return Base64.getEncoder().encodeToString(key.getEncoded());
  }


}
