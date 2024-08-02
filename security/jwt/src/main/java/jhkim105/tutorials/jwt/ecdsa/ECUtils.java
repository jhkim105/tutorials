package jhkim105.tutorials.jwt.ecdsa;


import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSHeader.Builder;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.gen.ECKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.util.DateUtils;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import jhkim105.tutorials.jwt.JwtPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ECUtils implements InitializingBean {

  private static final long DEFAULT_SKEW_SECONDS = 30;
  private ECKey jwk;

  public String generateToken(JwtPrincipal jwtPrincipal) {
    JWSHeader header = new Builder(JWSAlgorithm.ES256)
        .keyID(jwk.getKeyID())
        .build();

    JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
        .subject(jwtPrincipal.id())
        .issuer("jhkim105")
        .expirationTime(new Date(new Date().getTime() + 60 * 1000))
        .claim("authority", jwtPrincipal.authority())
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
    String jwtKey = generateJwtKey();
    jwk =  ECKey.parse(jwtKey);
  }

  public String generateJwtKey() {
    try {
      return new ECKeyGenerator(Curve.P_256)
          .keyID(UUID.randomUUID().toString())
          .issueTime(new Date())
          .generate().toJSONString();
    } catch (JOSEException e) {
      throw new RuntimeException(e);
    }
  }

  public JwtPrincipal parse(String token) {
    var signedJWT = parseSignedJWT(token);
    verify(signedJWT);
    return parseJwtPrincipal(signedJWT);
  }

  private SignedJWT parseSignedJWT(String token) {
    try {
      return SignedJWT.parse(token);
    } catch (ParseException e) {
      throw new IllegalStateException(e);
    }
  }

  private JwtPrincipal parseJwtPrincipal(SignedJWT signedJWT) {
    try {
      var jwtClaimsSet = signedJWT.getJWTClaimsSet();
      return new JwtPrincipal(jwtClaimsSet.getSubject(), jwtClaimsSet.getStringClaim("authority"));
    } catch (ParseException e) {
      throw new IllegalStateException(e);
    }
  }

  private void verify(SignedJWT signedJWT) {
    try {
      verifySignature(signedJWT, jwk);
      verifyExpirationTime(signedJWT);
    } catch (JOSEException | ParseException e) {
      throw new IllegalStateException(e);
    }
  }

  private void verifySignature(SignedJWT signedJWT, ECKey publicKey) throws JOSEException {
    if (!signedJWT.verify(new ECDSAVerifier(publicKey))) {
      throw new IllegalStateException("token verification failed");
    }
  }

  private void verifyExpirationTime(SignedJWT signedJWT) throws ParseException {
    if (!DateUtils.isAfter(signedJWT.getJWTClaimsSet().getExpirationTime(), new Date(), DEFAULT_SKEW_SECONDS)) {
      throw new IllegalStateException("token expired");
    }
  }
}
