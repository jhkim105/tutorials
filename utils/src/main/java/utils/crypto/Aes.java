package utils.crypto;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;


/**
 * IV 를 고정값으로 사용하는 방식은 CBC 에서 권장 방식은 아님 (암호화 결과문이 동일하므로)
 * secret 으로 부터 유도(password based key drive) 하거나, 암호화 결과에 같이 기록하는 방식을 추천함
 */
@Slf4j
public class Aes {

  private static final String ALG = "AES/CBC/PKCS5Padding";

  private final SecretKey secretKey;
  private final IvParameterSpec ivParameterSpec;

  public Aes(String password, int size) {
    this.secretKey = generateKey(password, size);
    this.ivParameterSpec = generateIv(password);
  }

  public Aes(String password, String iv, int size) {
    this.secretKey = generateKey(password, size);
    this.ivParameterSpec = generateIv(iv);
  }

  public Aes(byte[] password, byte[] iv, int size) {
    this.secretKey = generateKey(password, size);
    this.ivParameterSpec = generateIv(iv);
  }

  public static Aes newInstance(String password) {
    return new Aes(password, 16);
  }


  public String encrypt(String strToEncrypt) {
    return Base64.getEncoder().encodeToString(encrypt(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
  }

  public String decrypt(String strToDecrypt) {
    return new String(decrypt(Base64.getDecoder().decode(strToDecrypt)));
  }

  public byte[] encrypt(byte[] bytesToEncrypt) {
    try {
      Cipher cipher = initCipher(ALG);
      cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
      return cipher.doFinal(bytesToEncrypt);
    } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
      throw new RuntimeException(e);
    }

  }

  public byte[] decrypt(byte[] bytesToDecrypt) {
    try {
      Cipher cipher = initCipher(ALG);
      cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
      return cipher.doFinal(bytesToDecrypt);
    } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
      throw new RuntimeException(e);
    }
  }


  private Cipher initCipher(String transformation) {
    try {
      return Cipher.getInstance(transformation);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
      throw new RuntimeException(e);
    }
  }

  private SecretKey generateKey(String password, int size) {
    byte[] passwordBytes = getPasswordBytes(password);
    passwordBytes = Arrays.copyOf(passwordBytes, size);
    return generateKey(passwordBytes);
  }

  private SecretKey generateKey(byte[] password, int size) {
    password = Arrays.copyOf(password, size);
    return generateKey(password);
  }

  private SecretKey generateKey(byte[] password) {
    return new SecretKeySpec(password, "AES");
  }

  private byte[] getPasswordBytes(String password) {
    return password.getBytes(StandardCharsets.UTF_8);
  }

  private IvParameterSpec generateIv(String iv) {
    byte[] bytes = getSha256Hash(iv);
    bytes = Arrays.copyOf(bytes, 16);
    return generateIv(bytes);
  }

  private IvParameterSpec generateIv(byte[] iv) {
    iv = Arrays.copyOf(iv, 16);
    return new IvParameterSpec(iv);
  }

  private byte[] getSha256Hash(String value) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      return digest.digest(value.getBytes(StandardCharsets.UTF_8));
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  private SecretKey generateKey(String password, String salt) {
    SecretKeyFactory factory;
    try {
      factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
    try {
      return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    } catch (InvalidKeySpecException e) {
      throw new RuntimeException(e);
    }
  }

}