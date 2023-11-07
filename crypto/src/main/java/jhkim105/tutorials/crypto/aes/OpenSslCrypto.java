package jhkim105.tutorials.crypto.aes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.util.Assert;


@Slf4j
public class OpenSslCrypto {

  private static final String ALG = "AES";
  private static final String TF = "AES/CBC/PKCS5Padding";
  private static final String SALT_PREFIX = "Salted__";

  private final String key;

  public OpenSslCrypto(String key) {
    this.key = key;
  }

  private String deriveKeyAndIv(String key, byte[] salt) {
    String keyHex = Hex.encodeHexString(key.getBytes());
    return Hex.encodeHexString(deriveKeyAndIv(keyHex.getBytes(), salt));
  }

  private byte[] deriveKeyAndIv(final byte[] pass, final byte[] salt) {
    MessageDigest md = DigestUtils.getSha256Digest();
    final byte[] passAndSalt = concat(pass, salt);
    byte[] hash = new byte[0];
    byte[] keyAndIv = new byte[0];
    for (int i = 0; i < 3 && keyAndIv.length < 48; i++) {
      final byte[] hashData = concat(hash, passAndSalt);
      hash = md.digest(hashData);
      keyAndIv = concat(keyAndIv, hash);
    }
    return keyAndIv;
  }

  private byte[] concat(byte[] a, byte[] b) {
    byte[] result = new byte[a.length + b.length];
    System.arraycopy(a, 0, result, 0, a.length);
    System.arraycopy(b, 0, result, a.length, b.length);
    return result;
  }

  private byte[] decodeHex(char[] chars) {
    try {
      return Hex.decodeHex(chars);
    } catch (DecoderException e) {
      throw new RuntimeException(e);
    }
  }


  public void encrypt(File inputFile, File outputFile) {
    byte[] salt = generateSalt();
    log.debug("salt: {}", Hex.encodeHexString(salt));
    String keyAndIv = deriveKeyAndIv(key, salt);
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


  private byte[] generateSalt() {
    byte[] bytes = new byte[8];
    SecureRandom random = new SecureRandom();
    random.nextBytes(bytes);
    return bytes;
  }

  public void decrypt(File inputFile, File outputFile) {
    try (FileInputStream inputStream = new FileInputStream(inputFile)) {
      byte[] prefix = new byte[8];
      inputStream.read(prefix);
      Assert.isTrue(new String(prefix).equals(SALT_PREFIX), "Salt not exists.");
      byte[] salt = new byte[8];
      inputStream.read(salt);
      log.debug("salt: {}", Hex.encodeHexString(salt));

      String keyAndIv = deriveKeyAndIv(key, salt);
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


}
