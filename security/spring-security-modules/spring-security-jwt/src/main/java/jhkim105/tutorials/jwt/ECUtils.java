package jhkim105.tutorials.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSHeader.Builder;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.gen.ECKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.util.DateUtils;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;

@Slf4j
public class ECUtils {

    private static final long DEFAULT_SKEW_SECONDS = 30;


    public static String generateKey(String keyId) {
        try {
            return new ECKeyGenerator(Curve.P_256)
                .keyID(keyId)
                .issueTime(new Date())
                .generate().toJSONString();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateToken(String key, Map<String, Object> claimsMap) {
        ECKey ecKey = parseJWK(key);
        JWSHeader header = new Builder(JWSAlgorithm.ES256)
            .keyID(ecKey.getKeyID())
            .build();

        var claimsSetBuilder = new JWTClaimsSet.Builder()
            .expirationTime(new Date(new Date().getTime() + 60 * 1000));

        claimsMap.forEach(claimsSetBuilder::claim);

        SignedJWT signedJWT = new SignedJWT(header, claimsSetBuilder.build());
        try {
            signedJWT.sign(new ECDSASigner(ecKey));
            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private static ECKey parseJWK(String key) {
        try {
            return ECKey.parse(key);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }


    public static String parseKeyId(String token) {
        try {
            return SignedJWT.parse(token).getHeader().getKeyID();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> parse(String publicKey, String token) {
        var signedJWT = parseSignedJWT(token);
        var publicJWK = parseJWK(publicKey);
        verify(publicJWK, signedJWT);
        return parseClaims(signedJWT);
    }

    private static SignedJWT parseSignedJWT(String token) {
        try {
            return SignedJWT.parse(token);
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }
    }

    private static Map<String, Object> parseClaims(SignedJWT signedJWT) {
        try {
            var jwtClaimsSet = signedJWT.getJWTClaimsSet();
            var claimsMap = new HashMap<String, Object>();
            claimsMap.put("id", jwtClaimsSet.getStringClaim("id"));
            claimsMap.put("authority", jwtClaimsSet.getStringClaim("authority"));
            return claimsMap;
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }
    }

    private static void verify(ECKey publicJWK, SignedJWT signedJWT) {
        try {
            verifySignature(signedJWT, publicJWK);
            verifyExpirationTime(signedJWT);
        } catch (JOSEException | ParseException e) {
            throw new IllegalStateException(e);
        }
    }

    private static void verifySignature(SignedJWT signedJWT, ECKey publicKey) throws JOSEException {
        if (!signedJWT.verify(new ECDSAVerifier(publicKey))) {
            throw new IllegalStateException("token verification failed");
        }
    }

    private static void verifyExpirationTime(SignedJWT signedJWT) throws ParseException {
        if (!DateUtils.isAfter(signedJWT.getJWTClaimsSet().getExpirationTime(), new Date(), DEFAULT_SKEW_SECONDS)) {
            throw new IllegalStateException("token expired");
        }
    }

    public static String jwks(List<String> keyList) {
        List<JWK> jwks = keyList.stream().map(key -> parseJWK(key)).map(JWK::toPublicJWK).toList();
        return new JWKSet(jwks).toString();
    }

    public static String getPublicKeyPEM(String key) {
        ECKey jwk = parseJWK(key);
        try (StringWriter stringWriter = new StringWriter(); JcaPEMWriter pemWriter = new JcaPEMWriter(stringWriter)) {
            pemWriter.writeObject(jwk.toPublicJWK().toECPublicKey());
            pemWriter.flush();
            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error converting public key to PEM format", e);
        }
    }
}
