package jhkim105.tutorials.jwt;

import com.nimbusds.jose.jwk.JWKSet;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;

public class JwksUtils {
    public static JWKSet getPubicKeyFromUrl(String jwksUrl) {
        int connectTimeout = 1000;
        int readTimeout = 1000;
        int sizeLimit = 10000;
        URL url;
        try {
            url = new URI(jwksUrl).toURL();
            return JWKSet.load(url, connectTimeout, readTimeout, sizeLimit);
        } catch (IOException | URISyntaxException | ParseException e) {
            throw new IllegalStateException(e);
        }
    }
}
