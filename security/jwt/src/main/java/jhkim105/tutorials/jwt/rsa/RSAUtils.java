package jhkim105.tutorials.jwt.rsa;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSHeader.Builder;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Base64;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

/**
 * openssl genrsa -out rsa-private.pem 2048
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RSAUtils implements InitializingBean {

  @Value("${rsa.key-path}")
  private String keyPath;

  private RSAKey jwk;

  private final ResourceLoader resourceLoader;

  public String generateToken() {
    JWSHeader header = new Builder(JWSAlgorithm.RS256)
        .keyID(jwk.getKeyID())
        .build();

    JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
        .subject("rs256")
        .issuer("jhkim105")
        .expirationTime(new Date(new Date().getTime() + 60 * 1000))
        .build();

    SignedJWT signedJWT = new SignedJWT(header, claimsSet);
    try {
      signedJWT.sign(new RSASSASigner(jwk));
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
      pemWriter.writeObject(jwk.toPublicJWK().toRSAPublicKey());
      pemWriter.flush();
      return stringWriter.toString();
    } catch (Exception e) {
      throw new RuntimeException("Error converting public key to PEM format", e);
    }
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    // openssl genrsa -out rsa-private.pem 2048
    // OR
    // openssl genpkey -algorithm RSA -out rsa-private-pkcs8 -pkeyopt rsa_keygen_bits:2048
    jwk = JWK.parseFromPEMEncodedObjects(readPemEncodedRSAPrivateKeyString()).toRSAKey();
  }

  private String readPemEncodedRSAPrivateKeyString() {
    try {
      byte[] bytes = FileCopyUtils.copyToByteArray(resourceLoader.getResource(keyPath).getInputStream());
      return new String(bytes);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


}
