package jhkim105.tutorials.jwt.nimbus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.Ed25519Signer;
import com.nimbusds.jose.crypto.Ed25519Verifier;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.OctetKeyPair;
import com.nimbusds.jose.jwk.gen.OctetKeyPairGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * https://connect2id.com/products/nimbus-jose-jwt/examples/jwt-with-eddsa
 */
@Slf4j
class EdDsaTests {


  @Test
  void generateAndParseKeyPair() throws Exception {
    OctetKeyPair jwk = new OctetKeyPairGenerator(Curve.Ed25519)
        .keyID("123")
        .generate();
    String keyString = jwk.toString();


    log.debug(keyString);
    // {"kty":"OKP","d":"GeYgt7IrHVbciu-66DD4U_SSJgRKgD5cxlDXQAbU7Lk","crv":"Ed25519","kid":"123","x":"PnsX2SKrPDrqlqWvtkJhTLZjdKOyBaiUXvfGzY0olWc"}
    OctetKeyPair parsedJwk = OctetKeyPair.parse(keyString);
    assertEquals(jwk, parsedJwk);
  }

  @Test
  void parseKeyPair() throws Exception {

  }

  @Test
  void generateAndVerify_EdDSA() throws Exception {
    // Generate a key pair with Ed25519 curve
    OctetKeyPair jwk = new OctetKeyPairGenerator(Curve.Ed25519)
        .keyID("123")
        .generate();
    OctetKeyPair publicJWK = jwk.toPublicJWK();
    log.debug("jwk: {}", jwk);

    // Create the EdDSA signer
    JWSSigner signer = new Ed25519Signer(jwk);

    // Prepare JWT with claims set
    JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
        .subject("alice")
        .issuer("https://c2id.com")
        .expirationTime(new Date(new Date().getTime() + 60 * 1000))
        .build();

    SignedJWT signedJWT = new SignedJWT(
        new JWSHeader.Builder(JWSAlgorithm.EdDSA).keyID(jwk.getKeyID()).build(),
        claimsSet);

    // Compute the EC signature
    signedJWT.sign(signer);

    // Serialize the JWS to compact form
    String s = signedJWT.serialize();
    log.debug("token: {}", s);

    // On the consumer side, parse the JWS and verify its EdDSA signature
    signedJWT = SignedJWT.parse(s);

    JWSVerifier verifier = new Ed25519Verifier(publicJWK);
    assertTrue(signedJWT.verify(verifier));

    // Retrieve / verify the JWT claims according to the app requirements
    assertEquals("alice", signedJWT.getJWTClaimsSet().getSubject());
    assertEquals("https://c2id.com", signedJWT.getJWTClaimsSet().getIssuer());
    assertTrue(new Date().before(signedJWT.getJWTClaimsSet().getExpirationTime()));
  }
}
