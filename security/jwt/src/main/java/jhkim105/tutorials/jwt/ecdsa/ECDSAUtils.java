package jhkim105.tutorials.jwt.ecdsa;


import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSHeader.Builder;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.gen.ECKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.io.StringWriter;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ECDSAUtils implements InitializingBean {

  private final ECDSAProperties ecdsaProperties;
  private ECKey jwk;

  public String generateToken() {
    JWSHeader header = new Builder(JWSAlgorithm.ES256)
        .keyID(jwk.getKeyID())
        .build();

    JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
        .subject("es256")
        .issuer("jhkim105")
        .expirationTime(new Date(new Date().getTime() + 60 * 1000))
        .build();

    SignedJWT signedJWT = new SignedJWT(header, claimsSet);
    try {
      signedJWT.sign(new ECDSASigner(jwk));
      return signedJWT.serialize();
    } catch (JOSEException e) {
      throw new RuntimeException(e);
    }
  }

  public String jwks() {
    return new JWKSet(jwk.toPublicJWK()).toString();
  }

  public String getPublicKeyEncodedString() {
    try {
      return Base64.getEncoder().encodeToString(jwk.toPublicJWK().toPublicKey().getEncoded());
    } catch (JOSEException e) {
      throw new RuntimeException(e);
    }
  }

  public String getPublicKeyPEM() {
    try (StringWriter stringWriter = new StringWriter(); JcaPEMWriter pemWriter = new JcaPEMWriter(stringWriter)) {
      pemWriter.writeObject(jwk.toPublicJWK().toECPublicKey());
      pemWriter.flush();
      return stringWriter.toString();
    } catch (Exception e) {
      throw new RuntimeException("Error converting public key to PEM format", e);
    }
  }


  @Override
  public void afterPropertiesSet() throws Exception {
    this.jwk = JWK.parse(ecdsaProperties.getKey()).toECKey();
    String jwtKey = ecdsaProperties.getKey();
    if(jwtKey == null || jwtKey.isBlank()) {
      jwtKey = generateJwtKey();
      log.info("new key generated. key: {}", jwtKey);
    }
    jwk =  ECKey.parse(jwtKey);
  }

  private String generateJwtKey() {
    try {
      return new ECKeyGenerator(Curve.P_256)
          .keyID(UUID.randomUUID().toString())
          .issueTime(new Date())
          .generate().toJSONString();
    } catch (JOSEException e) {
      throw new RuntimeException(e);
    }
  }
}
