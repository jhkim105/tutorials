package jhkim105.tutorials.aws.encryption.sdk;

import com.amazonaws.encryptionsdk.AwsCrypto;
import com.amazonaws.encryptionsdk.CryptoResult;
import com.amazonaws.encryptionsdk.kmssdkv2.KmsMasterKey;
import com.amazonaws.encryptionsdk.kmssdkv2.KmsMasterKeyProvider;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Crypto {

  private final AwsCrypto awsCrypto;

  private final KmsMasterKeyProvider masterKeyProvider;


  public String encrypt(String str) {
    return Base64.getEncoder().encodeToString(encryptBytes(str.getBytes(StandardCharsets.UTF_8)));
  }

  public String decrypt(String str) {
    return new String(decryptBytes(Base64.getDecoder().decode(str)));
  }

  public byte[] encryptBytes(byte[] bytesToDecrypt) {
    final CryptoResult<byte[], KmsMasterKey> encryptResult = awsCrypto.encryptData(masterKeyProvider,
        bytesToDecrypt);
    return encryptResult.getResult();
  }

  public byte[] decryptBytes(byte[] bytesToEncrypt) {
    CryptoResult<byte[], KmsMasterKey> decryptResult = awsCrypto.decryptData(masterKeyProvider, bytesToEncrypt);
    return decryptResult.getResult();
  }

}
