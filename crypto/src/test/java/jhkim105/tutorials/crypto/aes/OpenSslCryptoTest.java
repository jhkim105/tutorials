package jhkim105.tutorials.crypto.aes;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


/**
 * openssl enc -aes-128-cbc --in src/test/resources/image.png -out src/test/resources/image.png.enc -k 31323334353637383930616263646566 -p
 * openssl enc -d -aes-128-cbc --in src/test/resources/image.png.enc -out src/test/resources/image-dec.png -k 31323334353637383930616263646566 -p
 */
@Slf4j
class OpenSslCryptoTest {


  @Test
  void encryptAndDecryptFile() throws IOException {
    File original = new File("src/test/resources/image.png");
    File encrypted = new File("src/test/resources/image.png.enc");
    File decrypted =new File("src/test/resources/image-dec.png");

    OpenSslCrypto openSslCrypto = new OpenSslCrypto("1234567890abcdef");
    openSslCrypto.encrypt(original, encrypted);
    openSslCrypto.decrypt(encrypted, decrypted);
    assertTrue(IOUtils.contentEquals(Files.newInputStream(original.toPath()), Files.newInputStream(decrypted.toPath())));
  }

  @Disabled
  @Test
  void decrypt() throws IOException {

    // openssl encrypt
    // openssl enc -aes-128-cbc --in src/test/resources/image.png -out src/test/resources/image.png.enc -k 31323334353637383930616263646566 -p

    File encrypted = new File("src/test/resources/image.png.enc");
    File decrypted =new File("src/test/resources/image-dec.png");

    OpenSslCrypto openSslCrypto = new OpenSslCrypto("1234567890abcdef");
    openSslCrypto.decrypt(encrypted, decrypted);
  }

  private String decodeHex(String str) {
    try {
      return new String(Hex.decodeHex(str));
    } catch (DecoderException e) {
      throw new RuntimeException(e);
    }
  }

  @Disabled
  @Test
  void encrypt() throws IOException {
    File original = new File("src/test/resources/image.png");
    File encrypted = new File("src/test/resources/image.png.enc");

    OpenSslCrypto openSslCrypto = new OpenSslCrypto("1234567890abcdef");
    openSslCrypto.encrypt(original, encrypted);

    // openssl decrypt
    // openssl enc -d -aes-128-cbc --in src/test/resources/image.png.enc -out src/test/resources/image-dec.png -k 31323334353637383930616263646566 -p
  }


}