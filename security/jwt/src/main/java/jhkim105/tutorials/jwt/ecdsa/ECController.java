package jhkim105.tutorials.jwt.ecdsa;

import java.util.UUID;
import jhkim105.tutorials.jwt.JwtPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ecdsa")
@RequiredArgsConstructor
public class ECController {


  private final ECUtils ecUtils;


  @GetMapping("/generate")
  public String generate() {
    return ecUtils.generateToken(new JwtPrincipal(UUID.randomUUID().toString(), "USER"));
  }


  @GetMapping("/parse")
  public JwtPrincipal parse(String token) {
    return ecUtils.parse(token);
  }

  @GetMapping("/jwks")
  public String jwks() {
    return ecUtils.jwks();
  }

  // https://jwt.io 에서 검증시 사용
  @GetMapping("/public-key")
  public String publicKey() {
    return ecUtils.getPublicKeyPEM();
  }



}
