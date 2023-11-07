package jhkim105.tutorials.crypto.aes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.util.Assert;


@Slf4j
public class OpenSslCryptoPbkdf2 {

  private static final String ALG = "AES";
  private static final String TF = "AES/CBC/PKCS5Padding";
  private static final String SALT_PREFIX = "Salted__";

  private final String password;
  private final int iterationCount;

  public OpenSslCryptoPbkdf2(String password) {
    this.password = password;
    this.iterationCount = 1000;
  }

  public void encrypt(File inputFile, File outputFile) {
    byte[] salt = generateSalt();
    log.debug("salt: {}", Hex.encodeHexString(salt));
    String keyAndIv = deriveKeyAndIv(password, salt, iterationCount);
    byte[] keyBytes = decodeHex(keyAndIv.substring(0, 32).toCharArray());
    byte[] ivBytes = decodeHex(keyAndIv.substring(32, 64).toCharArray());

    Cipher cipher;
    try {
      cipher = Cipher.getInstance(TF);
      cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, ALG), new IvParameterSpec(ivBytes));
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
      throw new RuntimeException(e);
    }

    try (
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        CipherOutputStream cipherOut = new CipherOutputStream(outputStream, cipher)
    ) {
      outputStream.write(SALT_PREFIX.getBytes());
      outputStream.write(salt);
      FileUtils.copyFile(inputFile, cipherOut);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void decrypt(File inputFile, File outputFile) {
    try (FileInputStream inputStream = new FileInputStream(inputFile)) {
      byte[] prefix = new byte[8];
      inputStream.read(prefix);
      Assert.isTrue(new String(prefix).equals(SALT_PREFIX), "Salt not exists.");
      byte[] salt = new byte[8];
      inputStream.read(salt);
      log.debug("salt: {}", Hex.encodeHexString(salt));

      String keyAndIv = deriveKeyAndIv(password, salt, iterationCount);
      log.debug("key: {}", keyAndIv.substring(0, 32));
      log.debug("iv: {}", keyAndIv.substring(32, 64));

      byte[] keyBytes = decodeHex(keyAndIv.substring(0, 32).toCharArray());
      byte[] ivBytes = decodeHex(keyAndIv.substring(32, 64).toCharArray());

      Cipher cipher = Cipher.getInstance(TF);
      cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes, ALG), new IvParameterSpec(ivBytes));

      try (
          CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
          FileOutputStream outputStream = new FileOutputStream(outputFile)
      ) {
        IOUtils.copy(cipherInputStream, outputStream);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

    } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
             InvalidAlgorithmParameterException e) {
      throw new RuntimeException(e);
    }

  }

  private byte[] generateSalt() {
    byte[] bytes = new byte[8];
    SecureRandom random = new SecureRandom();
    random.nextBytes(bytes);
    return bytes;
  }


  private String deriveKeyAndIv(String password, byte[] salt, int iterationCount) {
    String keyHex = Hex.encodeHexString(password.getBytes());
    return Hex.encodeHexString(deriveKeyAndIv(keyHex.getBytes(), salt, iterationCount));
  }

  private byte[] deriveKeyAndIv(final byte[] pass, final byte[] salt, int iterationCount) {
    int keyLength = 16;
    int ivLength = 16;
    KeySpec keySpec = new PBEKeySpec(new String(pass).toCharArray(), salt, iterationCount, (keyLength + ivLength) * 8);
    SecretKeyFactory factory;
    try {
      factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
      return factory.generateSecret(keySpec).getEncoded();
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException(e);
    }
  }

  private byte[] decodeHex(char[] chars) {
    try {
      return Hex.decodeHex(chars);
    } catch (DecoderException e) {
      throw new RuntimeException(e);
    }
  }



}
