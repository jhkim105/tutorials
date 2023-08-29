package jhkim105.tutorials.jwt.jjwt;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class JjwtTests {

  @Test
  void test() {
    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    String jws = Jwts.builder().setSubject("Joe").claim("custom", new Custom("name123")).signWith(key).compact();
    log.info("{}", jws);
    assertThat(Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws).getBody().getSubject())
        .isEqualTo("Joe");

    String secretString = Encoders.BASE64.encode(key.getEncoded());
    log.info("{}", secretString);
  }


  @RequiredArgsConstructor
  @Getter
  static class Custom {

    @JsonProperty("custom_name")
    private final String customName;

  }
}
