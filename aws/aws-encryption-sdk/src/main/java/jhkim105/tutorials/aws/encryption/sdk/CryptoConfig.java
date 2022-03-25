package jhkim105.tutorials.aws.encryption.sdk;

import com.amazonaws.encryptionsdk.AwsCrypto;
import com.amazonaws.encryptionsdk.CommitmentPolicy;
import com.amazonaws.encryptionsdk.kmssdkv2.KmsMasterKeyProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class CryptoConfig {

  @Bean
  public KmsMasterKeyProvider masterKeyProvider(@Lazy KmsProperties kmsProperties) {
    return KmsMasterKeyProvider.builder()
        .buildStrict(kmsProperties.getKeyArn());
  }

  @Bean
  public AwsCrypto awsCrypto() {
    return AwsCrypto.builder()
        .withCommitmentPolicy(CommitmentPolicy.RequireEncryptRequireDecrypt)
        .build();
  }

}
