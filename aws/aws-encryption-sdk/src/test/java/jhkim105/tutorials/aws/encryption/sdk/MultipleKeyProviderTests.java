package jhkim105.tutorials.aws.encryption.sdk;


import com.amazonaws.encryptionsdk.AwsCrypto;
import com.amazonaws.encryptionsdk.CommitmentPolicy;
import com.amazonaws.encryptionsdk.CryptoOutputStream;
import com.amazonaws.encryptionsdk.MasterKeyProvider;
import com.amazonaws.encryptionsdk.jce.JceMasterKey;
import com.amazonaws.encryptionsdk.kmssdkv2.KmsMasterKeyProvider;
import com.amazonaws.encryptionsdk.multi.MultipleProviderFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.Test;

/**
 *
 * The sample encrypts data under both an AWS KMS key and an "escrowed" RSA key pair
 */
class MultipleKeyProviderTests {

  private static PublicKey publicEscrowKey;
  private static PrivateKey privateEscrowKey;

  @Test
  void test() throws Exception {
    generateEscrowKeyPair();

    final String kmsArn = "arn:aws:kms:ap-northeast-1:919767229200:key/mrk-ea3f92b9121343d9b5b0ec8df0f3cbb2";
    final String fileName = "src/test/resources/original.txt";

    standardEncrypt(kmsArn, fileName);
    standardDecrypt(kmsArn, fileName);

    escrowDecrypt(fileName);
  }

  private void generateEscrowKeyPair() throws GeneralSecurityException {
    final KeyPairGenerator kg = KeyPairGenerator.getInstance("RSA");
    kg.initialize(4096); // Escrow keys should be very strong
    final KeyPair keyPair = kg.generateKeyPair();
    publicEscrowKey = keyPair.getPublic();
    privateEscrowKey = keyPair.getPrivate();

  }


  private void standardEncrypt(final String kmsArn, final String fileName) throws Exception {
    final AwsCrypto crypto = AwsCrypto.builder()
        .withCommitmentPolicy(CommitmentPolicy.RequireEncryptRequireDecrypt)
        .build();

    final KmsMasterKeyProvider keyProvider = KmsMasterKeyProvider.builder().buildStrict(kmsArn);
    final JceMasterKey escrowPub = JceMasterKey.getInstance(publicEscrowKey, null, "Escrow",
        "Escrow",
        "RSA/ECB/OAEPWithSHA-512AndMGF1Padding");

    // Combine the providers into a single master key provider
    final MasterKeyProvider<?> provider = MultipleProviderFactory.buildMultiProvider(keyProvider,
        escrowPub);

    final FileInputStream in = new FileInputStream(fileName);
    final FileOutputStream out = new FileOutputStream(fileName + ".encrypted");
    final CryptoOutputStream<?> encryptingStream = crypto.createEncryptingStream(provider, out);
    IOUtils.copy(in, encryptingStream);
    in.close();
    encryptingStream.close();
  }

  private void standardDecrypt(final String kmsArn, final String fileName) throws Exception {
    final AwsCrypto crypto = AwsCrypto.builder()
        .withCommitmentPolicy(CommitmentPolicy.RequireEncryptRequireDecrypt)
        .build();

    final KmsMasterKeyProvider keyProvider = KmsMasterKeyProvider.builder().buildStrict(kmsArn);

    final JceMasterKey escrowPub = JceMasterKey.getInstance(publicEscrowKey, null, "Escrow",
        "Escrow",
        "RSA/ECB/OAEPWithSHA-512AndMGF1Padding");

    final MasterKeyProvider<?> provider = MultipleProviderFactory.buildMultiProvider(keyProvider,
        escrowPub);

    final FileInputStream in = new FileInputStream(fileName + ".encrypted");
    final FileOutputStream out = new FileOutputStream(fileName + ".decrypted");
    final ByteArrayOutputStream plaintextBuffer = new ByteArrayOutputStream();
    final CryptoOutputStream<?> decryptingStream = crypto.createDecryptingStream(provider,
        plaintextBuffer);
    IOUtils.copy(in, decryptingStream);
    in.close();
    decryptingStream.close();
    final ByteArrayInputStream plaintextReader = new ByteArrayInputStream(
        plaintextBuffer.toByteArray());
    IOUtils.copy(plaintextReader, out);
    out.close();
  }

  private void escrowDecrypt(final String fileName) throws Exception {
    final AwsCrypto crypto = AwsCrypto.standard();

    final JceMasterKey escrowPriv = JceMasterKey.getInstance(publicEscrowKey, privateEscrowKey,
        "Escrow", "Escrow",
        "RSA/ECB/OAEPWithSHA-512AndMGF1Padding");

    final FileInputStream in = new FileInputStream(fileName + ".encrypted");
    final FileOutputStream out = new FileOutputStream(fileName + ".deescrowed");
    final CryptoOutputStream<?> decryptingStream = crypto.createDecryptingStream(escrowPriv, out);
    IOUtils.copy(in, decryptingStream);
    in.close();
    decryptingStream.close();
  }


}
