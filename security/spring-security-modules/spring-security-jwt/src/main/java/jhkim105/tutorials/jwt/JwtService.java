package jhkim105.tutorials.jwt;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import jhkim105.tutorials.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwkRepository jwkRepository;

    public Jwk generateKey() {
        var keyId = UUID.randomUUID().toString();
        var key = ECUtils.generateKey(keyId);
        return jwkRepository.save(new Jwk(keyId, key));
    }

    public String issueToken(UserPrincipal userPrincipal) {
        Jwk jwk = createOrGetJwk();
        Map<String, Object> claimMap = new HashMap<>();
        claimMap.put("id", userPrincipal.id());
        claimMap.put("authority", userPrincipal.authority());
        return ECUtils.generateToken(jwk.getKeyData(), claimMap);
    }

    private Jwk createOrGetJwk() {
        var jwkOptional = jwkRepository.findTopByOrderByCreatedAtDesc();
        return jwkOptional.orElse(generateKey());
    }

    public UserPrincipal parseToken(String token) {
        String keyId = ECUtils.parseKeyId(token);
        Jwk jwk = jwkRepository.findById(keyId).orElseThrow();
        Map<String, Object> map = ECUtils.parse(jwk.getKeyData(), token);
        return new UserPrincipal((String)map.get("id"), (String)map.get("authority"));
    }


    public String jwks() {
        List<Jwk> jwks = jwkRepository.findAll();
        List<String> keyList = jwks.stream().map(Jwk::getKeyData).toList();
        return ECUtils.jwks(keyList);
    }

    public String getPublicKeyPEM() {
        Jwk jwk = createOrGetJwk();
        return ECUtils.getPublicKeyPEM(jwk.getKeyData());
    }
}
