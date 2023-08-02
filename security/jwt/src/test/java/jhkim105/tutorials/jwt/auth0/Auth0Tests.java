package jhkim105.tutorials.jwt.auth0;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class Auth0Tests {

  private static final String SECRET = "jhkim105";
  private static final String ISSUER = "jhkim105";

  private String token;

  @Test
  void createToken() {
    try {
      Algorithm algorithm = Algorithm.HMAC256(SECRET);
      token = JWT.create()
          .withIssuer(ISSUER)
          .withSubject("Subject 1")
          .withClaim("claim1", "value 1")
          .sign(algorithm);

    } catch (JWTCreationException e){
      throw new RuntimeException(e);
    }
    log.debug("{}", token);
  }

  @Test
  void parseToken() {
    String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJqaGtpbTEwNSIsInN1YiI6IlN1YmplY3QgMSIsImNsYWltMSI6InZhbHVlIDEifQ.bIo01fNNG5mmKtt-ip7p8rec6q3EDho7-fQIqxJViLs";

    Algorithm algorithm = Algorithm.HMAC256(SECRET);
    JWTVerifier verifier = JWT.require(algorithm)
        .withIssuer(ISSUER)
        .build();
    try {
      DecodedJWT decodedJWT = verifier.verify(token);
      log.debug("Issuer: {}", decodedJWT.getIssuer());
      log.debug("Subject: {}", decodedJWT.getSubject());
      log.debug("claim1: {}", decodedJWT.getClaim("claim1"));
      log.debug("expireAt: {}", decodedJWT.getExpiresAt());
    } catch (JWTVerificationException e) {
      throw new RuntimeException(e);
    }

  }

}
