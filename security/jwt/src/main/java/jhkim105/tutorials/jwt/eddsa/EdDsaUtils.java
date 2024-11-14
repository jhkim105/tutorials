package jhkim105.tutorials.jwt.eddsa;


import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSHeader.Builder;
import com.nimbusds.jose.crypto.Ed25519Signer;
import com.nimbusds.jose.crypto.Ed25519Verifier;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.OctetKeyPair;
import com.nimbusds.jose.jwk.gen.OctetKeyPairGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.util.DateUtils;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;
import jhkim105.tutorials.jwt.JwtPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EdDsaUtils implements InitializingBean {

  private static final long DEFAULT_SKEW_SECONDS = 30;

  private OctetKeyPair jwk;

  public String generateToken(JwtPrincipal jwtPrincipal) {
    JWSHeader header = new Builder(JWSAlgorithm.EdDSA)
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
      signedJWT.sign(new Ed25519Signer(jwk.toOctetKeyPair()));
      return signedJWT.serialize();
    } catch (JOSEException e) {
      throw new RuntimeException(e);
    }
  }

  public String jwks() {
    return new JWKSet(jwk.toPublicJWK()).toString();
  }

  public String getPublicKeyPEM() {
    try (StringWriter stringWriter = new StringWriter(); PemWriter pemWriter = new PemWriter(stringWriter)) {
      byte[] publicKeyBytes = jwk.getDecodedX();

      PemObject pemObject = new PemObject("PUBLIC KEY", publicKeyBytes);
      pemWriter.writeObject(pemObject);
      pemWriter.flush();

      return stringWriter.toString();
    } catch (Exception e) {
      throw new RuntimeException("Error converting public key to PEM format", e);
    }
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    String jwtKey = generateJwtKey();
    jwk =  OctetKeyPair.parse(jwtKey);
  }

  public String generateJwtKey() {
    try {
      return new OctetKeyPairGenerator(Curve.Ed25519)
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

  private JwtPrincipal parseJwtPrincipal(SignedJWT signedJWT) {
    try {
      var jwtClaimsSet = signedJWT.getJWTClaimsSet();
      return new JwtPrincipal(jwtClaimsSet.getSubject(), jwtClaimsSet.getStringClaim("authority"));
    } catch (ParseException e) {
      throw new IllegalStateException(e);
    }
  }

  private SignedJWT parseSignedJWT(String token) {
    try {
      return SignedJWT.parse(token);
    } catch (ParseException e) {
      throw new IllegalStateException(e);
    }
  }
  private void verify(SignedJWT signedJWT) {
    try {
      verifySignature(signedJWT, jwk.toPublicJWK());
      verifyExpirationTime(signedJWT);
    } catch (JOSEException | ParseException e) {
      throw new IllegalStateException(e);
    }
  }

  private void verifySignature(SignedJWT signedJWT, OctetKeyPair publicKey) throws JOSEException {
    if (!signedJWT.verify(new Ed25519Verifier(publicKey))) {
      throw new IllegalStateException("token verification failed");
    }
  }

  private void verifyExpirationTime(SignedJWT signedJWT) throws ParseException {
    if (!DateUtils.isAfter(signedJWT.getJWTClaimsSet().getExpirationTime(), new Date(), DEFAULT_SKEW_SECONDS)) {
      throw new IllegalStateException("token expired");
    }
  }

}
