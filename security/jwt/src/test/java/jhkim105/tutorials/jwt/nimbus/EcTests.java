package jhkim105.tutorials.jwt.nimbus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.gen.ECKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.util.Base64;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * https://connect2id.com/products/nimbus-jose-jwt/examples/jwt-with-ec-signature
 */
@Slf4j
class EcTests {

  @Test
  void generateAndVerify_ES256() throws Exception {
    // Generate an EC key pair
    ECKey jwk = new ECKeyGenerator(Curve.P_256)
        .keyID("123")
        .generate();
    log.debug("jwk: {}", jwk);
    log.debug("privateKey: {}", Base64.getEncoder().encodeToString(jwk.toPrivateKey().getEncoded()));
    log.debug("publicKey: {}", Base64.getEncoder().encodeToString(jwk.toPublicKey().getEncoded()));

    ECKey ecPublicJWK = jwk.toPublicJWK();

    // Create the EC signer
    JWSSigner signer = new ECDSASigner(jwk);

    // Prepare JWT with claims set
    JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
        .subject("alice")
        .issuer("https://c2id.com")
        .expirationTime(new Date(new Date().getTime() + 60 * 1000))
        .build();

    SignedJWT signedJWT = new SignedJWT(
        new JWSHeader.Builder(JWSAlgorithm.ES256).keyID(jwk.getKeyID()).build(),
        claimsSet);

    // Compute the EC signature
    signedJWT.sign(signer);

    // Serialize the JWS to compact form
    String s = signedJWT.serialize();
    log.debug("token: {}", s);
    // On the consumer side, parse the JWS and verify its EC signature
    signedJWT = SignedJWT.parse(s);

    JWSVerifier verifier = new ECDSAVerifier(ecPublicJWK);
    assertTrue(signedJWT.verify(verifier));

    // Retrieve / verify the JWT claims according to the app requirements
    assertEquals("alice", signedJWT.getJWTClaimsSet().getSubject());
    assertEquals("https://c2id.com", signedJWT.getJWTClaimsSet().getIssuer());
    assertTrue(new Date().before(signedJWT.getJWTClaimsSet().getExpirationTime()));
  }


  @Test
  void generateAndParseKeyPair() throws Exception {
    ECKey ecJWK = new ECKeyGenerator(Curve.P_256)
        .keyID("123")
        .generate();

    String jwkString = ecJWK.toString();
    log.debug("jwk: {}", jwkString);
    ECKey parsedJwk = ECKey.parse(jwkString);

    assertEquals(ecJWK, parsedJwk);
  }


}
