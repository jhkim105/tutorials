package jhkim105.tutorials.jwt.nimbus;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


/**
 *
 * Get Token
 * - curl-token-password-grant-type.sh
 *
 *
 *
 */
@Slf4j
@Disabled
class JwsRsaTests {

  @Test
  @Disabled
  void verify() throws ParseException, JOSEException {
    String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJnSVFZNTF3NzZRamkzVE9uR2JPWmd2YUdBSnhnOGw0cFVJTFZqZ0t4SVNvIn0.eyJleHAiOjE2OTAzNTM4NzEsImlhdCI6MTY5MDM1MzU3MSwianRpIjoiNjU1ZGRiZWItMzRmOS00N2JjLTgzZWEtYTYyODgwNWExNmZlIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDg5L3JlYWxtcy9kZW1vIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjFmZjliYTA4LThkMDItNDE2Yi04YjkzLWM4YWQwOWQ4MDA3YiIsInR5cCI6IkJlYXJlciIsImF6cCI6Im9pZGMtZGVtbyIsInNlc3Npb25fc3RhdGUiOiI5YTBhZWJmNS0yYzhmLTQ5MWEtYTEzOS1hMTU3YThiZTdmZjciLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJkZWZhdWx0LXJvbGVzLWRlbW8iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6ImVtYWlsIHByb2ZpbGUgcmVhZCIsInNpZCI6IjlhMGFlYmY1LTJjOGYtNDkxYS1hMTM5LWExNTdhOGJlN2ZmNyIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwicHJlZmVycmVkX3VzZXJuYW1lIjoidXNlcjAxIiwiZ2l2ZW5fbmFtZSI6IiIsImZhbWlseV9uYW1lIjoiIn0.gSwp8XGDMFN42ZFoTuq-a-JRrHro0PgruaX897lzPp33VZkSaBAeU0-gJYgLppEsyL7RSX78Hmv053885cDZ2HoaztvZl0yF-RUuWPvgv2BmUmPiA1dytjxYHX1WOSW6TKegjbFFyqU8ryiKRmZ83VoIGyOcLVHrDveHh0tVuMJ-YkRE3rKvghffkDJbbDxB_Ceq6dddgk7XSgosdKarT8BPCOf1-WMpL4OMypct7tIMR9BwEJ_1m2EfZOwkKPkOXdPkj_R7PlfZm5lDhOLyubGxFI_HKBky2DM2dhns9oJ8_hU_j68T9ep6tpqwZcAts6I0h4AgmbKVeldNlsZJOA";

    JWSObject jwsObject = JWSObject.parse(token);
    String keyId = jwsObject.getHeader().getKeyID();
    log.debug("payload: {}", jwsObject.getPayload());

    String jwksUrl = "http://localhost:8089/realms/demo/protocol/openid-connect/certs";
    JWKSet jwkSet = getPubicKeyFromUrl(jwksUrl);
    JWK jwk = jwkSet.getKeyByKeyId(keyId);
    RSAKey rsaKey = jwk.toRSAKey();
    JWSVerifier verifier = new RSASSAVerifier(rsaKey);
    assertTrue(jwsObject.verify(verifier));
  }

  private JWKSet getPubicKeyFromUrl(String jwksUrl) {
    int connectTimeout = 1000;
    int readTimeout = 1000;
    int sizeLimit = 10000;
    URL url;
    try {
      url = new URL("http://localhost:8089/realms/demo/protocol/openid-connect/certs");
      return JWKSet.load(url, connectTimeout, readTimeout, sizeLimit);
    } catch (IOException | ParseException e) {
      throw new RuntimeException(e);
    }

  }


  @Test
  // JWSObject + JWTClaimsSet
  void verifyWithSignedJWT() throws ParseException, JOSEException {
    String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJnSVFZNTF3NzZRamkzVE9uR2JPWmd2YUdBSnhnOGw0cFVJTFZqZ0t4SVNvIn0.eyJleHAiOjE2OTAzNTM4NzEsImlhdCI6MTY5MDM1MzU3MSwianRpIjoiNjU1ZGRiZWItMzRmOS00N2JjLTgzZWEtYTYyODgwNWExNmZlIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDg5L3JlYWxtcy9kZW1vIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjFmZjliYTA4LThkMDItNDE2Yi04YjkzLWM4YWQwOWQ4MDA3YiIsInR5cCI6IkJlYXJlciIsImF6cCI6Im9pZGMtZGVtbyIsInNlc3Npb25fc3RhdGUiOiI5YTBhZWJmNS0yYzhmLTQ5MWEtYTEzOS1hMTU3YThiZTdmZjciLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJkZWZhdWx0LXJvbGVzLWRlbW8iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6ImVtYWlsIHByb2ZpbGUgcmVhZCIsInNpZCI6IjlhMGFlYmY1LTJjOGYtNDkxYS1hMTM5LWExNTdhOGJlN2ZmNyIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwicHJlZmVycmVkX3VzZXJuYW1lIjoidXNlcjAxIiwiZ2l2ZW5fbmFtZSI6IiIsImZhbWlseV9uYW1lIjoiIn0.gSwp8XGDMFN42ZFoTuq-a-JRrHro0PgruaX897lzPp33VZkSaBAeU0-gJYgLppEsyL7RSX78Hmv053885cDZ2HoaztvZl0yF-RUuWPvgv2BmUmPiA1dytjxYHX1WOSW6TKegjbFFyqU8ryiKRmZ83VoIGyOcLVHrDveHh0tVuMJ-YkRE3rKvghffkDJbbDxB_Ceq6dddgk7XSgosdKarT8BPCOf1-WMpL4OMypct7tIMR9BwEJ_1m2EfZOwkKPkOXdPkj_R7PlfZm5lDhOLyubGxFI_HKBky2DM2dhns9oJ8_hU_j68T9ep6tpqwZcAts6I0h4AgmbKVeldNlsZJOA";

    SignedJWT signedJWT = SignedJWT.parse(token);
    log.debug("payload: {}", signedJWT.getPayload());
    String keyId = signedJWT.getHeader().getKeyID();
    log.debug("{}", signedJWT.getJWTClaimsSet().getSubject());

    String jwksUrl = "http://localhost:8089/realms/demo/protocol/openid-connect/certs";
    JWKSet jwkSet = getPubicKeyFromUrl(jwksUrl);

    JWK jwk = jwkSet.getKeyByKeyId(keyId);
    RSAKey rsaPublicKey = jwk.toRSAKey();
    JWSVerifier verifier = new RSASSAVerifier(rsaPublicKey);
    assertTrue(signedJWT.verify(verifier));
  }


}
