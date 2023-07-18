package jhkim105.tutorials.crypto.aes;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;


@Slf4j
class OpensslAesTests {

  private static final String KEY = "1234567890abcdef";

  @Test
  /**
   * openssl enc -aes-128-cbc --nosalt --in input.txt -out enc-openssl.txt -k 31323334353637383930616263646566 -p
   *  1234567890abcdef
   *    31323334353637383930616263646566
   *    key=61FEB9C8A7000372181997A660798F89
   *    iv =D00446D6C850A0771301EC74F9DE27A7
   */
  void openssl_key_iv_nosalt() throws Exception {
    String key = KEY;
    String keyHex = Hex.encodeHexString(key.getBytes());
    log.debug(keyHex);
    String keyHexHashHex = Hex.encodeHexString(DigestUtils.sha256(keyHex));
    byte[] keyBytes = Hex.decodeHex(keyHexHashHex.substring(0, 32).toCharArray());
    log.debug("key: {}", Hex.encodeHexString(keyBytes)); // 61feb9c8a7000372181997a660798f89
    byte[] ivBytes = Hex.decodeHex(keyHexHashHex.substring(32, 64).toCharArray());
    log.debug("iv: {}", Hex.encodeHexString(ivBytes)); // d00446d6c850a0771301ec74f9de27a7

  }

  /**
   * openssl enc -aes-128-cbc --nosalt --in input.txt -out enc-openssl.txt -k 31323334353637383930616263646566 -p
   * 1234567890abcdef
   * 31323334353637383930616263646566
   * key=61FEB9C8A7000372181997A660798F89
   * iv =D00446D6C850A0771301EC74F9DE27A7
   */
  @Test
  void decrypt_nosalt() throws Exception {

    String inputFile = "src/test/resources/enc-openssl.txt"; // 암호화된 파일 경로와 이름
    String outputFile = "target/decrypted.txt"; // 복호화된 파일 저장 경로와 이름

    String key = KEY;

    byte[] inputBytes = Files.readAllBytes(Paths.get(inputFile));
    log.debug("input length: {}", inputBytes.length);

    // key: Hex.decode(Sha256(Hex.encode(key))[0-16])
    // iv: Hex.decode(Sha256(Hex.encode(key))[0-32])
    String keyAndIv = Hex.encodeHexString(DigestUtils.sha256(Hex.encodeHexString(key.getBytes())));
    byte[] keyBytes = Hex.decodeHex(keyAndIv.substring(0, 32).toCharArray());
    byte[] iv = Hex.decodeHex(keyAndIv.substring(32, 64).toCharArray());

    byte[] decrypted = decrypt(inputBytes, keyBytes, iv);

    Files.write(Paths.get(outputFile), decrypted);
  }

  private byte[] decrypt(byte[] inputBytes, byte[] keyBytes, byte[] iv) {
    log.debug("key: {}", Hex.encodeHexString(keyBytes)); // 61feb9c8a7000372181997a660798f89
    log.debug("iv : {}", Hex.encodeHexString(iv)); // d00446d6c850a0771301ec74f9de27a7
    SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

    IvParameterSpec ivSpec = new IvParameterSpec(iv);

    Cipher cipher;
    try {
      cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
      cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);

      return cipher.doFinal(inputBytes);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException |
             IllegalBlockSizeException | BadPaddingException e) {
      throw new RuntimeException(e);

    }
  }



  /**
   * openssl enc -aes-128-cbc --in input.txt -out enc-openssl-salt.txt -k 31323334353637383930616263646566 -p
   * salt=3FFFC2F7A0B7A973
   * key=FF5128C186EC51B97C6EC9BA47D20A52
   * iv =742B2AA028C92177317C27C400FCA3A5
   *
   * prefix: Salted__
   */
  @Test
  void decrypt_salt() throws Exception {
    String inputFile = "src/test/resources/enc-openssl-salt.txt"; // 암호화된 파일 경로와 이름
    String outputFile = "target/decrypted.txt"; // 복호화된 파일 저장 경로와 이름

    byte[] inputBytes = Files.readAllBytes(Paths.get(inputFile));;
    log.debug("{}", new String(Arrays.copyOfRange(inputBytes, 0, 8))); // Salted__

    byte[] salt = Arrays.copyOfRange(inputBytes, 8, 16);
    log.debug("salt: {}", Hex.encodeHexString(salt)); // fb80828cd8097b4f

    byte[] cipherBytes = Arrays.copyOfRange(inputBytes, 16, inputBytes.length);



    // Derive key
    String key = KEY;
    String keyHex = Hex.encodeHexString(key.getBytes());

    String keyAndIv = Hex.encodeHexString(deriveKeyAndIv(keyHex.getBytes(), salt));
    log.debug("keyAndIv: {}", keyAndIv);

    byte[] keyBytes = Hex.decodeHex(keyAndIv.substring(0, 32).toCharArray());
    byte[] ivBytes = Hex.decodeHex(keyAndIv.substring(32, 64).toCharArray());

    byte[] decrypted = decrypt(cipherBytes, keyBytes, ivBytes);
    Files.write(Paths.get(outputFile), decrypted);

  }

  private byte[] concat(byte[] a, byte[] b) {
    byte[] result = new byte[a.length + b.length];
    System.arraycopy(a, 0, result, 0, a.length);
    System.arraycopy(b, 0, result, a.length, b.length);
    return result;
  }

  private byte[] deriveKeyAndIv(final byte[] pass, final byte[] salt) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("SHA-256");
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

  private byte[] deriveKeyAndIvWithPBKDF2(String password, int hashIterations, final byte[] salt)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");;

    PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, hashIterations, 48 * 8);
    byte[] keyAndIv = keyFactory.generateSecret(keySpec).getEncoded();
    return keyAndIv;
  }

}

