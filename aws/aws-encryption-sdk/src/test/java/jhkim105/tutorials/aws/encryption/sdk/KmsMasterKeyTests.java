package jhkim105.tutorials.aws.encryption.sdk;


import com.amazonaws.encryptionsdk.AwsCrypto;
import com.amazonaws.encryptionsdk.CommitmentPolicy;
import com.amazonaws.encryptionsdk.CryptoResult;
import com.amazonaws.encryptionsdk.kmssdkv2.KmsMasterKey;
import com.amazonaws.encryptionsdk.kmssdkv2.KmsMasterKeyProvider;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * https://docs.aws.amazon.com/encryption-sdk/latest/developer-guide/java-example-code.html
 * Key ARN: For help finding the Amazon Resource Name (ARN) of your AWS KMS key, see 'Finding the key ID and key ARN' at https://docs.aws.amazon.com/kms/latest/developerguide/find-cmk-id-arn.html
 */
class KmsMasterKeyTests {

  private static final byte[] EXAMPLE_DATA = "Hello World".getBytes(StandardCharsets.UTF_8);

  private static final String KEY_ARN = "arn:aws:kms:ap-northeast-1:919767229200:key/mrk-ea3f92b9121343d9b5b0ec8df0f3cbb2";

  @Test
  void test() {
//    System.setProperty("aws.accessKeyId", "...");
//    System.setProperty("aws.secretAccessKey", "...");

    final AwsCrypto crypto = AwsCrypto.builder()
        .withCommitmentPolicy(CommitmentPolicy.RequireEncryptRequireDecrypt)
        .build();

    final KmsMasterKeyProvider keyProvider = KmsMasterKeyProvider.builder()
        .buildStrict(KEY_ARN);

    final Map<String, String> encryptionContext = Collections.singletonMap("ExampleContextKey",
        "ExampleContextValue");

    final CryptoResult<byte[], KmsMasterKey> encryptResult = crypto.encryptData(keyProvider,
        EXAMPLE_DATA, encryptionContext);
    final byte[] ciphertext = encryptResult.getResult();

    final CryptoResult<byte[], KmsMasterKey> decryptResult = crypto.decryptData(keyProvider,
        ciphertext);

    if (!encryptionContext.entrySet().stream()
        .allMatch(
            e -> e.getValue().equals(decryptResult.getEncryptionContext().get(e.getKey())))) {
      throw new IllegalStateException("Wrong Encryption Context!");
    }

    Assertions.assertThat(Arrays.equals(decryptResult.getResult(), EXAMPLE_DATA)).isTrue();
  }

}
