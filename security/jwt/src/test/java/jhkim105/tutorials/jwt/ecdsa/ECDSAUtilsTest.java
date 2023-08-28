package jhkim105.tutorials.jwt.ecdsa;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
class ECDSAUtilsTest {


  @Autowired
  ECDSAUtils ecdsaUtils;


  @Test
  void verifyUsingPublicKeyPEM() throws Exception {
    String token = ecdsaUtils.generateToken();
    log.debug(token);
    String publicKeyPEM = ecdsaUtils.getPublicKeyPEM();
    log.debug(publicKeyPEM);
    log.debug(ecdsaUtils.getPublicKeyEncodedString());
    JWK publicJWK = JWK.parseFromPEMEncodedObjects(publicKeyPEM);
    SignedJWT jwt = SignedJWT.parse(token);
    JWSVerifier verifier = new ECDSAVerifier((ECKey) publicJWK);

    assertTrue(jwt.verify(verifier));
  }

}