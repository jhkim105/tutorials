package utils.crypto;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
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
import org.apache.commons.codec.digest.DigestUtils;


/**
 * IV 를 고정값으로 사용하는 방식은 CBC 에서 권장 방식은 아님 (암호화 결과문이 동일하므로)
 * 지정하지 않을 경우 iv 값을 포함해서 저장해야 하므로 사이즈가 길어진다.
 * 여기에서는 고정값 사용함
 */
@Slf4j
public class Aes {

  private static final String ALG = "AES/CBC/PKCS5Padding";

  private final SecretKey secretKey;

  private final IvParameterSpec ivParameterSpec;

  private final Cipher cipher;

  public Aes(String password, int size) {
    this.cipher = cipher(ALG);
    this.secretKey = generateKey(password, size);
    this.ivParameterSpec = generateIv(password);
  }

  public Aes(String password, String iv, int size) {
    this.cipher = cipher(ALG);
    this.secretKey = generateKey(password, size);
    this.ivParameterSpec = generateIv(iv);
  }

  public Aes(byte[] password, byte[] iv, int size) {
    this.cipher = cipher(ALG);
    this.secretKey = generateKey(password, size);
    this.ivParameterSpec = generateIv(iv);
  }

  public static Aes newInstance(String password) {
      return new Aes(password, 16);
  }

  private Cipher cipher(String alg) {
    try {
      return Cipher.getInstance(alg);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
      throw new RuntimeException(e);
    }
  }

  private SecretKey generateKey(String password, int size) {
    byte[] bytes = password.getBytes(StandardCharsets.UTF_8);
    bytes = Arrays.copyOf(bytes, size);
    return generateKey(bytes, size);
  }

  private SecretKey generateKey(byte[] password, int size) {
    password = Arrays.copyOf(password, size);
    return new SecretKeySpec(password, "AES");
  }

  private IvParameterSpec generateIv(String iv) {
    byte[] bytes = DigestUtils.sha256(iv);
    bytes = Arrays.copyOf(bytes, 16);
    return generateIv(bytes);
  }

  private IvParameterSpec generateIv(byte[] iv) {
    iv = Arrays.copyOf(iv, 16);
    return new IvParameterSpec(iv);
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
      return new SecretKeySpec(factory.generateSecret(spec)
          .getEncoded(), "AES");
    } catch (InvalidKeySpecException e) {
      throw new RuntimeException(e);
    }
  }

  public String encrypt(String strToEncrypt) {
    return Base64.getEncoder().encodeToString(encryptBytes(strToEncrypt));
  }

  public String decrypt(String strToDecrypt) {
    return new String(decryptBytes(Base64.getDecoder().decode(strToDecrypt)));
  }

  public byte[] encryptBytes(String strToEncrypt) {
    try {
      cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
      byte[] bytes = strToEncrypt.getBytes(StandardCharsets.UTF_8);
      return cipher.doFinal(bytes);
    } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
      throw new RuntimeException(e);
    }

  }

  public byte[] decryptBytes(byte[] bytesToDecrypt) {
    try {
      cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
      return cipher.doFinal(bytesToDecrypt);
    } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
      throw new RuntimeException(e);
    }
  }
}
