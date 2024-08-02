package jhkim105.tutorials.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jwks")
@RequiredArgsConstructor
public class JwkController {

    private final JwtService jwtService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String jwks() {
        return jwtService.jwks();
    }

    @GetMapping(value = "/public-key", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPublicKey() {
        return jwtService.getPublicKeyPEM();
    }
}
