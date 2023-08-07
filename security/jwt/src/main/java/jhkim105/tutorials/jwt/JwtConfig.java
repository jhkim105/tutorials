package jhkim105.tutorials.jwt;


import com.nimbusds.jose.jwk.ECKey;
import java.text.ParseException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {


  @Bean
  public ECKey jwk(JwtProperties jwtProperties) throws ParseException {
    return ECKey.parse(jwtProperties.getKey());
  }



}
