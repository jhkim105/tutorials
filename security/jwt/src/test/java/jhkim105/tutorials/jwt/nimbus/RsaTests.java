package jhkim105.tutorials.jwt.nimbus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * https://connect2id.com/products/nimbus-jose-jwt/examples/jwt-with-rsa-signature
 */
@Slf4j
class RsaTests {


  @Test
  void test() throws Exception {
    int keySize = 2048;
    String keyId = "key01";

    // RSA signatures require a public and private RSA key pair, the public key
    // must be made known to the JWS recipient in order to verify the signatures
    RSAKey rsaKey = new RSAKeyGenerator(keySize)
        .keyID(keyId)
        .generate();


    // Create RSA-signer with the private key
    JWSSigner signer = new RSASSASigner(rsaKey);

    // Prepare JWT with claims set
    JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
        .subject("alice")
        .issuer("https://c2id.com")
        .expirationTime(new Date(new Date().getTime() + 60 * 1000))
        .build();

    SignedJWT signedJWT = new SignedJWT(
        new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaKey.getKeyID()).build(),
        claimsSet);

    // Compute the RSA signature
    signedJWT.sign(signer);

    // To serialize to compact form, produces something like
    // eyJhbGciOiJSUzI1NiJ9.SW4gUlNBIHdlIHRydXN0IQ.IRMQENi4nJyp4er2L
    // mZq3ivwoAjqa1uUkSBKFIX7ATndFF5ivnt-m8uApHO4kfIFOrW7w2Ezmlg3Qd
    // maXlS9DhN0nUk_hGI3amEjkKd0BWYCB8vfUbUv0XGjQip78AI4z1PrFRNidm7
    // -jPDm5Iq0SZnjKjCNS5Q15fokXZc8u0A
    String s = signedJWT.serialize();
    log.debug("token: {}", s);
    // On the consumer side, parse the JWS and verify its RSA signature
    signedJWT = SignedJWT.parse(s);

    RSAKey publicJWK = rsaKey.toPublicJWK();
    JWSVerifier verifier = new RSASSAVerifier(publicJWK);
    assertTrue(signedJWT.verify(verifier));

    // Retrieve / verify the JWT claims according to the app requirements
    assertEquals("alice", signedJWT.getJWTClaimsSet().getSubject());
    assertEquals("https://c2id.com", signedJWT.getJWTClaimsSet().getIssuer());
    assertTrue(new Date().before(signedJWT.getJWTClaimsSet().getExpirationTime()));

  }

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

  /**
   *
   * @throws IOException
   * @throws JOSEException
   */
  @Test
  void testPemPkcs8() throws IOException, JOSEException {
    // openssl genrsa -out rsa-private.pem 2048
    String pkcs1 = new String(Files.readAllBytes(Path.of("rsa-private.pem")));

    // openssl genpkey -algorithm RSA -out rsa-private-pkcs8 -pkeyopt rsa_keygen_bits:2048
    String pkcs8 = new String(Files.readAllBytes(Path.of("rsa-private-pkcs8.pem")));

    JWK jwk1 = JWK.parseFromPEMEncodedObjects(pkcs1);
    JWK jwk8 = JWK.parseFromPEMEncodedObjects(pkcs8);

    assertNotNull(jwk1);
    assertNotNull(jwk8);
  }


  @Test
  void generateAndParseKeyPair() throws Exception {
    RSAKey rsaJwk = new RSAKeyGenerator(2048)
        .keyID("rsa-001")
        .generate();

    String jwkString = rsaJwk.toString();
    log.debug("jwk: {}", jwkString);
    RSAKey parsedJwk = RSAKey.parse(jwkString);

    assertEquals(rsaJwk, parsedJwk);
  }

}
