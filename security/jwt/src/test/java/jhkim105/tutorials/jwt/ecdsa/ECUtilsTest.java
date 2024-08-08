package jhkim105.tutorials.jwt.ecdsa;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jwt.SignedJWT;
import java.util.UUID;
import jhkim105.tutorials.jwt.JwtPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
class ECUtilsTest {


  @Autowired
  ECUtils ecUtils;


  @Test
  void verifyUsingPublicKeyPEM() throws Exception {
    String token = ecUtils.generateToken(new JwtPrincipal(UUID.randomUUID().toString(), "USER"));
    log.debug(token);
    String publicKeyPEM = ecUtils.getPublicKeyPEM();
    log.debug(publicKeyPEM);
    log.debug(ecUtils.getPublicKeyEncodedString());
    JWK publicJWK = JWK.parseFromPEMEncodedObjects(publicKeyPEM);
    SignedJWT jwt = SignedJWT.parse(token);
    JWSVerifier verifier = new ECDSAVerifier((ECKey) publicJWK);

    assertTrue(jwt.verify(verifier));
  }

  @Test
  void generateKey() throws Exception {
    String key = ecUtils.generateKey();
    ECKey ecKey = ECKey.parse(key);
    log.debug(key);
    log.debug("{}", ecKey.toPublicJWK());
  }

}