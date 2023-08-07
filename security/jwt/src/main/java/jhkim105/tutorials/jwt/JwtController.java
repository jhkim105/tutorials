package jhkim105.tutorials.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jwt")
@RequiredArgsConstructor
public class JwtController {


  private final JwtUtils jwtUtils;


  @GetMapping("/generate")
  public String generate() {
    return jwtUtils.generateToken();
  }

  @GetMapping("/jwks")
  public String jwks() {
    return jwtUtils.jwks();
  }

  /**
   * https://jwt.io 에서 검증시 사용
   */
  @GetMapping("/public-key")
  public String publicKey() {
    return jwtUtils.publicKey();
  }

}
