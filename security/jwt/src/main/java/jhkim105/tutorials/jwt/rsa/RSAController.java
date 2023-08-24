package jhkim105.tutorials.jwt.rsa;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rsa")
@RequiredArgsConstructor
public class RSAController {


  private final RSAUtils rsaUtils;


  @GetMapping("/generate")
  public String generate() {
    return rsaUtils.generateToken();
  }

  @GetMapping("/jwks")
  public String jwks() {
    return rsaUtils.jwks();
  }

  /**
   * https://jwt.io 에서 검증시 사용
   */
  @GetMapping("/public-key")
  public String publicKey() {
    return rsaUtils.getPublicKeyPEM();
  }

}
