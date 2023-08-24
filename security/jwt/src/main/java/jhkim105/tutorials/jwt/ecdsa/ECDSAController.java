package jhkim105.tutorials.jwt.ecdsa;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ecdsa")
@RequiredArgsConstructor
public class ECDSAController {


  private final ECDSAUtils ecdsaUtils;


  @GetMapping("/generate")
  public String generate() {
    return ecdsaUtils.generateToken();
  }

  @GetMapping("/jwks")
  public String jwks() {
    return ecdsaUtils.jwks();
  }

  /**
   * https://jwt.io 에서 검증시 사용
   */
  @GetMapping("/public-key")
  public String publicKey() {
    return ecdsaUtils.getPublicKeyEncodedString();
  }

}
