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

/**
 * Algorithm, key 를 지정
 */
public class FileCrypto {

  private SecretKey secretKey;
  private Cipher cipher;

  public FileCrypto(SecretKey secretKey, String cipher) throws NoSuchPaddingException, NoSuchAlgorithmException {
    this.secretKey = secretKey;
    this.cipher = Cipher.getInstance(cipher);
  }


  public void encrypt(String content, String destFilePath) throws InvalidKeyException, IOException {
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    byte[] iv = cipher.getIV();

    try (
        FileOutputStream fileOut = new FileOutputStream(destFilePath);
        CipherOutputStream cipherOut = new CipherOutputStream(fileOut, cipher)
    ) {
      fileOut.write(iv);
      cipherOut.write(content.getBytes(StandardCharsets.UTF_8));
    }

  }

  public String decrypt(String sourceFilePath) throws InvalidAlgorithmParameterException, InvalidKeyException, IOException {
    String content;
    try (FileInputStream fileIn = new FileInputStream(sourceFilePath)) {
      byte[] fileIv = new byte[16];
      fileIn.read(fileIv);
      cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(fileIv));

      try (
          CipherInputStream cipherIn = new CipherInputStream(fileIn, cipher);
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


}
