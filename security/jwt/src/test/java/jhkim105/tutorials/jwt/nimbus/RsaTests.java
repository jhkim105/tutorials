package jhkim105.tutorials.jwt.nimbus;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class RsaTests {


  @Test
  void generateJWK() throws Exception {
    RSAKey jwk = new RSAKeyGenerator(2048)
        .keyUse(KeyUse.SIGNATURE) // indicate the intended use of the key (optional)
        .keyID(UUID.randomUUID().toString())
        .issueTime(new Date())
        .generate();


    log.debug("jwk-> {}", jwk);
    log.debug("publicJWK-> {}", jwk.toPublicJWK());
  }

  @Test
  void generateJWKUsingKeyPairGenerator() throws NoSuchAlgorithmException {
    // Generate the RSA key pair
    KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
    gen.initialize(2048);
    KeyPair keyPair = gen.generateKeyPair();
    // Convert to JWK format
    JWK jwk = new RSAKey.Builder((RSAPublicKey)keyPair.getPublic())
        .privateKey((RSAPrivateKey)keyPair.getPrivate())
        .keyUse(KeyUse.SIGNATURE)
        .keyID(UUID.randomUUID().toString())
        .issueTime(new Date())
        .build();

    log.debug("jwk-> {}", jwk);
    log.debug("publicJWK-> {}", jwk.toPublicJWK());

  }

}
