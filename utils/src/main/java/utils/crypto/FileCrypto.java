package utils.crypto;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import lombok.extern.slf4j.Slf4j;

/**
 * Algorithm, key 를 지정
 */
@Slf4j
public class FileCrypto {

  private final SecretKey secretKey;
  private final String transformation;

  public FileCrypto(SecretKey secretKey, String transformation) {
    this.secretKey = secretKey;
    this.transformation = transformation;
  }


  public void encrypt(String content, String destFilePath) throws InvalidKeyException, IOException {
    Cipher cipher = getCipher();
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);

    try (
        FileOutputStream fileOut = new FileOutputStream(destFilePath);
        CipherOutputStream cipherOut = new CipherOutputStream(fileOut, cipher)
    ) {

      byte[] iv = cipher.getIV();
      fileOut.write(iv);
      cipherOut.write(content.getBytes(StandardCharsets.UTF_8));
    }

  }

  public String decrypt(String sourceFilePath) throws InvalidAlgorithmParameterException, InvalidKeyException, IOException {
    String content;
    try (FileInputStream inputStream = new FileInputStream(sourceFilePath)) {
      byte[] iv = new byte[16];
      inputStream.read(iv);
      Cipher cipher = getCipher();
      cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

      try (
          CipherInputStream cipherIn = new CipherInputStream(inputStream, cipher);
          InputStreamReader inputReader = new InputStreamReader(cipherIn);
          BufferedReader reader = new BufferedReader(inputReader);
      ) {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
          sb.append(line);
        }
        content = sb.toString();
      }

    }
    return content;
  }

  private Cipher getCipher() {
    try {
      return Cipher.getInstance(transformation);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
      throw new RuntimeException(e);
    }
  }

}
