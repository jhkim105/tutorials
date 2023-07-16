package utils.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;


@Slf4j
public class AesFileCrypto {

  private static final String ALG = "AES";
  private static final String TF = "AES/CBC/PKCS5Padding";
  private static final int KEY_LENGTH = 16;
  private static final int IV_LENGTH = 16;
  private final SecretKey secretKey;

  public AesFileCrypto(String key) {
    this.secretKey = secretKey(key);
    log.debug("key hex: [{}]", Hex.encodeHexString(secretKey.getEncoded()));
  }

  private SecretKey secretKey(String secret) {
    byte[] key = secret.getBytes(StandardCharsets.UTF_8);
    key = Arrays.copyOf(key, KEY_LENGTH);
    return new SecretKeySpec(key, ALG);
  }

  public void encrypt(File inputFile, File outputFile) {
    try {
      Cipher cipher = Cipher.getInstance(TF);
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);

      try (
          FileOutputStream outputStream = new FileOutputStream(outputFile);
          CipherOutputStream cipherOut = new CipherOutputStream(outputStream, cipher)
      ) {
        byte[] iv = cipher.getIV();
        outputStream.write(iv);
        FileUtils.copyFile(inputFile, cipherOut);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
      throw new RuntimeException(e);
    }

  }

  public void decrypt(File inputFile, File outputFile) {
    try (FileInputStream inputStream = new FileInputStream(inputFile)) {
      byte[] iv = new byte[IV_LENGTH];
      inputStream.read(iv);
      Cipher cipher = Cipher.getInstance(TF);
      cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

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