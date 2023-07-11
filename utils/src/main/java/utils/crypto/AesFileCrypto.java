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
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;


/**
 * iv 값을 포함하여 저장
 */
public class AesFileCrypto {

  private static final String ALG = "AES/CBC/PKCS5Padding";
  private SecretKey secretKey;
  private Cipher cipher;

  public AesFileCrypto(String secret)  {
    this.secretKey = secretKey(secret);
    this.cipher = cipher(ALG);
  }

  private Cipher cipher(String alg) {
    try {
      return Cipher.getInstance(alg);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
      throw new RuntimeException(e);
    }
  }

  private SecretKey secretKey(String secret) {
    byte[] key = secret.getBytes(StandardCharsets.UTF_8);
    key = Arrays.copyOf(key, 32);
    return new SecretKeySpec(key, "AES");
  }

  public void encrypt(File inputFile, File outputFile)  {
    initCipher(Cipher.ENCRYPT_MODE);
    byte[] iv = cipher.getIV();

    try (
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        CipherOutputStream cipherOut = new CipherOutputStream(outputStream, cipher)
    ) {
      outputStream.write(iv);
      cipherOut.write(FileUtils.readFileToByteArray(inputFile));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  private void initCipher(int encryptMode) {
    try {
      cipher.init(encryptMode, secretKey);
    } catch (InvalidKeyException e) {
      throw new RuntimeException(e);
    }
  }

  private void initCipher(int encryptMode, IvParameterSpec ivParameterSpec) {
    try {
      cipher.init(encryptMode, secretKey, ivParameterSpec);
    } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
      throw new RuntimeException(e);
    }
  }

  public void decrypt(File inputFile, File outputFile)  {
    try (FileInputStream fileIn = new FileInputStream(inputFile)) {
      byte[] fileIv = new byte[16];
      fileIn.read(fileIv);
      initCipher(Cipher.DECRYPT_MODE, new IvParameterSpec(fileIv));

      try (
          CipherInputStream cipherInputStream = new CipherInputStream(fileIn, cipher);
          FileOutputStream outputStream = new FileOutputStream(outputFile);
      ) {
        IOUtils.copy(cipherInputStream, outputStream);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
