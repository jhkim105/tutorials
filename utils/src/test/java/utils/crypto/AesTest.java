package utils.crypto;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Slf4j
class AesTest {


  @Test
  /**
   * openssl enc -d -aes-256-cbc -nosalt -K 6162636465666768696a6b6c6d6e6f7031323334353637383930313233343536 -iv 6162636465666768696a6b6c6d6e6f70 -in target/enc.txt -out target/dec-openssl.txt
   */
  void aes256() throws Exception{
    String key = "abcdefghijklmnop1234567890123456";
    Aes aes = new Aes(key, 32);
    String dec = "target/enc.txt";
    String originalString = "1234567890";
    byte[] encrypts = aes.encryptBytes(originalString);
    Files.write(Paths.get(dec), encrypts);
    encrypts = Files.readAllBytes(Paths.get(dec));

    String decryptString = new String(aes.decryptBytes(encrypts));
    Assertions.assertThat(originalString).isEqualTo(decryptString);
  }

  @Test
  @Disabled
  void aes256_decrypt_with_key() throws Exception{
    String key = "abcdefghijklmnop1234567890123456";
    Aes aes = new Aes(key, 32);
    String dec = "target/input-enc-256.txt";

    byte[] encrypts = Files.readAllBytes(Paths.get(dec));

    String decryptString = new String(aes.decryptBytes(encrypts));
    log.debug(decryptString);
  }

  @Test
  @Disabled
  void aes256_decrypt_with_key_and_iv() throws Exception{
    String key = "abcdefghijklmnop1234567890123456";
    String iv = "F7183A3E260A30DFCD75FBC8025AFBDA";
    Aes aes = new Aes(key, iv, 32);
    String dec = "target/input-enc-256.txt";

    byte[] encrypts = Files.readAllBytes(Paths.get(dec));

    String decryptString = new String(aes.decryptBytes(encrypts));
    log.debug(decryptString);
  }


  /**
   * openssl enc -e -aes-256-cbc -nosalt -k 6162636465666768696a6b6c6d6e6f7031323334353637383930313233343536 -p -in src/test/resources/input.txt -out target/input-enc-256.txt
   */
  @Test
  @Disabled
  void decryptWithHex() throws  Exception{
    String key ="4455CEB66E472882A8EF8ABA0DA7C6959A4A4ACDA57BBF8B095CD5D320CC6B12";
    String iv ="F7183A3E260A30DFCD75FBC8025AFBDA";
    Aes aes = new Aes(Hex.decodeHex(key), Hex.decodeHex(iv), 32);
    String dec = "target/input-enc-256.txt";

    byte[] encrypts = Files.readAllBytes(Paths.get(dec));

    String decryptString = new String(aes.decryptBytes(encrypts));
    log.debug(decryptString);
  }


  @Test
  void openssl_key_iv() throws Exception{
    String key = Hex.encodeHexString("abcdefghijklmnop1234567890123456".getBytes(StandardCharsets.UTF_8)); //6162636465666768696a6b6c6d6e6f7031323334353637383930313233343536
    byte[] keyBytes = DigestUtils.sha256(key);
    log.debug(Hex.encodeHexString(keyBytes)); // 4455CEB66E472882A8EF8ABA0DA7C6959A4A4ACDA57BBF8B095CD5D320CC6B12

    byte[] ivBytes = DigestUtils.sha256(keyBytes);
    ivBytes = Arrays.copyOf(ivBytes, 16);

    log.debug(Hex.encodeHexString(ivBytes));

  }


  @Test
  void aes128() throws Exception{
    String key = "abcdefghijklmnop";
    Aes aes = new Aes(key, 16);
    String dec = "target/enc_128.txt";
    String originalString = "1234567890";
    byte[] encrypts = aes.encryptBytes(originalString);
    Files.write(Paths.get(dec), encrypts);
    encrypts = Files.readAllBytes(Paths.get(dec));

    String decryptString = new String(aes.decryptBytes(encrypts));
    Assertions.assertThat(originalString).isEqualTo(decryptString);
  }


}