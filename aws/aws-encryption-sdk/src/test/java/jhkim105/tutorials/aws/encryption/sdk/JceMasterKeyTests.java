package jhkim105.tutorials.aws.encryption.sdk;

import com.amazonaws.encryptionsdk.AwsCrypto;
import com.amazonaws.encryptionsdk.CommitmentPolicy;
import com.amazonaws.encryptionsdk.CryptoAlgorithm;
import com.amazonaws.encryptionsdk.CryptoInputStream;
import com.amazonaws.encryptionsdk.jce.JceMasterKey;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.Test;

/**
 * https://docs.aws.amazon.com/encryption-sdk/latest/developer-guide/java-example-code.html
 */
public class JceMasterKeyTests {


  @Test
  void test() throws IOException {
    String srcFile = "src/test/resources/original.txt"; // TODO

    SecretKey cryptoKey = retrieveEncryptionKey();

    JceMasterKey masterKey = JceMasterKey.getInstance(cryptoKey, "Example", "RandomKey", "AES/GCM/NoPadding");

    final AwsCrypto crypto = AwsCrypto.builder()
        .withCommitmentPolicy(CommitmentPolicy.RequireEncryptRequireDecrypt)
        .withEncryptionAlgorithm(CryptoAlgorithm.ALG_AES_256_GCM_HKDF_SHA512_COMMIT_KEY)
        .build();

    Map<String, String> context = Collections.singletonMap("Example", "FileStreaming");

    FileInputStream in = new FileInputStream(srcFile);
    CryptoInputStream<JceMasterKey> encryptingStream = crypto.createEncryptingStream(masterKey, in, context);
    FileOutputStream out = new FileOutputStream(srcFile + ".encrypted");
    IOUtils.copy(encryptingStream, out);
    encryptingStream.close();
    out.close();

    in = new FileInputStream(srcFile + ".encrypted");
    CryptoInputStream<JceMasterKey> decryptingStream = crypto.createUnsignedMessageDecryptingStream(masterKey, in);
    if (!"FileStreaming".equals(decryptingStream.getCryptoResult().getEncryptionContext().get("Example"))) {
      throw new IllegalStateException("Bad encryption context");
    }

    out = new FileOutputStream(srcFile + ".decrypted");
    IOUtils.copy(decryptingStream, out);
    decryptingStream.close();
    out.close();
  }


  /**
   * In practice, this key would be saved in a secure location.
   * For this demo, we generate a new random key for each operation.
   */
  private static SecretKey retrieveEncryptionKey() {
    SecureRandom rnd = new SecureRandom();
    byte[] rawKey = new byte[16]; // 128 bits
    rnd.nextBytes(rawKey);
    return new SecretKeySpec(rawKey, "AES");
  }
}
