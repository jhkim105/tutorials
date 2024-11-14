package jhkim105.tutorials.jwt.eddsa;

import java.util.UUID;
import jhkim105.tutorials.jwt.JwtPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eddsa")
@RequiredArgsConstructor
public class EdDsaController {


  private final EdDsaUtils edDsaUtils;


  @GetMapping("/generate")
  public String generate() {
    return edDsaUtils.generateToken(new JwtPrincipal(UUID.randomUUID().toString(), "USER"));
  }

  @GetMapping("/parse")
  public JwtPrincipal parse(String token) {
    return edDsaUtils.parse(token);
  }

  @GetMapping("/jwks")
  public String jwks() {
    return edDsaUtils.jwks();
  }

  /**
   * https://jwt.io 에서 검증시 사용
   */
  @GetMapping("/public-key")
  public String publicKey() {
    return edDsaUtils.getPublicKeyPEM();
  }

}
