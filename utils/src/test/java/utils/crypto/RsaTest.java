package utils.crypto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
class RsaTest {

  @Test
  void testString() throws Exception {
    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
    generator.initialize(2048);
    KeyPair keyPair = generator.generateKeyPair();
    PrivateKey privateKey = keyPair.getPrivate();
    PublicKey publicKey = keyPair.getPublic();

    String original = "Original Text";
    Cipher encryptCipher = Cipher.getInstance("RSA");
    encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
    byte[] originalBytes = original.getBytes(StandardCharsets.UTF_8);
    byte[] encryptBytes = encryptCipher.doFinal(originalBytes);

    Cipher decryptCipher = Cipher.getInstance("RSA");
    decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
    byte[] decryptBytes = decryptCipher.doFinal(encryptBytes);
    String decrypt = new String(decryptBytes, StandardCharsets.UTF_8);

    Assertions.assertEquals(original, decrypt);
  }

  @Test
  void testFile() throws Exception {
    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
    generator.initialize(2048);
    KeyPair keyPair = generator.generateKeyPair();
    PrivateKey privateKey = keyPair.getPrivate();
    PublicKey publicKey = keyPair.getPublic();

    File originalFile = new File("src/test/resources/input.txt");
    File encryptFile = new File("target/encrypt.txt");
    byte[] fileBytes = Files.readAllBytes(originalFile.toPath());
    Cipher encryptCipher = Cipher.getInstance("RSA");
    encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
    byte[] encryptedFileBytes = encryptCipher.doFinal(fileBytes);
    try (FileOutputStream stream = new FileOutputStream(encryptFile)) {
      stream.write(encryptedFileBytes);
    }

    File decryptFile = new File("target/encrypt.txt");
    encryptedFileBytes = FileUtils.readFileToByteArray(encryptFile);
    Cipher decryptCipher = Cipher.getInstance("RSA");
    decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
    byte[] decryptedFileBytes = decryptCipher.doFinal(encryptedFileBytes);
    try (FileOutputStream stream = new FileOutputStream(decryptFile)) {
      stream.write(decryptedFileBytes);
    }

    String original = readString(new File("src/test/resources/input.txt"));
    String decrypt = readString(decryptFile);
    Assertions.assertEquals(original, decrypt);

  }

  private String readString(File file) throws Exception {
    StringBuilder resultStringBuilder = new StringBuilder();
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        resultStringBuilder.append(line);
      }
    }
    return resultStringBuilder.toString();
  }

}
