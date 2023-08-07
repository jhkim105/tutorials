package jhkim105.tutorials.jwt;


import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSHeader.Builder;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.util.Base64;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtUtils {

  private final ECKey jwk;

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

  public String publicKey() {
    try {
      return Base64.getEncoder().encodeToString(jwk.toPublicJWK().toPublicKey().getEncoded());
    } catch (JOSEException e) {
      throw new RuntimeException(e);
    }
  }

}
