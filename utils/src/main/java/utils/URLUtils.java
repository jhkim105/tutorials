package utils;

import java.net.MalformedURLException;
import java.net.URL;

public class URLUtils {
    public static String getBaseUrl(String urlString) {
        URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        String baseUrl = url.getProtocol() + "://" + url.getHost();

        int port = url.getPort();
        if (port != -1) {
            baseUrl += ":" + port;
        }

        return baseUrl;
    }
}
