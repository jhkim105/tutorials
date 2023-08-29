package jhkim105.tutorials.jwt.nimbus;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.shaded.gson.annotations.SerializedName;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.security.SecureRandom;
import java.util.Date;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;


@Slf4j
class NimbusTests {

  @Test
  void hs256() throws Exception {
    SecureRandom random = new SecureRandom();
    byte[] sharedSecret = new byte[32];
    random.nextBytes(sharedSecret);

    JWSSigner signer = new MACSigner(sharedSecret);

    JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
        .subject("alice")
        .issuer("https://c2id.com")
        .claim("custom", new Custom("name001"))
        .expirationTime(new Date(new Date().getTime() + 60 * 1000))
        .build();

    SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

    signedJWT.sign(signer);

    String token = signedJWT.serialize();
    log.debug(token);

    signedJWT = SignedJWT.parse(token);
    log.debug(signedJWT.getPayload().toString());

  }

  
  @RequiredArgsConstructor
  @Getter
  static class Custom {

    @SerializedName("custom_name")
    private final String customName;

  }

}
